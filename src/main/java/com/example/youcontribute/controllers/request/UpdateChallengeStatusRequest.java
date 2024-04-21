package com.example.youcontribute.controllers.request;

import com.example.youcontribute.model.IssueChallengeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateChallengeStatusRequest {
    private IssueChallengeStatus status;
}
