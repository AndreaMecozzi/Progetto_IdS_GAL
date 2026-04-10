package unicam.ids2526.gal.progetto_hackhub_gal.core.supporto;

public class SupportoDTO {
    private Long supportoId;
    private String teamRichiedente;
    private String hackathon;
    private String richiesta;

    public SupportoDTO() {}

    public SupportoDTO(Long supportoId, String teamRichiedente, String hackathon, String richiesta) {
        this.supportoId = supportoId;
        this.teamRichiedente = teamRichiedente;
        this.hackathon = hackathon;
        this.richiesta = richiesta;
    }

    // Getter e Setter
    public Long getSupportoId() { return supportoId; }
    public void setSupportoId(Long supportoId) { this.supportoId = supportoId; }

    public String getTeamRichiedente() { return teamRichiedente; }
    public void setTeamRichiedente(String teamRichiedente) { this.teamRichiedente = teamRichiedente; }

    public String getHackathon() { return hackathon; }
    public void setHackathon(String hackathon) { this.hackathon = hackathon; }

    public String getRichiesta() { return richiesta; }
    public void setRichiesta(String richiesta) { this.richiesta = richiesta; }
}