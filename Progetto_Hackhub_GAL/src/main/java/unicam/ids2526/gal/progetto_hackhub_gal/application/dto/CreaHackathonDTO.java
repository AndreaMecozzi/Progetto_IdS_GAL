package unicam.ids2526.gal.progetto_hackhub_gal.application.dto;

import org.springframework.web.multipart.MultipartFile;

public class CreaHackathonDTO {
    private String nomeHackathon;
    private Double premio;
    private Integer dimensioneTeam;
    private MultipartFile regolamento;

    //TODO implementare giudice e mentori

    public CreaHackathonDTO() {}

    public String getNomeHackathon() {
        return nomeHackathon;
    }

    public void setNomeHackathon(String nomeHackathon) {
        this.nomeHackathon = nomeHackathon;
    }

    public Double getPremio() {
        return premio;
    }

    public void setPremio(Double premio) {
        this.premio = premio;
    }

    public Integer getDimensioneTeam() {
        return dimensioneTeam;
    }

    public void setDimensioneTeam(Integer dimensioneTeam) {
        this.dimensioneTeam = dimensioneTeam;
    }

    public MultipartFile getRegolamento() {
        return regolamento;
    }

    public void setRegolamento(MultipartFile regolamento) {
        this.regolamento = regolamento;
    }
}
