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

    /** Usiamo @OneToOne perché una sottomissione ha un solo file.
     * cascade = CascadeType.ALL serve a salvare/cancellare il file
     * automaticamente insieme alla sottomissione.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private File file;

    // Costruttori
    public Sottomissione(){}

    public Sottomissione(String nome){
        this.nome= nome;
    }

    // Getter & Setter
    public long getSottomissioneID() { return sottomissioneID; }

    public void setSottomissioneID(long sottomissioneID) { this.sottomissioneID = sottomissioneID; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public File getFiles() { return file; }

    public void setFiles(File file) { this.file = file; }
}
