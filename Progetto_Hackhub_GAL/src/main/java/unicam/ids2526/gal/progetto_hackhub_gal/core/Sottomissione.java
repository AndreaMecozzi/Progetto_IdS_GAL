package unicam.ids2526.gal.progetto_hackhub_gal.core;

import jakarta.persistence.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Sottomissione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sottomissioneID;

    @Column(nullable = false)
    private String nome;

    /* * Nota: Se File è un'altra @Entity, usiamo @OneToMany.
     * Se vuoi solo memorizzare i percorsi dei file come stringhe, si userebbe @ElementCollection.
     * Qui assumiamo che File sia un'entità correlata.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sottomissione_id") // Crea la chiave esterna nella tabella File
    private List<File> files = new ArrayList<>();

    public Sottomissione(){}

    public Sottomissione(String nome){
        this.nome= nome;
    }

    public long getSottomissioneID() {
        return sottomissioneID;
    }

    public void setSottomissioneID(long sottomissioneID) {
        this.sottomissioneID = sottomissioneID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
