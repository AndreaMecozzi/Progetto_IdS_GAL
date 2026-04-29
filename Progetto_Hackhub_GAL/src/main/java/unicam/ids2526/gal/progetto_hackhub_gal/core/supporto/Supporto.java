package unicam.ids2526.gal.progetto_hackhub_gal.core.supporto;
import jakarta.persistence.*;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.Hackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.team.Team;
import java.util.List;


@Entity
public class Supporto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supportoID;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "hackathon_id", nullable = false)
    private Hackathon hackathon;

    @Column(nullable = false)
    private String richiesta;

    public Supporto() {}

    public Supporto(Team team, Hackathon hackathon, String richiesta) {
        this.team = team;
        this.hackathon = hackathon;
        this.richiesta = richiesta;
    }

    public Long getSupportoID() { return supportoID; }

    public Team getTeam() { return team; }

    public Hackathon getHackathon() { return hackathon; }

    public String getRichiesta() { return richiesta; }
    public void setRichiesta(String richiesta) { this.richiesta=richiesta; }
}
