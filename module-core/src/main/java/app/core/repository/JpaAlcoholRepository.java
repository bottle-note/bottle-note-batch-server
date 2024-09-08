package app.core.repository;

import app.core.domain.alcohol.Alcohol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAlcoholRepository extends JpaRepository<Alcohol, Long> {
}
