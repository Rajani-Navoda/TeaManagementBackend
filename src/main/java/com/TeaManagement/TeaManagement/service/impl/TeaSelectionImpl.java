package com.TeaManagement.TeaManagement.service.impl;

import com.TeaManagement.TeaManagement.dao.TeaOptionsDao;
import com.TeaManagement.TeaManagement.dao.TeaSelectionDao;
import com.TeaManagement.TeaManagement.dao.UserDao;
import com.TeaManagement.TeaManagement.dto.TeaOptionsDto;
import com.TeaManagement.TeaManagement.dto.TeaSelectionDto;
import com.TeaManagement.TeaManagement.entity.TeaOptions;
import com.TeaManagement.TeaManagement.entity.TeaSelection;
import com.TeaManagement.TeaManagement.entity.User;
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
}
