package unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon;

import java.util.List;

public class HackathonDTO {
    private String nomeHackathon;
    private Double premio;
    private int dimensioneMaxTeam;
    private String dataInizio;
    private String stato;
    private List<String> TeamPartecipanti; // Solo i nomi per brevità
    private String organizzatore;
    private String emailOrganizzatore;
    private String emailGiudice;
    private List<String> nomiMentori;
    private List<String> emailMentori;

    public HackathonDTO(String nomeHackathon, Double premio, int dimensioneMaxTeam, String dataInizio,
                        String stato, List<String> TeamPartecipanti, String organizzatore,
                        String emailOrganizzatore, String emailGiudice,
                        List<String> nomiMentori, List<String> emailMentori) {
        this.nomeHackathon = nomeHackathon;
        this.premio = premio;
        this.dimensioneMaxTeam = dimensioneMaxTeam;
        this.dataInizio = dataInizio;
        this.stato = stato;
        this.TeamPartecipanti = TeamPartecipanti;
        this.organizzatore = organizzatore;
        this.emailOrganizzatore = emailOrganizzatore;
        this.emailGiudice = emailGiudice;
        this.nomiMentori = nomiMentori;
        this.emailMentori = emailMentori;
    }

    // Getter compattati
    public String getNomeHackathon() { return nomeHackathon; }
    public Double getPremio() { return premio; }
    public int getDimensioneMaxTeam() { return dimensioneMaxTeam; }
    public String getDataInizio() { return dataInizio; }
    public String getStato() { return stato; }
    public List<String> getTeamPartecipanti() { return TeamPartecipanti; }
    public String getOrganizzatore() { return organizzatore; }
    public String getEmailOrganizzatore() { return emailOrganizzatore; }
    public String getEmailGiudice() { return emailGiudice; }
    public List<String> getNomiMentori() { return nomiMentori; }
    public List<String> getEmailMentori() { return emailMentori; }
}