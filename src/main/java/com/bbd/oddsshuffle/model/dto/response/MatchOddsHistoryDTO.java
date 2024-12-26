package com.bbd.oddsshuffle.model.dto.response;

import java.util.List;

public class MatchOddsHistoryDTO {
    private MatchResponseDTO match;
    private List<OddsHistoryResponseDTO> oddsHistory;

    public MatchOddsHistoryDTO(MatchResponseDTO match, List<OddsHistoryResponseDTO> oddsHistory) {
        this.match = match;
        this.oddsHistory = oddsHistory;
    }

    public MatchResponseDTO getMatch() {
        return match;
    }

    public void setMatch(MatchResponseDTO match) {
        this.match = match;
    }

    public List<OddsHistoryResponseDTO> getOddsHistory() {
        return oddsHistory;
    }

    public void setOddsHistory(List<OddsHistoryResponseDTO> oddsHistory) {
        this.oddsHistory = oddsHistory;
    }
}