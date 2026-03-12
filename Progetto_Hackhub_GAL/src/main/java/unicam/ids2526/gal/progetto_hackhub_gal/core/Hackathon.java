package unicam.ids2526.gal.progetto_hackhub_gal.core;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Hackathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hackathonID;

    @Column(nullable = false)
    private String nome;

    @Convert(converter = StatoHackathonConverter.class)
    @Column(nullable = false)
    private StatoHackathon stato;

    @Column(nullable = false)
    private LocalDateTime dataInizioStato;

    private Double premio;

    private int dimenisoneTeam; // Mantengo il refuso "dimenisone" come da diagramma

    private String regolamento;

    // Costruttore di default richiesto da JPA
    public Hackathon() {
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
    public void setStato() { this.stato.cambiaStato(); }

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

}
