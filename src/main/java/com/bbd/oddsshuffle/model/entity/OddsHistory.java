package com.bbd.oddsshuffle.model.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "odds_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OddsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @Column(nullable = false)
    private double homeOdds;

    @Column(nullable = false)
    private double drawOdds;

    @Column(nullable = false)
    private double awayOdds;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}