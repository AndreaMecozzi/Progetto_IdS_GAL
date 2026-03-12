package unicam.ids2526.gal.progetto_hackhub_gal.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Hackathon;

@Repository
public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
}
