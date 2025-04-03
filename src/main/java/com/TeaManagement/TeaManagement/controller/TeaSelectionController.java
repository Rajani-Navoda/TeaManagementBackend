package com.TeaManagement.TeaManagement.controller;

import com.TeaManagement.TeaManagement.dto.TeaSelectionDto;
import com.TeaManagement.TeaManagement.service.TeaSelectionService;
import com.TeaManagement.TeaManagement.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/teaSelection")
@CrossOrigin(origins = "http://localhost:4200")
public class TeaSelectionController {

    @Autowired
    private TeaSelectionService teaSelectionService;

    @PostMapping("/selectTeaOption")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<StandardResponse> selectTeaOption(@RequestBody TeaSelectionDto teaSelectionDto){
        String selectTea = teaSelectionService.saveTeaSelectionOption(teaSelectionDto);
        return new ResponseEntity<>(new StandardResponse(201, "Success", selectTea), HttpStatus.CREATED);
    }
}
