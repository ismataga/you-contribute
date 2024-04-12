package com.example.youcontribute.manager;

import com.example.youcontribute.model.Repository;
import com.example.youcontribute.service.GithubClient;
import com.example.youcontribute.service.IssuesService;
import com.example.youcontribute.service.RepositoryService;
import com.example.youcontribute.service.models.GithubIssueResponse;
import com.example.youcontribute.model.Issue;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RepositoryManager {

    private final RepositoryService repositoryService;

    private final IssuesService issuesService;

    private final GithubClient githubClient;

    public void importRepository(String organization, String repository) {
        this.repositoryService.create(organization, repository);
    }

    @Async
    public void importIssue(Repository repository) {
        LocalDate sinceLastDay = LocalDate.ofInstant(Instant.now().minus(1, ChronoUnit.DAYS), ZoneId.systemDefault());
        GithubIssueResponse[] githubIssueResponses = this.githubClient
                .listIssues(repository.getOrganization(), repository.getRepository(), sinceLastDay);


        List<Issue> issues = Arrays.stream(githubIssueResponses)
                .map(githubIssue -> Issue.builder().title(githubIssue.getTitle()).body(githubIssue.getBody()).build()).collect(Collectors.toList());

        this.issuesService.saveAll(issues);

    }
}



