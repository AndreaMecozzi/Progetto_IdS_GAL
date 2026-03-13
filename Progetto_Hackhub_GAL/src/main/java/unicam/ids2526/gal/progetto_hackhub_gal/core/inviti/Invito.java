package unicam.ids2526.gal.progetto_hackhub_gal.core.inviti;

import jakarta.persistence.*;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;

/**
 * Rappresenta un invito che viene inviato tra gli utenti
 */

@Entity
public class Invito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invitoId;
    private String messaggio;
    @ManyToOne
    @JoinColumn(name="mittente_id", nullable = false)
    private Utente mittente;
    @ManyToOne
    @JoinColumn(name="ricevente_id", nullable = false)
    private Utente ricevente;
    @Enumerated(EnumType.STRING)
    private EsitoInvito esitoInvito;

    public Invito() {}

    public Invito(Utente mittente, Utente ricevente) {
        this.messaggio = "Unisciti al mio team!";
        this.mittente = mittente;
        this.ricevente = ricevente;
        this.esitoInvito=EsitoInvito.INVIATO;
    }

    public Invito(String messaggio, Utente mittente, Utente ricevente) {
        this.messaggio = messaggio;
        this.mittente = mittente;
        this.ricevente = ricevente;
        this.esitoInvito=EsitoInvito.INVIATO;
    }

    public Long getInvitoId() {
        return invitoId;
    }

    public void setInvitoId(Long invitoId) {
        this.invitoId = invitoId;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    public Utente getMittente() {
        return mittente;
    }

    public void setMittente(Utente mittente) {
        this.mittente = mittente;
    }

    public Utente getRicevente() {
        return ricevente;
    }

    public void setRicevente(Utente ricevente) {
        this.ricevente = ricevente;
    }

    public EsitoInvito getEsitoInvito() { return esitoInvito; }

    public void setEsitoInvito(EsitoInvito esitoInvito) { this.esitoInvito = esitoInvito; }
}
