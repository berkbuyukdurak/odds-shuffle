package com.bbd.oddsshuffle.model.entity;

import com.bbd.oddsshuffle.constants.BetStatus;
import com.bbd.oddsshuffle.constants.BetType;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "bets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID betId;

    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @Column(nullable = false)
    private double oddsAtPlacement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BetType betType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BetStatus status;
}
