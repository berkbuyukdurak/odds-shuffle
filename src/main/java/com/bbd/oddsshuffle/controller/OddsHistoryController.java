package com.bbd.oddsshuffle.controller;

import com.bbd.oddsshuffle.model.dto.response.MatchOddsHistoryDTO;
import com.bbd.oddsshuffle.service.OddsHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/odds-history")
public class OddsHistoryController {

    private final OddsHistoryService oddsHistoryService;

    public OddsHistoryController(OddsHistoryService oddsHistoryService) {
        this.oddsHistoryService = oddsHistoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<MatchOddsHistoryDTO>> getAllMatchesWithOddsHistory() {
        List<MatchOddsHistoryDTO> matchOddsHistory = oddsHistoryService.getAllMatchesWithOddsHistory();
        return ResponseEntity.ok(matchOddsHistory);
    }
}