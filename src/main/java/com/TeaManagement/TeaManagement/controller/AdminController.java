package com.TeaManagement.TeaManagement.controller;

import com.TeaManagement.TeaManagement.dto.DepartmentDto;
import com.TeaManagement.TeaManagement.dto.TeaOptionUpdateDto;
import com.TeaManagement.TeaManagement.service.AdminService;
import com.TeaManagement.TeaManagement.service.UserService;
import com.TeaManagement.TeaManagement.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping(path = "/add-Department")
    public ResponseEntity<StandardResponse> addNewDepartment(@RequestBody DepartmentDto departmentDto) {
        String message = adminService.addNewDepartment(departmentDto);
        return new ResponseEntity<>(new StandardResponse(201, "Success", message), HttpStatus.CREATED);
    }


    @GetMapping(
            path = "/get-all-Departments"
    )
//    @PreAuthorize("hasAnyRole('Admin','User')")
    public List<DepartmentDto> getAllDepartments(){
        List<DepartmentDto> allDepartments = adminService.getAllDepartments();
        return allDepartments;
    }
}
