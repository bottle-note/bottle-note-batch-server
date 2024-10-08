package app.core.domain.alcohol;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity(name = "image_history")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long imageCount;

  @Builder.Default
  @Column(nullable = false, columnDefinition = "TIMESTAMP")
  private LocalDateTime time = LocalDateTime.now();

    public static ImageHistory create(Long imageCount) {
        return new ImageHistory(null, imageCount, LocalDateTime.now());
    }
}
