package com.TeaManagement.TeaManagement.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CountByBeverageDto {

    private String beverageName;
    private int count;

}
