package com.bbd.oddsshuffle.service;


import com.bbd.oddsshuffle.model.dto.request.OddsHistoryRequestDTO;
import com.bbd.oddsshuffle.model.dto.response.MatchOddsHistoryDTO;
import com.bbd.oddsshuffle.model.dto.response.MatchResponseDTO;
import com.bbd.oddsshuffle.model.dto.response.OddsHistoryResponseDTO;
import com.bbd.oddsshuffle.model.entity.Match;
import com.bbd.oddsshuffle.model.entity.OddsHistory;
import com.bbd.oddsshuffle.repository.MatchRepository;
import com.bbd.oddsshuffle.repository.OddsHistoryRepository;
import com.bbd.oddsshuffle.util.mapper.MapperUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OddsHistoryService {
    private final OddsHistoryRepository oddsHistoryRepository;
    private final MatchRepository matchRepository;
    private final MapperUtil mapperUtil;

    public OddsHistoryService(OddsHistoryRepository oddsHistoryRepository, MatchRepository matchRepository, MapperUtil mapperUtil) {
        this.oddsHistoryRepository = oddsHistoryRepository;
        this.matchRepository = matchRepository;
        this.mapperUtil = mapperUtil;
    }

    public OddsHistoryResponseDTO addOddsHistory(Match match, OddsHistoryRequestDTO requestDTO) {
        OddsHistory oddsHistory = new OddsHistory();
        oddsHistory.setMatch(match);
        oddsHistory.setHomeOdds(requestDTO.getHomeOdds());
        oddsHistory.setDrawOdds(requestDTO.getDrawOdds());
        oddsHistory.setAwayOdds(requestDTO.getAwayOdds());
        oddsHistory.setTimestamp(requestDTO.getTimestamp());

        OddsHistory savedOddsHistory = oddsHistoryRepository.save(oddsHistory);
        return mapperUtil.convertToDTO(savedOddsHistory, OddsHistoryResponseDTO.class);
    }

    public List<OddsHistoryResponseDTO> getOddsHistoryByMatchId(UUID matchId) {
        List<OddsHistory> oddsHistories = oddsHistoryRepository.findByMatch_MatchIdOrderByTimestampAsc(matchId);
        return oddsHistories.stream()
                .map(oddsHistory -> mapperUtil.convertToDTO(oddsHistory, OddsHistoryResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<MatchOddsHistoryDTO> getAllMatchesWithOddsHistory() {
        List<Match> matches = matchRepository.findAll();
        return matches.stream()
                .map(match -> {
                    List<OddsHistory> oddsHistoryList = oddsHistoryRepository.findByMatch_MatchIdOrderByTimestampAsc(match.getMatchId());
                    List<OddsHistoryResponseDTO> oddsHistoryResponse = oddsHistoryList.stream()
                            .map(odds -> mapperUtil.convertToDTO(odds, OddsHistoryResponseDTO.class))
                            .toList();
                    return new MatchOddsHistoryDTO(mapperUtil.convertToDTO(match, MatchResponseDTO.class), oddsHistoryResponse);
                })
                .toList();
    }
}
