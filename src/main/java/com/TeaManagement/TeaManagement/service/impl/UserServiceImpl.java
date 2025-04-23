package com.TeaManagement.TeaManagement.service.impl;

import com.TeaManagement.TeaManagement.dao.RoleDao;
import com.TeaManagement.TeaManagement.dao.UserDao;
import com.TeaManagement.TeaManagement.dto.DepartmentDto;
import com.TeaManagement.TeaManagement.dto.EmployeeDto;
import com.TeaManagement.TeaManagement.dto.TeaOptionUpdateDto;
import com.TeaManagement.TeaManagement.entity.Role;
import com.TeaManagement.TeaManagement.entity.TeaOptions;
import com.TeaManagement.TeaManagement.entity.User;
import com.TeaManagement.TeaManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User registerNewUser(User user){
        System.out.println(user);
        Role role = roleDao.findById("User").get();

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRole(roles);
        user.setEmpPassword(getEncodedPassword(user.getEmpPassword()));
        return userDao.save(user);
    }

    public void initRolesAndUser(){

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role for the system");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("default role for newly created record");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setEmpNo("Admin001");
        adminUser.setEmpName("John Doe");
        adminUser.setEmpDepartment("Admin");
        adminUser.setEmpEmail("john.doe@example.com");
        adminUser.setEmpPassword(getEncodedPassword("admin@123"));
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

    }

    public String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }

    @Override
    public EmployeeDto updateEmployee(String empNo, EmployeeDto employeeDto) {
        User existingUser = userDao.findById(empNo)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setEmpName(employeeDto.getEmpName());
        existingUser.setEmpDepartment(employeeDto.getEmpDepartment());
        existingUser.setEmpEmail(employeeDto.getEmpEmail());

        if (employeeDto.getEmpPassword() != null && !employeeDto.getEmpPassword().isEmpty()) {
            existingUser.setEmpPassword(getEncodedPassword(employeeDto.getEmpPassword()));
        }

        User updatedUser = userDao.save(existingUser);

        return convertToDto(updatedUser);
    }

    @Override
    public String deleteCustomer(String empNo) {
        if(userDao.existsById(empNo)){
            userDao.deleteById(empNo);
            return "Employee "+ empNo +  " deleted successfully";
        }else{
            throw new RuntimeException("Employee not found");
        }
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<User> users = (List<User>) userDao.findAll();
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }




    private EmployeeDto convertToDto(User user) {
        EmployeeDto dto = new EmployeeDto();
        dto.setEmpNo(user.getEmpNo());
        dto.setEmpName(user.getEmpName());
        dto.setEmpDepartment(user.getEmpDepartment());
        dto.setEmpEmail(user.getEmpEmail());
        dto.setEmpPassword(user.getEmpPassword());
        return dto;
    }

}
