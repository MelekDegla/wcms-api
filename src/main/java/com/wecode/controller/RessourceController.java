package com.wecode.controller;

import com.wecode.entity.Resource;
import com.wecode.service.RessourceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/resources")
public class RessourceController {

    private final RessourceService ressourceService;

    public RessourceController(RessourceService ressourceService) {
        this.ressourceService = ressourceService;
    }

    @PostMapping
    public Resource save(@RequestBody Resource resource){

        return ressourceService.save(resource);
    }

    @PutMapping
    public Resource update(@RequestBody Resource resource){

        return ressourceService.save(resource);
    }


    @GetMapping("/{id}")
    public Resource findById(@PathVariable Long id ){

        return ressourceService.findById(id);
    }
    @GetMapping
    public List<Resource> findAll(){
        return ressourceService.findAll();
    }

    @DeleteMapping
    public void delete(@PathVariable Long id){

        ressourceService.delete(id);
    }
}


