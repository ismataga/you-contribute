package com.example.youcontribute.controllers.resource;

import com.example.youcontribute.model.Issue;
import com.example.youcontribute.model.Repository;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class IssueResource {
    private Integer id;
    private Long githubIssueId;
    private String title;
    private String body;
    private String url;

    public static IssueResource createFor(Issue issue) {
        return IssueResource.builder()
                .id(issue.getId())
                .title(issue.getTitle())
                .body(issue.getBody())
                .githubIssueId(issue.getGithubIssueId())
                .url(issue.getUrl())
                .build();
    }

    public static List<IssueResource> createFor(List<Issue> issues) {
        return issues.stream().map(IssueResource::createFor).collect(Collectors.toList());

    }
}
