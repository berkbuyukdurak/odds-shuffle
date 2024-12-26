package com.bbd.oddsshuffle.service;

import com.bbd.oddsshuffle.handler.MatchWebSocketHandler;
import com.bbd.oddsshuffle.model.dto.request.OddsHistoryRequestDTO;
import com.bbd.oddsshuffle.model.dto.response.MatchResponseDTO;
import com.bbd.oddsshuffle.model.entity.Match;
import com.bbd.oddsshuffle.repository.MatchRepository;
import com.bbd.oddsshuffle.util.OddsGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class OddsService {

    private final MatchRepository matchRepository;
    private final OddsHistoryService oddsHistoryService;
    private final OddsGenerator oddsGenerator;
    private final MatchWebSocketHandler matchWebSocketHandler;
    private final ObjectMapper objectMapper;

    public OddsService(MatchRepository matchRepository, OddsHistoryService oddsHistoryService, OddsGenerator oddsGenerator, MatchWebSocketHandler matchWebSocketHandler, ObjectMapper objectMapper) {
        this.matchRepository = matchRepository;
        this.oddsHistoryService = oddsHistoryService;
        this.oddsGenerator = oddsGenerator;
        this.matchWebSocketHandler = matchWebSocketHandler;
        this.objectMapper = objectMapper;
    }

    public void updateOdds() throws JsonProcessingException {
        List<Match> matches = matchRepository.findAll();
        List<MatchResponseDTO> matchResponseDTOs = new ArrayList<>();

        for (Match match : matches) {
            double[] newOdds = oddsGenerator.generateOdds();
            match.setHomeOdds(newOdds[0]);
            match.setDrawOdds(newOdds[1]);
            match.setAwayOdds(newOdds[2]);
            matchRepository.save(match);

            OddsHistoryRequestDTO oddsHistoryRequestDTO = new OddsHistoryRequestDTO(
                    match.getMatchId(),
                    match.getHomeOdds(),
                    match.getDrawOdds(),
                    match.getAwayOdds(),
                    LocalDateTime.now()
            );
            oddsHistoryService.addOddsHistory(match, oddsHistoryRequestDTO);

            MatchResponseDTO matchResponseDTO = new MatchResponseDTO();
            matchResponseDTO.setId(match.getMatchId());
            matchResponseDTO.setLeague(match.getLeague());
            matchResponseDTO.setHomeTeam(match.getHomeTeam());
            matchResponseDTO.setHomeOdds(match.getHomeOdds());
            matchResponseDTO.setAwayTeam(match.getAwayTeam());
            matchResponseDTO.setAwayOdds(match.getAwayOdds());
            matchResponseDTO.setDrawOdds(match.getDrawOdds());
            matchResponseDTO.setStartTime(match.getStartTime());
            matchResponseDTOs.add(matchResponseDTO);
        }

        // Convert DTO to JSON
        String jsonMessage = objectMapper.writeValueAsString(matchResponseDTOs);
        matchWebSocketHandler.broadcast(jsonMessage);
    }
}
