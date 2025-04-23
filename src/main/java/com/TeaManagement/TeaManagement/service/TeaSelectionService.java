package com.TeaManagement.TeaManagement.service;

import com.TeaManagement.TeaManagement.dto.*;
import com.TeaManagement.TeaManagement.entity.enums.Teatime;

import java.time.LocalDate;
import java.util.List;

public interface TeaSelectionService {

    public String saveTeaSelectionOption(TeaSelectionDto teaSelectionDto);

    public List<CountByBeverageDto> countByBeverages(Teatime teaTime);

    public List<CountByDepartmentDto> countByDepartmentSAndBeverage(Teatime teatime);

    public ActiveSessionDto getCurrentSessionStatus();

    public List<CostByDepartmentDto>  getMonthlyCostByDepartment(String month, int year);
}
