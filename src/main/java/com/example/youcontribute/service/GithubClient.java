package com.example.youcontribute.service;

import com.example.youcontribute.config.GithubProperties;
import com.example.youcontribute.service.models.GithubIssueResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;


@Service
@AllArgsConstructor
public class GithubClient {

    private final RestTemplate restTemplate;

    private final GithubProperties githubProperties;


    public GithubIssueResponse[] listIssues(String owner, String repository, LocalDate since) {
        String issuesUrl = String.format("%s/repos/%s/%s/issues?since=%s", this.githubProperties.getApiUrl(), owner, repository,since.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "token "+ this.githubProperties.getToken());
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<GithubIssueResponse[]> response = this.restTemplate.exchange(issuesUrl, HttpMethod.GET, request, GithubIssueResponse[].class);
        return response.getBody();
    }
}
