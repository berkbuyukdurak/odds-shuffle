package com.bbd.oddsshuffle.service;

import com.bbd.oddsshuffle.model.dto.request.OddsHistoryRequestDTO;
import com.bbd.oddsshuffle.model.entity.Match;
import com.bbd.oddsshuffle.model.entity.OddsHistory;
import com.bbd.oddsshuffle.repository.MatchRepository;
import com.bbd.oddsshuffle.repository.OddsHistoryRepository;
import com.bbd.oddsshuffle.util.OddsGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class OddsService {

    private final MatchRepository matchRepository;
    private final OddsHistoryService oddsHistoryService;
    private final OddsGenerator oddsGenerator;

    public OddsService(MatchRepository matchRepository, OddsHistoryService oddsHistoryService, OddsGenerator oddsGenerator) {
        this.matchRepository = matchRepository;
        this.oddsHistoryService = oddsHistoryService;
        this.oddsGenerator = oddsGenerator;
    }

    public void updateOdds() {
        List<Match> matches = matchRepository.findAll();
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

            System.out.println("Odds updated");
        }
    }
}
