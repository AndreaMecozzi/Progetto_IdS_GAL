package unicam.ids2526.gal.progetto_hackhub_gal.core.sottomissioni;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import unicam.ids2526.gal.progetto_hackhub_gal.core.team.Team;

@Entity
public class Sottomissione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sottomissioneID;

    @Column(nullable = false)
    private String nome;

    /** cascade = CascadeType.ALL serve a salvare/cancellare il file
     * automaticamente insieme alla sottomissione.
     */
    //@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file", referencedColumnName = "id")
    private String file;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
    @ManyToOne
    @JoinColumn(name = "valutazione_id")
    private Valutazione valutazione;


    // Costruttori
    public Sottomissione(){}

    public Sottomissione(Team team){
        this.nome= "Sottomissione di " + team.getNome();
        this.team= team;
        this.file=null;
        this.valutazione =null;
    }

    // Getter & Setter
    public long getSottomissioneID() { return sottomissioneID; }

    public void setSottomissioneID(long sottomissioneID) { this.sottomissioneID = sottomissioneID; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getFile() { return file; }

    public void setFile(String file) { this.file = file; }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Valutazione getValutazione() {
        return valutazione;
    }

    public void setValutazione(Valutazione voto) {
        this.valutazione = voto;
    }
}
