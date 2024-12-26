package com.bbd.oddsshuffle.controller;

import com.bbd.oddsshuffle.model.dto.response.MatchOddsHistoryDTO;
import com.bbd.oddsshuffle.service.OddsHistoryService;
import com.bbd.oddsshuffle.util.GenericResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
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
    public ResponseEntity<Object> getAllMatchesWithOddsHistory() {
        List<MatchOddsHistoryDTO> matchOddsHistory = oddsHistoryService.getAllMatchesWithOddsHistory();
        if (ObjectUtils.isEmpty(matchOddsHistory)) {
            return GenericResponseHandler.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
        return GenericResponseHandler.successResponse(HttpStatus.OK, matchOddsHistory);
    }
}