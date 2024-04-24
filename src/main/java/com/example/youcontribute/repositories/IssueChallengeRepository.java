package com.example.youcontribute.repositories;

import com.example.youcontribute.model.IssueChallenge;
import com.example.youcontribute.model.IssueChallengeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface IssueChallengeRepository extends PagingAndSortingRepository<IssueChallenge, Integer>, JpaRepository<IssueChallenge, Integer> {

    Optional<IssueChallenge> findByStatusIn(List<IssueChallengeStatus> status);

    @Override
    List<IssueChallenge> findAll();
}
