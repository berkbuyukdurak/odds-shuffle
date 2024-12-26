package com.bbd.oddsshuffle.service;

import com.bbd.oddsshuffle.model.dto.request.MatchRequestDTO;
import com.bbd.oddsshuffle.model.dto.response.MatchResponseDTO;
import com.bbd.oddsshuffle.model.entity.Match;
import com.bbd.oddsshuffle.repository.MatchRepository;
import com.bbd.oddsshuffle.util.mapper.MapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class MatchServiceTest {

    @InjectMocks
    private MatchService matchService;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private MapperUtil mapperUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddMatch() {
        MatchRequestDTO requestDTO = new MatchRequestDTO();
        Match match = new Match();
        Match savedMatch = new Match();
        savedMatch.setMatchId(UUID.randomUUID());

        when(mapperUtil.convertToEntity(requestDTO, Match.class)).thenReturn(match);
        when(matchRepository.save(match)).thenReturn(savedMatch);
        when(mapperUtil.convertToDTO(savedMatch, MatchResponseDTO.class)).thenReturn(new MatchResponseDTO());

        matchService.addMatch(requestDTO);

        verify(matchRepository, times(1)).save(match);
    }

    @Test
    void testGetAllMatches() {
        when(matchRepository.findAll()).thenReturn(Collections.emptyList());

        List<MatchResponseDTO> result = matchService.getAllMatches();

        verify(matchRepository, times(1)).findAll();
        assert result.isEmpty();
    }
}