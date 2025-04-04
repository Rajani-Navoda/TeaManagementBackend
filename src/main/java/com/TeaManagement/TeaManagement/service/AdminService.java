package com.TeaManagement.TeaManagement.service;

import com.TeaManagement.TeaManagement.dto.DepartmentDto;
import com.TeaManagement.TeaManagement.dto.TeaOptionUpdateDto;

import java.util.List;

public interface AdminService {

    public String addNewDepartment(DepartmentDto departmentDto);

    public List<DepartmentDto> getAllDepartments();
}
