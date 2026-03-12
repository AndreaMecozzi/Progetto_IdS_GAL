package unicam.ids2526.gal.progetto_hackhub_gal.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Hackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Sottomissione;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Team;

import java.util.Optional;

@Repository
public interface SottomissioneRepository extends JpaRepository<Sottomissione, Long> {
    /**
     * Query corrispondente: SELECT * FROM sottomissione WHERE team_id = ?
     * Utilizzato nell'aggiornamento per trovare la sottomissione esistente di un team.
     */
    Optional<Sottomissione> findByTeamNome(String nometeam);

    Optional<Sottomissione> findByTeamTeamId(Long teamId);

}
