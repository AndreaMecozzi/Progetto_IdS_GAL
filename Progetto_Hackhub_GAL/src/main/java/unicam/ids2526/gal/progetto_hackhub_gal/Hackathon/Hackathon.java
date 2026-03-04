package unicam.ids2526.gal.progetto_hackhub_gal.Hackathon;

import unicam.ids2526.gal.progetto_hackhub_gal.Team;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe che rappresenta gli Hackathon. Contiene tutte le informazioni all'Hackathon,
 * come il nome, una breve descrizione, lo stato, la data di scadenza delle iscrizioni e
 * la lista di team iscritti
 */
public class Hackathon {
    private long hackathonID;
    private String nome;
    private String descrizione;
    private HackathonState state;
    private LocalDate scadenzaIscrizioni;
    private ArrayList<Team> teams;

    public Hackathon(long hackathonID, String nome, String descrizione) {
        this.hackathonID = hackathonID;
        this.nome = nome;
        this.descrizione = descrizione;
        this.scadenzaIscrizioni=LocalDate.now().plusDays(7);
    }

    public long getHackathonID() {
        return hackathonID;
    }

    public void setHackathonID(long hackathonID) {
        this.hackathonID = hackathonID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public HackathonState getState() {
        return state;
    }

    public void setState(HackathonState state) {
        this.state = state;
    }

    public LocalDate getScadenzaIscrizioni() {
        return scadenzaIscrizioni;
    }

    public void setScadenzaIscrizioni(LocalDate scadenzaIscrizioni) {
        this.scadenzaIscrizioni = scadenzaIscrizioni;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }
}
