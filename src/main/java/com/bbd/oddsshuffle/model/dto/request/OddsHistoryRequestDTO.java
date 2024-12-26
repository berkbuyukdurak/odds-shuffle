package com.bbd.oddsshuffle.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OddsHistoryRequestDTO {
    private UUID matchId;
    private double homeOdds;
    private double drawOdds;
    private double awayOdds;
    private LocalDateTime timestamp;
}