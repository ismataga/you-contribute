package com.example.youcontribute.service;

import com.example.youcontribute.model.Repository;
import com.example.youcontribute.repositories.IssuesRepository;
import com.example.youcontribute.model.Issue;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class IssuesService {
    private final IssuesRepository issueRepository;
    private final RepositoryService repositoryService;

    @Transactional
    public void saveAll(List<Issue> issues) {
        this.issueRepository.saveAll(issues);
    }

    public List<Issue> list(Integer repositoryId) {
        Repository repository = repositoryService.findById(repositoryId);
        return this.issueRepository.findByRepository(repository);
    }

    public Issue findRandomIssue() {
        return this.issueRepository.findRandomIssue().orElseThrow(()->new EntityNotFoundException("No such issue"));
    }
}
