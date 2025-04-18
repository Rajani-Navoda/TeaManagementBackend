package com.TeaManagement.TeaManagement.service.impl;

import com.TeaManagement.TeaManagement.dao.TeaOptionsDao;
import com.TeaManagement.TeaManagement.dao.TeaSelectionDao;
import com.TeaManagement.TeaManagement.dao.UserDao;
import com.TeaManagement.TeaManagement.dto.CountByBeverageDto;
import com.TeaManagement.TeaManagement.dto.CountByDepartmentDto;
import com.TeaManagement.TeaManagement.dto.TeaOptionsDto;
import com.TeaManagement.TeaManagement.dto.TeaSelectionDto;
import com.TeaManagement.TeaManagement.entity.TeaOptions;
import com.TeaManagement.TeaManagement.entity.TeaSelection;
import com.TeaManagement.TeaManagement.entity.User;
import com.TeaManagement.TeaManagement.entity.enums.Teatime;
import com.TeaManagement.TeaManagement.service.TeaSelectionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            throw new RuntimeException("No tea selections found for teaTime: " + teaTime + " on " + currentDate);
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


}
