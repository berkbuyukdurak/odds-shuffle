package com.bbd.oddsshuffle.model.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

public class OddsHistoryRequestDTO {
    private UUID matchId;
    private double homeOdds;
    private double drawOdds;

    public UUID getMatchId() {
        return matchId;
    }

    public double getHomeOdds() {
        return homeOdds;
    }

    public void setMatchId(UUID matchId) {
        this.matchId = matchId;
    }

    public void setAwayOdds(double awayOdds) {
        this.awayOdds = awayOdds;
    }

    public void setDrawOdds(double drawOdds) {
        this.drawOdds = drawOdds;
    }

    public void setHomeOdds(double homeOdds) {
        this.homeOdds = homeOdds;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getDrawOdds() {
        return drawOdds;
    }

    public double getAwayOdds() {
        return awayOdds;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    private double awayOdds;
    private LocalDateTime timestamp;

    public OddsHistoryRequestDTO(UUID matchId, double homeOdds, double drawOdds, double awayOdds, LocalDateTime timestamp) {
        this.matchId = matchId;
        this.homeOdds = homeOdds;
        this.drawOdds = drawOdds;
        this.awayOdds = awayOdds;
        this.timestamp = timestamp;
    }
}