package unicam.ids2526.gal.progetto_hackhub_gal.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.core.sottomissioni.Valutazione;

public interface ValutazioneRepository extends JpaRepository<Valutazione, Integer> {}
