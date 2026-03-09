package unicam.ids2526.gal.progetto_hackhub_gal.core;

import jakarta.persistence.*;

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
    //@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file", referencedColumnName = "id")
    private File file;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    // Costruttori
    public Sottomissione(){}

    public Sottomissione(String nome, Team team){
        this.nome= nome;
        this.team=team;
    }

    // Getter & Setter
    public long getSottomissioneID() { return sottomissioneID; }

    public void setSottomissioneID(long sottomissioneID) { this.sottomissioneID = sottomissioneID; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public File getFile() { return file; }

    public void setFile(File file) { this.file = file; }
}
