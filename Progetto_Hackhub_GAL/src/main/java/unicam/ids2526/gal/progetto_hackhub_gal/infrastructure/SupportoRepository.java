package unicam.ids2526.gal.progetto_hackhub_gal.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import unicam.ids2526.gal.progetto_hackhub_gal.core.supporto.Supporto;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;

import java.util.List;

public interface SupportoRepository extends JpaRepository<Supporto, Long> {
    List<Supporto> findByRiceventeContaining(Utente ricevente);
}
