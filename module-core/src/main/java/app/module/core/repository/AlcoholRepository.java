package app.module.core.repository;

import app.module.core.domain.alcohol.Alcohol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlcoholRepository extends JpaRepository<Alcohol, Long> {
}
