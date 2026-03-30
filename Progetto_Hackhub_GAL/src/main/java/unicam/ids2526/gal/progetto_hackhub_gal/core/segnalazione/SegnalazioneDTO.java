package unicam.ids2526.gal.progetto_hackhub_gal.core.segnalazione;

public class SegnalazioneDTO {
    private Long sottomissioneID;
    private String mentoreSegnalatore;
    private String emailMentore;
    private Long mentoreID;
    private String nomeTeam;
    private String nomeHackathon;

    public SegnalazioneDTO(Long sottomissioneID, String nomeMittente, String emailMittente, Long utenteID, String nomeTeam, String nomeHackathon) {
        this.sottomissioneID = sottomissioneID;
        this.mentoreSegnalatore = nomeMittente;
        this.emailMentore = emailMittente;
        this.mentoreID = utenteID;
        this.nomeTeam = nomeTeam;
        this.nomeHackathon = nomeHackathon;
    }

    public Long getSottomissioneID() { return sottomissioneID; }
    public String getNomeMittente() { return mentoreSegnalatore; }
    public String getEmailMittente() { return emailMentore; }
    public Long getUtenteID() { return mentoreID; }
    public String getNomeTeam() { return nomeTeam; }
    public String getNomeHackathon() { return nomeHackathon; }
}