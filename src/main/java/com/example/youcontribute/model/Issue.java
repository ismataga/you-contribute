package com.example.youcontribute.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;


@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long githubIssueId;
    private Integer githubIssueNumber;

    private String title;
    @Column(columnDefinition = "text", length = 65535)
    private String body;
    private String url;

    @ManyToOne
    @JoinColumn
    @JsonManagedReference
    private Repository repository;


}

