package app.core.domain.common;

import lombok.Builder;

@Builder
public record PopularityData(
	Long alcoholId,
	Double reviewScore,
	Double ratingScore,
	Double pickScore,
	Double popularityScore) {
}
