package com.bbd.oddsshuffle.repository;

import com.bbd.oddsshuffle.model.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MatchRepository extends JpaRepository<Match, UUID> {
    @Query("SELECT m FROM Match m WHERE m.startTime > CURRENT_TIMESTAMP ORDER BY m.startTime ASC")
    List<Match> findUpcomingMatches();

    @Query("SELECT m FROM Match m WHERE LOWER(m.league) = LOWER(:league)")
    List<Match> findByLeague(@Param("league") String league);

    @Query("SELECT m FROM Match m LEFT JOIN FETCH m.oddsHistory WHERE m.matchId = :matchId")
    Optional<Match> findByIdWithOddsHistory(@Param("matchId") UUID matchId);
}