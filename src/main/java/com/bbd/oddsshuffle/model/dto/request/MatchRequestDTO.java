package com.bbd.oddsshuffle.model.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchRequestDTO {
    @NotBlank(message = "League name cannot be blank")
    private String league;

    @NotBlank(message = "Home team name cannot be blank")
    private String homeTeam;

    @NotBlank(message = "Away team name cannot be blank")
    private String awayTeam;

    @Positive(message = "Home win odds must be a positive value")
    private double homeOdds;

    @Positive(message = "Draw odds must be a positive value")
    private double drawOdds;

    @Positive(message = "Away win odds must be a positive value")
    private double awayOdds;

    @Future(message = "Match start time must be in the future")
    private LocalDateTime startTime;
}