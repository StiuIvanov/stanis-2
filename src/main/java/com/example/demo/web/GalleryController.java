package com.example.demo.web;

import com.example.demo.model.dto.ParentDTO;
import com.example.demo.service.ParentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GalleryController {

    private final ParentService parentService;

    public GalleryController(ParentService parentService) {
        this.parentService = parentService;
    }




    @GetMapping("/gallery/info")
    @ResponseBody
    public ResponseEntity<List<ParentDTO>> getInfoForParents(){
        List<ParentDTO> allParents = parentService.getAllParents();

        return ResponseEntity
                .ok(allParents);
    }
}
