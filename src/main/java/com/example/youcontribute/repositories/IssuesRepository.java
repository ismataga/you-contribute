package com.example.youcontribute.repositories;

import com.example.youcontribute.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IssuesRepository extends PagingAndSortingRepository<Issue, Integer>, JpaRepository<Issue, Integer> {
    List<Issue> findAll();

}
