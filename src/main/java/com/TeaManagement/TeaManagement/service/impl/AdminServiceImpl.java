package com.TeaManagement.TeaManagement.service.impl;

import com.TeaManagement.TeaManagement.dao.DepartmentDao;
import com.TeaManagement.TeaManagement.dto.DepartmentDto;
import com.TeaManagement.TeaManagement.dto.TeaOptionUpdateDto;
import com.TeaManagement.TeaManagement.entity.Department;
import com.TeaManagement.TeaManagement.entity.TeaOptions;
import com.TeaManagement.TeaManagement.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    DepartmentDao departmentDao;

    @Override
    public String addNewDepartment(DepartmentDto departmentDto) {

        try {

            Department department = modelMapper.map(departmentDto, Department.class);

            // Check if a department with the same name already exists
            Department existingDepartment = departmentDao.findByDeptName(department.getDeptName());
            if (existingDepartment != null) {
                throw new RuntimeException("Department with name '"
                        + department.getDeptName() + "' already exists.");
            }

            // Save the new department
            departmentDao.save(department);
            return department.getDeptName() + " added successfully.";
        } catch (Exception e) {
            System.out.println("Error adding new department: " + e);
            throw new RuntimeException("Failed to add new department: " + e.getMessage(), e);
        }

    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = departmentDao.findAll();
        if (!departments.isEmpty()) {
            List<DepartmentDto> departmentDtoList = new ArrayList<>();
            for (Department department : departments) {
                DepartmentDto departmentDto = modelMapper.map(department, DepartmentDto.class);
                departmentDtoList.add(departmentDto);
            }
            return departmentDtoList;
        } else {
            throw new NotFoundException("No departments found");
        }
    }


}
