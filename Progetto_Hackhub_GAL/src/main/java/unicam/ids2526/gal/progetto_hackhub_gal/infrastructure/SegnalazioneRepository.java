package unicam.ids2526.gal.progetto_hackhub_gal.infrastructure;
import org.springframework.data.jpa.repository.JpaRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.Hackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.segnalazione.Segnalazione;
import unicam.ids2526.gal.progetto_hackhub_gal.core.team.Team;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;

import java.util.List;

public interface SegnalazioneRepository extends JpaRepository<Segnalazione, Long> {
    List<Segnalazione> findByHackathon(Hackathon hackathon);
    List<Segnalazione> findByHackathonIn(List<Hackathon> hackathons);
    List<Segnalazione> findByTeam(Team team);
}
