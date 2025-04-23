package com.TeaManagement.TeaManagement.dto;

import com.TeaManagement.TeaManagement.entity.TeaOptions;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CostByDepartmentDto {

    private String deptName;
    private Map<String, BeverageDetail> beverageDetails = new HashMap<>();
    private double totalCost;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BeverageDetail {
        private int count;
        private double cost;
    }


}
