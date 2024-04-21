package com.example.youcontribute.controllers;


import com.example.youcontribute.controllers.request.UpdateChallengeStatusRequest;
import com.example.youcontribute.controllers.resource.IssueResource;
import com.example.youcontribute.model.IssueChallenge;
import com.example.youcontribute.service.IssueChallengeService;
import com.example.youcontribute.service.IssuesService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/challenges")
public class IssueChallengeController {

    private final IssueChallengeService issueChallengeService;

    public IssueChallengeController(IssueChallengeService issueChallengeService) {
        this.issueChallengeService = issueChallengeService;
    }


    @PatchMapping("/{id}")
    public void  updateStatus(@PathVariable("id") Integer id , @RequestBody UpdateChallengeStatusRequest request) {
         this.issueChallengeService.updateStatus(id, request.getStatus());
    }

    @GetMapping
    public List<IssueChallenge> list(){
        this.issueChallengeService.list();
    }
}
