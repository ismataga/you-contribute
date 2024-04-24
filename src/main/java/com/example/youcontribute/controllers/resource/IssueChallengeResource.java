package com.example.youcontribute.controllers.resource;

import com.example.youcontribute.model.IssueChallenge;
import com.example.youcontribute.model.IssueChallengeStatus;
import com.example.youcontribute.model.Repository;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class IssueChallengeResource {
    private Integer id;
    private Integer issueId;
    private Integer repositoryId;
    private String repositoryTitle;
    private String issueTitle;
    private Date creationDate;
    private IssueChallengeStatus status;

    public static IssueChallengeResource createFor(IssueChallenge issueChallenge) {
        return IssueChallengeResource.builder()
                .id(issueChallenge.getId())
                .issueId(issueChallenge.getIssue().getId())
                .repositoryId(issueChallenge.getIssue().getRepository().getId())
                .issueTitle(issueChallenge.getIssue().getTitle())
                .repositoryTitle(issueChallenge.getIssue().getRepository().getRepository())
                .creationDate(issueChallenge.getCreatedAt())
                .status(issueChallenge.getStatus())
                .build();
    }

    public static List<IssueChallengeResource> createFor(List<IssueChallenge> issueChallenges) {
        return issueChallenges.stream().map(IssueChallengeResource::createFor).collect(Collectors.toList());

    }
}
