package com.bbd.oddsshuffle.model.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public class OddsHistoryResponseDTO {
    private UUID id;
    private UUID matchId;
    private double homeOdds;
    private double drawOdds;
    private double awayOdds;
    private LocalDateTime timestamp;

    // Getters and Setters
}