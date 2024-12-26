package com.bbd.oddsshuffle.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchOddsHistoryDTO {
    private MatchResponseDTO match;
    private List<OddsHistoryResponseDTO> oddsHistory;
}