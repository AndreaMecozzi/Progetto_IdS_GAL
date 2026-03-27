package unicam.ids2526.gal.progetto_hackhub_gal.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.Hackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;

import java.util.List;
import java.util.Optional;

@Repository
public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
    Optional<Hackathon> findByNome(String nomeHackathon);
    List<Hackathon> findAll();
    void deleteByNome(String nome);

    @Query(value = "SELECT * FROM hackathon WHERE stato <> :stato", nativeQuery = true)
    List<Hackathon> findAllByStatoNot(@Param("stato") String stato);

    List<Hackathon> findByOrganizzatore(Utente organizzatore);
}
