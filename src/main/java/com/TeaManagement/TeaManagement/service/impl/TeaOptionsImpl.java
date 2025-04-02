package com.TeaManagement.TeaManagement.service.impl;

import com.TeaManagement.TeaManagement.dao.TeaOptionsDao;
import com.TeaManagement.TeaManagement.dto.TeaOptionUpdateDto;
import com.TeaManagement.TeaManagement.dto.TeaOptionsDto;
import com.TeaManagement.TeaManagement.entity.TeaOptions;
import com.TeaManagement.TeaManagement.entity.TeaSelection;
import com.TeaManagement.TeaManagement.service.TeaOptionsService;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeaOptionsImpl implements TeaOptionsService {

    @Autowired
    private TeaOptionsDao teaOptionsDao;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public String addNewBeverageOption(TeaOptionsDto teaOptionsDto) {

        try {
            TeaOptions teaOptions = modelMapper.map(teaOptionsDto, TeaOptions.class);


            TeaOptions existingBeverage = teaOptionsDao.findByBeverageName(teaOptions.getBeverageName());
            if (existingBeverage != null) {
                throw new RuntimeException("Beverage with name '" + teaOptions.getBeverageName() +
                        "' already exists.");
            }

            teaOptionsDao.save(teaOptions);
            return teaOptions.getBeverageName() + " added successfully.";

        } catch (Exception e) {
            System.out.println("Error adding new beverage option: " + e);
            throw new RuntimeException("Failed to add new beverage option: " + e.getMessage(), e);
        }
    }

    @Override
    public String updateBeverage(TeaOptionUpdateDto teaOptionUpdateDto) {
       if(teaOptionsDao.existsById(teaOptionUpdateDto.getBeverageId())){
           TeaOptions teaOption = teaOptionsDao.getReferenceById(teaOptionUpdateDto.getBeverageId());
           teaOption.setBeverageName(teaOptionUpdateDto.getBeverageName());
           teaOption.setUnitPrice(teaOptionUpdateDto.getUnitPrice());
           teaOptionsDao.save(teaOption);
           return teaOptionUpdateDto.getBeverageName() + " update successfully";
       }else{
           throw new RuntimeException("No data");
       }
    }

    @Override
    public List<TeaOptionUpdateDto> getAllBeverages() {
       List<TeaOptions> getAllBeverages = teaOptionsDao.findAll();
       if(!getAllBeverages.isEmpty()){
           List<TeaOptionUpdateDto> teaOptionList = new ArrayList<>();
           for(TeaOptions teaOptions: getAllBeverages){

               TeaOptionUpdateDto teaOptionUpdateDto = new TeaOptionUpdateDto(
                       teaOptions.getBeverageId(),
                       teaOptions.getBeverageName(),
                       teaOptions.getUnitPrice()
               );
               teaOptionList.add(teaOptionUpdateDto);

           }
           return teaOptionList;

       }else{
           throw new NotFoundException("No beverages found");
       }

    }

}
