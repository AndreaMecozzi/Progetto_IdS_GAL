package unicam.ids2526.gal.progetto_hackhub_gal;

import unicam.ids2526.gal.progetto_hackhub_gal.profili.Utente;

public class Invito {

    private long invitoID;
    private String messaggio;
    private Utente mittente;
    private Utente ricevitore;

    public Invito (long invitoID, String messaggio, Utente mittente, Utente ricevitore){
        this.invitoID= invitoID;
        this.messaggio= messaggio;
        this.mittente= mittente;
        this.ricevitore= ricevitore;
    }

    public long getInvitoID() {
        return invitoID;
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

    public Utente getRicevitore() {
        return ricevitore;
    }

    public void setRicevitore(Utente ricevitore) {
        this.ricevitore = ricevitore;
    }
}
