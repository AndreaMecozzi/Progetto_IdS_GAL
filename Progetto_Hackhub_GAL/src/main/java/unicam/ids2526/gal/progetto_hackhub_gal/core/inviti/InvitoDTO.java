package unicam.ids2526.gal.progetto_hackhub_gal.core.inviti;

public class InvitoDTO {
    private Long invitoId;
    private String nomeMittente;
    private String emailMittente;
    private String messaggio;

    public InvitoDTO(Long invitoId, String nomeMittente, String emailMittente, String messaggio) {
        this.invitoId = invitoId;
        this.nomeMittente = nomeMittente;
        this.emailMittente = emailMittente;
        this.messaggio = messaggio;
    }

    // Getter compattati
    public Long getInvitoId() { return invitoId; }
    public String getNomeMittente() { return nomeMittente; }
    public String getEmailMittente() { return emailMittente; }
    public String getMessaggio() { return messaggio; }
}