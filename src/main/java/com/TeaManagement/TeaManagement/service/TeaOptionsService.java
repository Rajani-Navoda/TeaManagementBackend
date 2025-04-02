package com.TeaManagement.TeaManagement.service;

import com.TeaManagement.TeaManagement.dto.TeaOptionUpdateDto;
import com.TeaManagement.TeaManagement.dto.TeaOptionsDto;

import java.util.List;

public interface TeaOptionsService {

    public String addNewBeverageOption(TeaOptionsDto teaOptionsDto);

    public String updateBeverage(TeaOptionUpdateDto teaOptionUpdateDto);

    public List<TeaOptionUpdateDto> getAllBeverages();
}
