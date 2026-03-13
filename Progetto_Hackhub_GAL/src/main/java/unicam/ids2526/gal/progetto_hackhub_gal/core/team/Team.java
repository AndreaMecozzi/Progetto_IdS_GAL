package unicam.ids2526.gal.progetto_hackhub_gal.core.team;

import jakarta.persistence.*;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.Hackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.sottomissioni.Sottomissione;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta un team. Un team è caratterizzato da un nome univoco, dagli utenti che lo compongono,
 * dall'hackathon a cui partecipa e
 */

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;
    @Column(unique = true, nullable = false)
    private String nome;
    @OneToMany
    @JoinColumn(name = "team_id")
    private List<Utente> utenti;
    @ManyToOne
    @JoinColumn(name="hackathon_id")
    private Hackathon hackathon;
    @OneToOne
    @JoinColumn(name="sottomissione_id")
    private Sottomissione sottomissione;

    public Team() {}

    public Team(String nome, Utente utente) {
        this.nome=nome;
        this.utenti=new ArrayList<>();
        this.utenti.add(utente);
        this.hackathon=null;
        this.sottomissione=null;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Hackathon getHackathon() {
        return hackathon;
    }

    public void setHackathon(Hackathon hackathon) {
        this.hackathon = hackathon;
    }

    public List<Utente> getUtenti() {
        return utenti;
    }

    public void addUtente(Utente utente) {
        this.utenti.add(utente);
    }

    public Sottomissione getSottomissione() {
        return sottomissione;
    }

    public void setSottomissione(Sottomissione sottomissione) {
        this.sottomissione = sottomissione;
    }
}
