package com.bbd.oddsshuffle.controller;

import com.bbd.oddsshuffle.model.dto.request.MatchRequestDTO;
import com.bbd.oddsshuffle.model.dto.response.MatchResponseDTO;
import com.bbd.oddsshuffle.service.match.MatchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping
    public ResponseEntity<MatchResponseDTO> addMatch(@RequestBody @Valid MatchRequestDTO requestDto) {
        MatchResponseDTO responseDto = matchService.addMatch(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<MatchResponseDTO>> getAllMatches() {
        List<MatchResponseDTO> matches = matchService.getAllMatches();
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<MatchResponseDTO>> getUpcomingMatches() {
        List<MatchResponseDTO> matches = matchService.getUpcomingMatches();
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/league/{league}")
    public ResponseEntity<List<MatchResponseDTO>> getMatchesByLeague(@PathVariable String league) {
        List<MatchResponseDTO> matches = matchService.getMatchesByLeague(league);
        return ResponseEntity.ok(matches);
    }
}
