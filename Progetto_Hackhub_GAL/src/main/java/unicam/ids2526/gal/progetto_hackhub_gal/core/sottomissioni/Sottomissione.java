package unicam.ids2526.gal.progetto_hackhub_gal.core.sottomissioni;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import unicam.ids2526.gal.progetto_hackhub_gal.core.team.Team;

import java.io.File;

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
    @Column(name = "file")
    private String file;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "team_id", nullable = false, unique = true)
    private Team team;
    @OneToOne
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

    public String getNome() { return nome; }

    public String getFile() { return file; }

    public void setFile(String file) { this.file = file; }

    public Team getTeam() {
        return team;
    }

    public Valutazione getValutazione() {
        return valutazione;
    }

    public void setValutazione(Valutazione voto) {
        this.valutazione = voto;
    }

    @PreRemove
    private void eliminaFileFisico() {
        if (this.file != null && !this.file.isEmpty()) {
            File fileDaEliminare = new File(this.file);

            if (fileDaEliminare.exists()) {
                fileDaEliminare.delete();
            }
        }
    }
}
