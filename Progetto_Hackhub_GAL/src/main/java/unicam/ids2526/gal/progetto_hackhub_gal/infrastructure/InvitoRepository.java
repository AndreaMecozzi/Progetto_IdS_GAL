package unicam.ids2526.gal.progetto_hackhub_gal.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Invito;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Utente;

import java.util.Optional;
import java.util.List;

public interface InvitoRepository extends JpaRepository<Invito, Long> {
//query corrispondente = SELECT * FROM invito WHERE ricevente = ?
    List <Invito> findByRicevente (Utente ricevente);

    boolean existsByMittenteAndRicevente(Utente mittente, Utente ricevente);
}
