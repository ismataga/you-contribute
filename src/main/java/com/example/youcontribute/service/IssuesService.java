package com.example.youcontribute.service;

import com.example.youcontribute.repositories.IssuesRepository;
import com.example.youcontribute.model.Issue;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class IssuesService {
    private final IssuesRepository issueRepository;
    @Transactional
    public void saveAll(List<Issue> issues) {
        this.issueRepository.saveAll(issues);
    }
}
