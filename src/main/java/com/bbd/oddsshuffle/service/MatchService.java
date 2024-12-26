package com.bbd.oddsshuffle.service;

import org.springframework.stereotype.Service;
import com.bbd.oddsshuffle.model.dto.request.MatchRequestDTO;
import com.bbd.oddsshuffle.model.dto.response.MatchResponseDTO;
import com.bbd.oddsshuffle.model.entity.Match;
import com.bbd.oddsshuffle.repository.MatchRepository;
import com.bbd.oddsshuffle.util.mapper.MapperUtil;

import java.util.List;
import java.util.UUID;

@Service
public class MatchService {
    private final MatchRepository matchRepository;
    private final MapperUtil mapperUtil;

    public MatchService(MatchRepository matchRepository, MapperUtil mapperUtil) {
        this.matchRepository = matchRepository;
        this.mapperUtil = mapperUtil;
    }

    public MatchResponseDTO addMatch(MatchRequestDTO requestDto) {
        Match match = mapperUtil.convertToEntity(requestDto, Match.class);
        Match savedMatch = matchRepository.save(match);
        return mapperUtil.convertToDTO(savedMatch, MatchResponseDTO.class);
    }

    public List<MatchResponseDTO> getAllMatches() {
        List<Match> matches = matchRepository.findAll();
        return matches.stream()
                .map(match -> mapperUtil.convertToDTO(match, MatchResponseDTO.class))
                .toList();
    }

    public MatchResponseDTO getMatchById(UUID id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found"));
        return mapperUtil.convertToDTO(match, MatchResponseDTO.class);
    }

    public List<MatchResponseDTO> getUpcomingMatches() {
        List<Match> matches = matchRepository.findUpcomingMatches();
        return matches.stream()
                .map(match -> mapperUtil.convertToDTO(match, MatchResponseDTO.class))
                .toList();
    }

    public List<MatchResponseDTO> getMatchesByLeague(String league) {
        List<Match> matches = matchRepository.findByLeague(league);
        return matches.stream()
                .map(match -> mapperUtil.convertToDTO(match, MatchResponseDTO.class))
                .toList();
    }
}