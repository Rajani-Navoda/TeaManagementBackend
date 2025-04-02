package com.TeaManagement.TeaManagement.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TeaOptionUpdateDto {

    private int beverageId;
    private String beverageName;
    private double unitPrice;
}
