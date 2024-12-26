package com.bbd.oddsshuffle.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "oddsHistory")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID matchId;

    private String league;
    private String homeTeam;
    private String awayTeam;

    private double homeOdds;
    private double drawOdds;
    private double awayOdds;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OddsHistory> oddsHistory;

    private LocalDateTime startTime;
}