package com.TeaManagement.TeaManagement.service;

import com.TeaManagement.TeaManagement.dto.ActiveSessionDto;
import com.TeaManagement.TeaManagement.dto.CountByBeverageDto;
import com.TeaManagement.TeaManagement.dto.CountByDepartmentDto;
import com.TeaManagement.TeaManagement.dto.TeaSelectionDto;
import com.TeaManagement.TeaManagement.entity.enums.Teatime;

import java.time.LocalDate;
import java.util.List;

public interface TeaSelectionService {

    public String saveTeaSelectionOption(TeaSelectionDto teaSelectionDto);

    public List<CountByBeverageDto> countByBeverages(Teatime teaTime);

    public List<CountByDepartmentDto> countByDepartmentSAndBeverage(Teatime teatime);

    public ActiveSessionDto getCurrentSessionStatus();
}
