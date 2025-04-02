package com.TeaManagement.TeaManagement.controller;

import com.TeaManagement.TeaManagement.dto.TeaOptionUpdateDto;
import com.TeaManagement.TeaManagement.dto.TeaOptionsDto;
import com.TeaManagement.TeaManagement.entity.TeaOptions;
import com.TeaManagement.TeaManagement.service.TeaOptionsService;
import com.TeaManagement.TeaManagement.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/teaOptions")
@CrossOrigin(origins = "http://localhost:4200")
public class TeaOptionsController {
    @Autowired
    private TeaOptionsService teaOptionsService;

    @PostMapping(
            path = "/add-Beverages"
    )
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<StandardResponse> addNewBeverageOption(@RequestBody TeaOptionsDto teaOptionsDto){
       String message = teaOptionsService.addNewBeverageOption(teaOptionsDto);

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(201,"Success", message), HttpStatus.CREATED
        );
    }

    @PutMapping(
            path = "/update-beverage"
    )
    @PreAuthorize("hasRole('Admin')")
    public String updateBeverage(@RequestBody TeaOptionUpdateDto teaOptionUpdateDto){
        teaOptionsService.updateBeverage(teaOptionUpdateDto);
        return "Beverage updated Successfully.";

    }

    @GetMapping(
            path = "/get-all-beverages"
    )
    @PreAuthorize("hasAnyRole('Admin','User')")
    public List<TeaOptionUpdateDto> getAllBeverages(){
        List<TeaOptionUpdateDto> allBeverages = teaOptionsService.getAllBeverages();
        return allBeverages;
    }


}
