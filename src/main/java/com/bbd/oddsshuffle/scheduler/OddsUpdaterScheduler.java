package com.bbd.oddsshuffle.scheduler;

import com.bbd.oddsshuffle.service.OddsService;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OddsUpdaterScheduler {

    private final OddsService oddsService;

    public OddsUpdaterScheduler(OddsService oddsService) {
        this.oddsService = oddsService;
    }

    @Scheduled(fixedRateString = "${oddsshuffle.scheduled.fixedRate}")
    public void updateOddsPeriodically() throws JsonProcessingException {
        oddsService.updateOdds();
    }
}