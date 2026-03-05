package unicam.ids2526.gal.progetto_hackhub_gal.Hackathon;

import unicam.ids2526.gal.progetto_hackhub_gal.Team;
import unicam.ids2526.gal.progetto_hackhub_gal.profili.Giudice;
import unicam.ids2526.gal.progetto_hackhub_gal.profili.Mentore;

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
    private Giudice giudice;
    private ArrayList<Mentore> mentori;

    public Hackathon(long hackathonID, String nome, String descrizione, Giudice giudice, Mentore mentore) {
        this.hackathonID = hackathonID;
        this.nome = nome;
        this.descrizione = descrizione;
        this.scadenzaIscrizioni=LocalDate.now().plusDays(7);
        this.giudice=giudice;
        this.mentori.add(mentore);
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
    public boolean iscriviTeam(Team team) {
        return this.teams.add(team);
    }

    public Giudice getGiudice() {
        return giudice;
    }

    public void setGiudice(Giudice giudice) {
        this.giudice = giudice;
    }

    public ArrayList<Mentore> getMentori() {
        return mentori;
    }

    public void addMentore(Mentore mentore) {
        this.mentori.add(mentore);
    }

    public void getVincitore(){
        //TODO implementare
    }
}
