package app.core.dto.response;

public record AlcoholImageInfo(
	Long id,
	String name,
	String imageUrl
) {
	public static AlcoholImageInfo of(Long id, String name, String imageUrl) {
		return new AlcoholImageInfo(id, name, imageUrl);
	}
}
