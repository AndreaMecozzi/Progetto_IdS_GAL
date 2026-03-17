package unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon;

import jakarta.persistence.*;
import unicam.ids2526.gal.progetto_hackhub_gal.core.team.Team;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Hackathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hackathonID;

    @Column(unique = true, nullable = false)
    private String nome;

    @Convert(converter = StatoHackathonConverter.class)
    @Column(nullable = false)
    private StatoHackathon stato;

    @Column(nullable = false)
    private LocalDateTime dataInizioStato;

    @Column(nullable = false)
    private Double premio;

    @Column(nullable = false)
    private int dimenisoneTeam; // Mantengo il refuso "dimenisone" come da diagramma

    @Column(nullable = false)
    private String regolamento;

    @ManyToOne
    @JoinColumn(name = "creatore_id", nullable = false)
    private Utente organizzatore;

    @ManyToMany
    @JoinTable(
            name = "hackathon_mentori", // Nome della tabella intermedia che creerà il DB
            joinColumns = @JoinColumn(name = "hackathon_id"), // Colonna verso l'Hackathon
            inverseJoinColumns = @JoinColumn(name = "mentore_id") // Colonna verso l'Utente (Mentore)
    )
    private List<Utente> mentore;

    @ManyToOne
    @JoinColumn(name="giudice_id", nullable = false)
    private Utente giudice;

    @OneToMany(mappedBy = "hackathon", cascade = CascadeType.ALL)
    private List<Team> teamPartecipanti;

    // Costruttore di default richiesto da JPA
    public Hackathon() {
    }

    public Hackathon(String nome, Double premio, int dimenisoneTeam,
                     String regolamento, Utente organizzatore, Utente  giudice, List<Utente> mentore) {
        this.nome = nome;
        this.premio = premio;
        this.dataInizioStato = LocalDateTime.now();
        this.dimenisoneTeam = dimenisoneTeam;
        this.regolamento = regolamento;
        this.organizzatore = organizzatore;
        this.giudice = giudice;
        this.mentore = mentore;
        this.teamPartecipanti = new ArrayList<Team>();
    }

    // Getter e Setter
    public Long getHackathonID() {
        return hackathonID;
    }
    public void setHackathonID(Long hackathonID) {
        this.hackathonID = hackathonID;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStato() {
        return stato.getNomeStato();
    }
    public void cambiaStato() {this.stato.cambiaStato();}
    public void setStato(StatoHackathon stato) { this.stato=stato;}

    public LocalDateTime getDataInizioStato() {
        return dataInizioStato;
    }
    public void setDataInizioStato(LocalDateTime dataInizioStato) {
        this.dataInizioStato = dataInizioStato;
    }

    public Double getPremio() {
        return premio;
    }
    public void setPremio(Double premio) {
        this.premio = premio;
    }

    public int getDimenisoneTeam() {
        return dimenisoneTeam;
    }
    public void setDimenisoneTeam(int dimenisoneTeam) {
        this.dimenisoneTeam = dimenisoneTeam;
    }

    public String getRegolamento(){return regolamento;}
    public void setRegolamento(String regolamento){this.regolamento=regolamento;}

    public Utente getOrganizzatore() {
        return organizzatore;
    }
    public void setOrganizzatore(Utente organizzatore) {
        this.organizzatore = organizzatore;
    }

    public List<Utente> getMentore() {
        return mentore;
    }
    public void setMentore(List<Utente> mentore) {
        this.mentore = mentore;
    }
    public void addMentore(Utente mentore) { this.mentore.add(mentore);}

    public Utente getGiudice() {
        return giudice;
    }
    public void setGiudice(Utente giudice) {
        this.giudice = giudice;
    }

    public List<Team> getTeamPartecipanti() {
        return teamPartecipanti;
    }
    public void setTeamPartecipanti(List<Team> teamPartecipanti) {
        this.teamPartecipanti = teamPartecipanti;
    }
    public void addTeam(Team team) {
        this.teamPartecipanti.add(team);
    }

    public void removeTeam(Team team) {
        if (team != null) {
            this.teamPartecipanti.remove(team);
            team.setHackathon(null);
        }
    }
}
