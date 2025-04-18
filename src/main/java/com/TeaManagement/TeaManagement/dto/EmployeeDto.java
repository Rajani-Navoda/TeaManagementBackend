package com.TeaManagement.TeaManagement.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeDto {

    private String empNo;
    private String empName;
    private String empDepartment;
    private String empEmail;
    private String empPassword;

}
