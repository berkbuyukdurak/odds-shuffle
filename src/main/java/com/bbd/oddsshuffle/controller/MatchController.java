package com.bbd.oddsshuffle.controller;

import com.bbd.oddsshuffle.model.dto.request.MatchRequestDTO;
import com.bbd.oddsshuffle.model.dto.response.MatchResponseDTO;
import com.bbd.oddsshuffle.service.MatchService;
import com.bbd.oddsshuffle.util.GenericResponseHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping
    public ResponseEntity<Object> addMatch(@RequestBody @Valid MatchRequestDTO requestDto) {
        MatchResponseDTO response = matchService.addMatch(requestDto);
        if (ObjectUtils.isEmpty(response)) {
            return GenericResponseHandler.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
        return GenericResponseHandler.successResponse(HttpStatus.CREATED, response);
    }

    @GetMapping
    public ResponseEntity<Object> getAllMatches() {
        List<MatchResponseDTO> response = matchService.getAllMatches();
        if (ObjectUtils.isEmpty(response)) {
            return GenericResponseHandler.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
        return GenericResponseHandler.successResponse(HttpStatus.OK, response);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<Object> getUpcomingMatches() {
        List<MatchResponseDTO> response = matchService.getUpcomingMatches();
        if (ObjectUtils.isEmpty(response)) {
            return GenericResponseHandler.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
        return GenericResponseHandler.successResponse(HttpStatus.OK, response);
    }

    @GetMapping("/league/{league}")
    public ResponseEntity<Object> getMatchesByLeague(@PathVariable String league) {
        List<MatchResponseDTO> response = matchService.getMatchesByLeague(league);
        if (ObjectUtils.isEmpty(response)) {
            return GenericResponseHandler.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
        return GenericResponseHandler.successResponse(HttpStatus.OK, response);
    }
}
