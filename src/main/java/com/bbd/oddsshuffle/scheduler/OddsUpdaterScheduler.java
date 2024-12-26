package com.bbd.oddsshuffle.scheduler;

import com.bbd.oddsshuffle.model.dto.response.MatchResponseDTO;
import com.bbd.oddsshuffle.service.OddsService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OddsUpdaterScheduler {

    private final OddsService oddsService;

    public OddsUpdaterScheduler(OddsService oddsService) {
        this.oddsService = oddsService;
    }

    @Scheduled(fixedRateString = "${oddsshuffle.scheduled.fixedRate}")
    public void updateOddsPeriodically() {
        System.out.println("Updating odds...");
        oddsService.updateOdds();
    }
}