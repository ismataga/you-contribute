package com.example.youcontribute.service;

import com.example.youcontribute.model.Issue;
import com.example.youcontribute.model.IssueChallenge;
import com.example.youcontribute.model.IssueChallengeStatus;
import com.example.youcontribute.repositories.IssueChallengeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@AllArgsConstructor
@Service
public class IssueChallengeService {
    private final IssueChallengeRepository issueChallengeRepository;

    @Transactional
    public IssueChallenge create(Issue issue) {
        IssueChallenge challenge = IssueChallenge.builder().issue(issue).status(IssueChallengeStatus.PENDING)
                .build();
        return this.issueChallengeRepository.save(challenge);
    }

    public Boolean hasOngoingChallenge() {
        return this.issueChallengeRepository.findByStatusIn(IssueChallengeStatus.ongoingStatuses()).isPresent();
    }

    public void updateStatus(Integer id, IssueChallengeStatus status) {
        IssueChallenge issueChallenge = this.issueChallengeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Issue Challenge " + id + " not found"));
        issueChallenge.setStatus(status);
        this.issueChallengeRepository.save(issueChallenge);
    }

    public List<IssueChallenge> list() {
        return this.issueChallengeRepository.findAll();
    }

}
