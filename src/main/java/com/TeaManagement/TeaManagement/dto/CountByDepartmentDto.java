package com.TeaManagement.TeaManagement.dto;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CountByDepartmentDto {

    private String deptName;
    // Map: key is beverage name, value is count
    private Map<String, Integer> beverageCount;

    public CountByDepartmentDto(String deptName, Map<String, Integer> beverageCount) {
        this.deptName = deptName;
        this.beverageCount = beverageCount;
    }


}
