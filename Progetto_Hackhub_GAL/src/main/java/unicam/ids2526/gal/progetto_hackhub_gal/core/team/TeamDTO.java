package unicam.ids2526.gal.progetto_hackhub_gal.core.team;

import java.util.List;

public class TeamDTO {
    private Long teamId;
    private String nomeTeam;
    private String nomeHackathon;
    private List<String> nomiMembri;
    private List<String> emailMembri;

    public TeamDTO(Long teamId, String nomeTeam, String nomeHackahthon, List<String> nomiMembri, List<String> emailMembri) {
        this.teamId = teamId;
        this.nomeTeam = nomeTeam;
        this.nomeHackathon = nomeHackahthon;
        this.nomiMembri = nomiMembri;
        this.emailMembri = emailMembri;
    }

    public Long getTeamId() { return teamId; }
    public String getNomeTeam() { return nomeTeam; }
    public String getNomeHackathon() { return nomeHackathon; }
    public List<String> getNomiMembri() { return nomiMembri; }
    public List<String> getEmailMembri() { return emailMembri; }
}