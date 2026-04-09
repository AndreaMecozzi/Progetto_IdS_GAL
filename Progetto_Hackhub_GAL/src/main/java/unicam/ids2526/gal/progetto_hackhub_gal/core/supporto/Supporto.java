package unicam.ids2526.gal.progetto_hackhub_gal.core.supporto;
import jakarta.persistence.*;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;
import java.util.List;


@Entity
public class Supporto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supportoID;

    @ManyToOne
    @JoinColumn(name = "mittente_id", nullable = false)
    private Utente mittente;

    @ManyToMany
    @JoinTable(
            name = "supporto_riceventi",
            joinColumns = @JoinColumn(name = "supporto_id"),
            inverseJoinColumns = @JoinColumn(name = "utente_id")
    )
    private List<Utente> ricevente;

    @Column(nullable = false)
    private String richiesta;

    public Supporto() {}

    public Supporto(Utente mittente, List<Utente> ricevente, String richiesta) {
        this.mittente = mittente;
        this.ricevente = ricevente;
        this.richiesta = richiesta;
    }

    public Long getSupportoID() { return supportoID; }
    public void setSupportoID(Long supportoID) { this.supportoID = supportoID; }

    public Utente getMittente() { return mittente; }
    public void setMittente(Utente mittente) { this.mittente = mittente; }

    public List<Utente> getRicevente() { return ricevente; }
    public void setRicevente(List<Utente> ricevente) { this.ricevente = ricevente; }

    public String getRichiesta() { return richiesta; }
    public void setRichiesta(String richiesta) { this.richiesta = richiesta; }
}
