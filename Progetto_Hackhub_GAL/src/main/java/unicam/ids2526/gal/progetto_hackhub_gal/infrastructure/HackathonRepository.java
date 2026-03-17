package unicam.ids2526.gal.progetto_hackhub_gal.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.Hackathon;

import java.util.List;
import java.util.Optional;

@Repository
public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
    Optional<Hackathon> findByNome(String nomeHackhathon);
    List<Hackathon> findAll();
    void deleteByNome(String nome);
    List<Hackathon> findAllByStatoNot(String stato);
}
