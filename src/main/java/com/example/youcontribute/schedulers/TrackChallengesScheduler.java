package com.example.youcontribute.schedulers;

import com.example.youcontribute.client.OneSignalClient;
import com.example.youcontribute.model.Issue;
import com.example.youcontribute.model.IssueChallenge;
import com.example.youcontribute.model.IssueChallengeStatus;
import com.example.youcontribute.model.Repository;
import com.example.youcontribute.service.GithubClient;
import com.example.youcontribute.service.IssueChallengeService;
import com.example.youcontribute.service.IssuesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@AllArgsConstructor
@Component
@Slf4j
public class TrackChallengesScheduler {

    private final IssueChallengeService issueChallengeService;

    private final GithubClient githubClient;

    @Scheduled(fixedRateString = "${application.challenge-tracking-frequency}")
    public void challengeIssuesScheduler() {
        log.info("Challenge IssuesScheduler  started");

        this.issueChallengeService.list()
                .stream()
                .filter(issueChallenge -> IssueChallengeStatus.ACCEPTED.equals(issueChallenge.getStatus()))
                .forEach(issueChallenge -> {
                    Repository repository = issueChallenge.getIssue().getRepository();
                    Arrays.stream(this.githubClient
                                    .listPullRequests(repository.getOrganization(), repository.getRepository()))
                            .filter(pull -> "ismataga".equals(pull.getUser().getLogin()) &&
                                    pull.getBody().contains(String.format("Fixes #id", issueChallenge.getIssue().getGithubIssueNumber()))
                                    && "open".equals(pull.getState()))
                            .findFirst()
                            .ifPresent(pullResponse -> {
                                this.issueChallengeService.updateStatus(issueChallenge.getId(), IssueChallengeStatus.COMPLETED);
                            });
                });


        log.info("Challenge IssuesScheduler  finished");


    }
}
