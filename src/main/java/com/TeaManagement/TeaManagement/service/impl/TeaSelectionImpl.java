package com.TeaManagement.TeaManagement.service.impl;

import com.TeaManagement.TeaManagement.dao.TeaSelectionDao;
import com.TeaManagement.TeaManagement.dto.TeaSelectionDto;
import com.TeaManagement.TeaManagement.entity.TeaSelection;
import com.TeaManagement.TeaManagement.service.TeaSelectionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class TeaSelectionImpl implements TeaSelectionService {
    @Autowired
    private TeaSelectionDao teaSelectionDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String saveTeaSelectionOption(TeaSelectionDto teaSelectionDto) {

        TeaSelection teaSelection = modelMapper.map(teaSelectionDto,TeaSelection.class);

        if(!teaSelectionDao.existsById(teaSelection.getSelectionId())){
            teaSelectionDao.save(teaSelection);
            return teaSelection.getUser().getEmpName() + "Order for" +
                    teaSelection.getTeaOptions().getBeverageName() +
                    "recorded successfully.";
        }
        else{
            throw new DuplicateKeyException("Already added");
        }

    }
}
