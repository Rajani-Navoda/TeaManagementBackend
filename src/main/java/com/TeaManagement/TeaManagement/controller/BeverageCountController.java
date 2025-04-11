package com.TeaManagement.TeaManagement.controller;

import com.TeaManagement.TeaManagement.dto.CountByBeverageDto;
import com.TeaManagement.TeaManagement.dto.TeaOptionUpdateDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/beverageCount")
@CrossOrigin(origins = "http://localhost:4200")
public class BeverageCountController {

//    @GetMapping(
//            path = "/get-count-by-beverages"
//    )
//    @PreAuthorize("hasRole('Admin')")
//    public List<CountByBeverageDto> countAllByBeverage(){
//
//    }

}
