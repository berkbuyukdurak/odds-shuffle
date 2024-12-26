package com.bbd.oddsshuffle.service;

import com.bbd.oddsshuffle.handler.MatchWebSocketHandler;
import com.bbd.oddsshuffle.model.dto.request.OddsHistoryRequestDTO;
import com.bbd.oddsshuffle.model.dto.response.MatchResponseDTO;
import com.bbd.oddsshuffle.model.entity.Match;
import com.bbd.oddsshuffle.repository.MatchRepository;
import com.bbd.oddsshuffle.util.OddsGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

class OddsServiceTest {

    @InjectMocks
    private OddsService oddsService;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private OddsHistoryService oddsHistoryService;

    @Mock
    private OddsGenerator oddsGenerator;

    @Mock
    private MatchWebSocketHandler matchWebSocketHandler;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateOdds() throws JsonProcessingException {
        Match mockMatch = new Match();
        mockMatch.setMatchId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        mockMatch.setLeague("Premier League");
        mockMatch.setHomeTeam("Team A");
        mockMatch.setAwayTeam("Team B");

        when(matchRepository.findAll()).thenReturn(Collections.singletonList(mockMatch));
        when(oddsGenerator.generateOdds()).thenReturn(new double[]{1.5, 3.0, 2.0});
        when(objectMapper.writeValueAsString(any())).thenReturn("mockJson");

        oddsService.updateOdds();

        verify(matchRepository, times(1)).findAll();
        verify(oddsHistoryService, times(1)).addOddsHistory(eq(mockMatch), any(OddsHistoryRequestDTO.class));
        verify(matchWebSocketHandler, times(1)).broadcast("mockJson");
    }
}