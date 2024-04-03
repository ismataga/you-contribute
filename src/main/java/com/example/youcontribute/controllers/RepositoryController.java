package com.example.youcontribute.controllers;


import com.example.youcontribute.controllers.request.CreateRepositoryRequest;
import com.example.youcontribute.controllers.resource.RepositoryResource;
import com.example.youcontribute.service.RepositoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/repositories")
public class RepositoryController {

    private final RepositoryService repositoryService;

    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void created(@RequestBody CreateRepositoryRequest request) {
        this.repositoryService.create(request.getOrganization(), request.getRepository());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<RepositoryResource> list() {
        return RepositoryResource.createFor(repositoryService.list());
    }
}
