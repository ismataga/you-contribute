package com.example.youcontribute.manager;

import com.example.youcontribute.config.ApplicationProperties;
import com.example.youcontribute.config.GithubProperties;
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
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RepositoryManager {

    private final RepositoryService repositoryService;

    private final IssuesService issuesService;

    private final GithubClient githubClient;
    private final ApplicationProperties applicationProperties;

    public void importRepository(String organization, String repository) {
        this.repositoryService.create(organization, repository);
    }

    @Async
    public void importIssue(Repository repository) {
        int schedulerFrequencyInMinutes = applicationProperties.getImportFrequency() / 60000;

        LocalDate since = LocalDate.ofInstant(Instant.now().minus(schedulerFrequencyInMinutes, ChronoUnit.MINUTES),
                ZoneOffset.UTC);

        GithubIssueResponse[] githubIssueResponses = this.githubClient
                .listIssues(repository.getOrganization(), repository.getRepository(), since);


        List<Issue> issues = Arrays.stream(githubIssueResponses)
                .filter(githubIssue -> Objects.isNull(githubIssue.getPullRequest()))
                .map(githubIssue -> Issue.builder()
                        .repository(repository)
                        .githubIssueId(githubIssue.getId())
                        .githubIssueNumber(githubIssue.getNumber())
                        .url(githubIssue.getHtmlUrl())
                        .title(githubIssue.getTitle())
                        .body(githubIssue.getBody())
                        .build()).collect(Collectors.toList());

        this.issuesService.saveAll(issues);

    }
}



