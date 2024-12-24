package com.bbd.oddsshuffle.mapper;

import com.bbd.oddsshuffle.model.dto.request.MatchRequestDTO;
import com.bbd.oddsshuffle.model.dto.response.MatchResponseDTO;
import com.bbd.oddsshuffle.model.entity.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    @Mapping(target = "id", ignore = true)
    Match toEntity(MatchRequestDTO dto);

    MatchResponseDTO toResponseDTO(Match entity);

    List<MatchResponseDTO> toResponseDTOList(List<Match> entities);
}
