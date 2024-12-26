package com.bbd.oddsshuffle.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchResponseDTO implements Serializable {
    private UUID id;
    private String league;
    private String homeTeam;
    private String awayTeam;
    private double homeOdds;
    private double drawOdds;
    private double awayOdds;
    private LocalDateTime startTime;
}