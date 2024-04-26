package com.example.youcontribute.repositories;

import com.example.youcontribute.model.Issue;
import com.example.youcontribute.model.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface IssuesRepository extends PagingAndSortingRepository<Issue, Integer>, JpaRepository<Issue, Integer> {
    List<Issue> findAll();

    List<Issue> findByRepository(Repository repository);

    @Query(value = "select * from issue where id not in (select issue.id from issue challenge) " +
            "order by rand() limit 1", nativeQuery = true)
    Optional<Issue> findRandomIssue();

    Optional<Issue> findByGithubIssueId(Long githubIssueId);
 }
