package com.example.youcontribute.controllers;


import com.example.youcontribute.controllers.request.CreateRepositoryRequest;
import com.example.youcontribute.controllers.resource.IssueResource;
import com.example.youcontribute.controllers.resource.RepositoryResource;
import com.example.youcontribute.service.IssuesService;
import com.example.youcontribute.service.RepositoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssueController {

    private final IssuesService issuesService;

    public IssueController(IssuesService issuesService) {
        this.issuesService = issuesService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<IssueResource> list(@RequestParam("repository_Id") Integer repositoryId) {
        return IssueResource.createFor(issuesService.list(repositoryId));
    }
}
