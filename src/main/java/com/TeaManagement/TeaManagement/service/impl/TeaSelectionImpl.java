package com.TeaManagement.TeaManagement.service.impl;

import com.TeaManagement.TeaManagement.dao.TeaOptionsDao;
import com.TeaManagement.TeaManagement.dao.TeaSelectionDao;
import com.TeaManagement.TeaManagement.dao.UserDao;
import com.TeaManagement.TeaManagement.dto.*;
import com.TeaManagement.TeaManagement.entity.TeaOptions;
import com.TeaManagement.TeaManagement.entity.TeaSelection;
import com.TeaManagement.TeaManagement.entity.User;
import com.TeaManagement.TeaManagement.entity.enums.Teatime;
import com.TeaManagement.TeaManagement.service.TeaSelectionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class TeaSelectionImpl implements TeaSelectionService {
    @Autowired
    private TeaSelectionDao teaSelectionDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TeaOptionsDao teaOptionsDao;

    @Override
    public String saveTeaSelectionOption(TeaSelectionDto teaSelectionDto) {

        User user = userDao.findById(teaSelectionDto.getEmpNo()).orElseThrow(()->
                new RuntimeException("user not found with empNo:"+ teaSelectionDto.getEmpNo()));

        TeaOptions teaOptions = teaOptionsDao.findById(teaSelectionDto.getBeverageId())
                .orElseThrow(() -> new RuntimeException("Tea Option not found with beverageId: "
                        + teaSelectionDto.getBeverageId()));

        TeaSelection teaSelection = new TeaSelection();
        teaSelection.setUser(user);
        teaSelection.setTeaOptions(teaOptions);
        teaSelection.setTeatime(teaSelectionDto.getTeatime());

        teaSelectionDao.save(teaSelection);
        return user.getEmpName() + " Order for " + teaOptions.getBeverageName() + " recorded successfully.";

    }

    @Override
    public List<CountByBeverageDto> countByBeverages(Teatime teaTime) {
        LocalDate currentDate = LocalDate.now();
        LocalDateTime startOfDay = currentDate.atStartOfDay();
        LocalDateTime endOfDay = currentDate.plusDays(1).atStartOfDay();

        List<TeaSelection> teaSelections = teaSelectionDao.
                findAllByTeatimeAndLocalDateTimeBetween(teaTime, startOfDay, endOfDay);

        if (teaSelections.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No tea selections found for teaTime: " + teaTime + " on " + currentDate);
        }

        Map<String, Long> beverageCountMap = teaSelections.stream()
                .collect(Collectors.groupingBy(ts -> ts.getTeaOptions().getBeverageName(), Collectors.counting()));

        List<CountByBeverageDto> countByBeverageDtos = beverageCountMap.entrySet().stream()
                .map(entry -> new CountByBeverageDto(entry.getKey(), entry.getValue().intValue()))
                .collect(Collectors.toList());

        return countByBeverageDtos;


    }

    @Override
    public List<CountByDepartmentDto> countByDepartmentSAndBeverage(Teatime teaTime) {
        // Define the current day's range
        LocalDate currentDate = LocalDate.now();
        LocalDateTime startOfDay = currentDate.atStartOfDay();
        LocalDateTime endOfDay = currentDate.plusDays(1).atStartOfDay();

        List<TeaSelection> teaSelections = teaSelectionDao.findAllByTeatimeAndLocalDateTimeBetween(
                teaTime, startOfDay, endOfDay);

        if (teaSelections.isEmpty()) {
            throw new RuntimeException("No tea selections found for teaTime: " + teaTime + " on " + currentDate);
        }

        Map<String, Map<String, Long>> deptBeverageMap = teaSelections.stream()
                .collect(Collectors.groupingBy(
                        ts -> ts.getUser().getEmpDepartment(),
                        Collectors.groupingBy(
                                ts -> ts.getTeaOptions().getBeverageName(),
                                Collectors.counting()
                        )
                ));

        List<CountByDepartmentDto> result = new ArrayList<>();

        for (Map.Entry<String, Map<String, Long>> deptEntry : deptBeverageMap.entrySet()) {
            String deptName = deptEntry.getKey();
            Map<String, Long> beverageLongMap = deptEntry.getValue();

            Map<String, Integer> beverageCount = new HashMap<>();
            for (Map.Entry<String, Long> bevEntry : beverageLongMap.entrySet()) {
                beverageCount.put(bevEntry.getKey(), bevEntry.getValue().intValue());
            }

            result.add(new CountByDepartmentDto(deptName, beverageCount));
        }

        return result;
    }

    @Override
    public ActiveSessionDto getCurrentSessionStatus() {
        LocalDateTime now = LocalDateTime.now();
        int currentMinutes = now.getHour() * 60 + now.getMinute();

        int morningStart = 8 * 60;
        int morningEnd = 10 * 60 + 30;

        int eveningStart = 15 * 60;
        int eveningEnd = 16 * 60 + 30;

        if (currentMinutes >= morningStart && currentMinutes <= morningEnd) {
            LocalDateTime endTime = now.withHour(12).withMinute(30).withSecond(0).withNano(0);
            long remaining = java.time.Duration.between(now, endTime).getSeconds();
            return new ActiveSessionDto(true, Teatime.MORNING, remaining);
        } else if (currentMinutes >= eveningStart && currentMinutes <= eveningEnd) {
            LocalDateTime endTime = now.withHour(16).withMinute(30).withSecond(0).withNano(0);
            long remaining = java.time.Duration.between(now, endTime).getSeconds();
            return new ActiveSessionDto(true, Teatime.EVENING, remaining);
        } else {
            return new ActiveSessionDto(false, Teatime.NONE, 0L);
        }
    }

    @Override
    public List<CostByDepartmentDto> getMonthlyCostByDepartment(String month, int year) {
        int monthValue;
        try {
            monthValue = Month.valueOf(month.toUpperCase()).getValue();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid month: " + month);
        }

        LocalDate startDate = LocalDate.of(year, monthValue, 1);
        LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1);

        // Get tea selections for the month
        List<TeaSelection> selections = teaSelectionDao.findAllByLocalDateTimeBetween(
                startDate.atStartOfDay(),
                endDate.atStartOfDay()
        );

        if (selections.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, Map<String, List<TeaSelection>>> deptBevMap = new HashMap<>();

        for (TeaSelection selection : selections) {
            String department = selection.getUser().getEmpDepartment();
            String beverage = selection.getTeaOptions().getBeverageName();

            if (!deptBevMap.containsKey(department)) {
                deptBevMap.put(department, new HashMap<>());
            }

            if (!deptBevMap.get(department).containsKey(beverage)) {
                deptBevMap.get(department).put(beverage, new ArrayList<>());
            }

            deptBevMap.get(department).get(beverage).add(selection);
        }

        // Calculate costs and create DTOs
        List<CostByDepartmentDto> result = new ArrayList<>();

        for (Map.Entry<String, Map<String, List<TeaSelection>>> deptEntry : deptBevMap.entrySet()) {
            String department = deptEntry.getKey();
            Map<String, List<TeaSelection>> beverageMap = deptEntry.getValue();

            CostByDepartmentDto deptDto = new CostByDepartmentDto();
            deptDto.setDeptName(department);

            double totalDeptCost = 0.0;

            for (Map.Entry<String, List<TeaSelection>> bevEntry : beverageMap.entrySet()) {
                String beverage = bevEntry.getKey();
                List<TeaSelection> bevSelections = bevEntry.getValue();

                int count = bevSelections.size();
                double unitPrice = bevSelections.get(0).getTeaOptions().getUnitPrice();
                double cost = count * unitPrice;

                CostByDepartmentDto.BeverageDetail detail = new CostByDepartmentDto.BeverageDetail();
                detail.setCount(count);
                detail.setCost(cost);

                deptDto.getBeverageDetails().put(beverage, detail);
                totalDeptCost += cost;
            }

            deptDto.setTotalCost(totalDeptCost);
            result.add(deptDto);
        }

        return result;

    }

}
