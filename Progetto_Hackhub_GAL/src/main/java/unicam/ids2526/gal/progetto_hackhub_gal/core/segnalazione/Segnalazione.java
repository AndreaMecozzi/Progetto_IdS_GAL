package unicam.ids2526.gal.progetto_hackhub_gal.core.segnalazione;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.Hackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.team.Team;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;


@Entity
public class Segnalazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long segnalazioneID;

    @ManyToOne
    @JoinColumn(name = "mittente_id")
    private Utente mittente;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private String motivazione;

    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    private Hackathon hackathon;



    protected Segnalazione() {}

    public Segnalazione(Utente mittente, Team team, String motivazione, Hackathon hackathon){
        this.mittente = mittente;
        this.team = team;
        this.motivazione = motivazione;
        this.hackathon = hackathon;

    }



    public Long getSegnalazioneID() { return segnalazioneID; }
    public Utente getMittente() { return mittente; }
    public Team getTeam() { return team; }
    public String getMotivazione() { return motivazione; }
    public void setMotivazione(String motivazione) { this.motivazione = motivazione; }
    public Hackathon getHackathon() { return hackathon; }


}
