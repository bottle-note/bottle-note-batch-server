package app.core.domain.common;

import lombok.Builder;

@Builder
public record PopularData(
    Long alcoholId,
    Double reviewScore,
    Double ratingScore,
    Double pickScore,
    Double popularScore) {}
