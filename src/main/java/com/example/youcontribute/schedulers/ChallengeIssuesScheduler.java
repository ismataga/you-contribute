package com.example.youcontribute.schedulers;

import com.example.youcontribute.client.OneSignalClient;
import com.example.youcontribute.manager.RepositoryManager;
import com.example.youcontribute.model.Issue;
import com.example.youcontribute.model.IssueChallenge;
import com.example.youcontribute.model.Repository;
import com.example.youcontribute.service.IssueChallengeService;
import com.example.youcontribute.service.IssuesService;
import com.example.youcontribute.service.RepositoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
@Slf4j
public class ChallengeIssuesScheduler {

    private final IssuesService issuesService;
    private final IssueChallengeService issueChallengeService;

    private final OneSignalClient oneSignalClient;
    @Scheduled(fixedRateString = "${application.challenge-frequency}")
    public  void  importIssuesScheduler(){
        log.info("Challenge IssuesScheduler  started");
        if(this.issueChallengeService.hasOngoingChallenge()){
            log.warn("There is already has ongoing challenge), skipping...");
            return;
        }

        Issue randomIssue = this.issuesService.findRandomIssue();
        log.info("Found random issue {}",randomIssue.getId());
        IssueChallenge issueChallenge = issueChallengeService.create(randomIssue);
        oneSignalClient.sendNotification(issueChallenge.getId(),randomIssue.getTitle());
        log.info("Challenge IssuesScheduler  finished");


    }
}
