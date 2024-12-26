package com.bbd.oddsshuffle.service;

import com.bbd.oddsshuffle.model.dto.request.OddsHistoryRequestDTO;
import com.bbd.oddsshuffle.model.entity.Match;
import com.bbd.oddsshuffle.model.entity.OddsHistory;
import com.bbd.oddsshuffle.repository.MatchRepository;
import com.bbd.oddsshuffle.repository.OddsHistoryRepository;
import com.bbd.oddsshuffle.util.mapper.MapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

class OddsHistoryServiceTest {

    @InjectMocks
    private OddsHistoryService oddsHistoryService;

    @Mock
    private OddsHistoryRepository oddsHistoryRepository;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private MapperUtil mapperUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOddsHistory() {
        Match mockMatch = new Match();
        mockMatch.setMatchId(UUID.randomUUID());

        OddsHistoryRequestDTO requestDTO = new OddsHistoryRequestDTO(mockMatch.getMatchId(), 1.5, 3.0, 2.0, null);

        oddsHistoryService.addOddsHistory(mockMatch, requestDTO);

        verify(oddsHistoryRepository, times(1)).save(any(OddsHistory.class));
    }

    @Test
    void testGetOddsHistoryByMatchId() {
        UUID matchId = UUID.randomUUID();
        when(oddsHistoryRepository.findByMatch_MatchIdOrderByTimestampAsc(matchId)).thenReturn(Collections.emptyList());

        List<?> result = oddsHistoryService.getOddsHistoryByMatchId(matchId);

        verify(oddsHistoryRepository, times(1)).findByMatch_MatchIdOrderByTimestampAsc(matchId);
        assert result.isEmpty();
    }
}