package com.bbd.oddsshuffle.model.dto.response;

import com.bbd.oddsshuffle.constants.BetStatus;
import com.bbd.oddsshuffle.constants.BetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BetResponseDTO {
    private UUID betId;
    private UUID matchId;
    private double oddsAtPlacement;
    private BetType betType;
    private BetStatus status;
}
