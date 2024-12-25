package com.bbd.oddsshuffle.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID matchId;

    @Column(nullable = false)
    private String league;

    @Column(name = "home_team", nullable = false)
    private String homeTeam;

    @Column(name = "away_team", nullable = false)
    private String awayTeam;

    @Column(nullable = false)
    private double homeOdds;

    @Column(nullable = false)
    private double drawOdds;

    @Column(nullable = false)
    private double awayOdds;

    @Column(nullable = false)
    private LocalDateTime startTime;
}