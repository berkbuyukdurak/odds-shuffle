package com.bbd.oddsshuffle.service.match;

import com.bbd.oddsshuffle.mapper.MatchMapper;
import com.bbd.oddsshuffle.model.dto.request.MatchRequestDTO;
import com.bbd.oddsshuffle.model.dto.response.MatchResponseDTO;
import com.bbd.oddsshuffle.model.entity.Match;
import com.bbd.oddsshuffle.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MatchService {
    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;

    public MatchService(MatchRepository matchRepository, MatchMapper matchMapper) {
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
    }

    public MatchResponseDTO addMatch(MatchRequestDTO requestDto) {
        Match match = matchMapper.toEntity(requestDto);
        Match savedMatch = matchRepository.save(match);
        return matchMapper.toResponseDTO(savedMatch);
    }

    public List<MatchResponseDTO> getAllMatches() {
        return matchMapper.toResponseDTOList(matchRepository.findAll());
    }

    public MatchResponseDTO getMatchById(UUID id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found"));
        return matchMapper.toResponseDTO(match);
    }

    public List<MatchResponseDTO> getUpcomingMatches() {
        List<Match> matches = matchRepository.findUpcomingMatches();
        return matchMapper.toResponseDTOList(matches);
    }

    public List<MatchResponseDTO> getMatchesByLeague(String league) {
        List<Match> matches = matchRepository.findByLeague(league);
        return matchMapper.toResponseDTOList(matches);
    }
}
