package app.core.repository;

import app.core.domain.alcohol.ImageMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaImageMappingRepository extends JpaRepository<ImageMapping, Long> {
}
