package unicam.ids2526.gal.progetto_hackhub_gal.core.sottomissioni;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Valutazione {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long valutazioneId;
    private int voto;
    private String descrizione;

    public Valutazione() {
    }

    public Valutazione(int voto, String descrizione) {
        this.voto = voto;
        this.descrizione = descrizione;
    }

    public Long getValutazioneId() {
        return valutazioneId;
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
