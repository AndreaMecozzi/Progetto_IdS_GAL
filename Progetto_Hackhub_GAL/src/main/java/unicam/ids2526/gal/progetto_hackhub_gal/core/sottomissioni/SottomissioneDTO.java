package unicam.ids2526.gal.progetto_hackhub_gal.core.sottomissioni;

public class SottomissioneDTO {
    private Long sottomissioneID;
    private String nomeSottomissione;
    private String valutazione; // Mostriamo il voto o un messaggio se non ancora valutata

    public SottomissioneDTO(Long sottomissioneID, String nomeSottomissione, String valutazione) {
        this.sottomissioneID = sottomissioneID;
        this.nomeSottomissione = nomeSottomissione;
        this.valutazione = valutazione;
    }

    public Long getSottomissioneID() { return sottomissioneID; }
    public String getNomeSottomissione() { return nomeSottomissione; }
    public String getValutazione() { return valutazione; }
}