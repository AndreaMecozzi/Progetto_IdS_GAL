package unicam.ids2526.gal.progetto_hackhub_gal.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.core.inviti.Invito;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;

import java.util.List;

public interface InvitoRepository extends JpaRepository<Invito, Long> {

    List <Invito> findByRicevente (Utente ricevente);

    boolean existsByMittenteAndRicevente(Utente mittente, Utente ricevente);
}
