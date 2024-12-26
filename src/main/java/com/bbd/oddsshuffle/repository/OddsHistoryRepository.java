package com.bbd.oddsshuffle.repository;

import com.bbd.oddsshuffle.model.entity.OddsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OddsHistoryRepository extends JpaRepository<OddsHistory, UUID> {

    List<OddsHistory> findByMatch_MatchId(UUID matchId);
    List<OddsHistory> findByMatch_MatchIdOrderByTimestampAsc(UUID matchId);
}