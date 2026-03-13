package unicam.ids2526.gal.progetto_hackhub_gal.application.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class CreaHackathonDTO {
    private String nomeHackathon;
    private Double premio;
    private Integer dimensioneTeam;
    private MultipartFile regolamento;
    private String giudice;
    private List<String> mentori;


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

    public String getGiudice() {
        return giudice;
    }

    public void setGiudice(String giudice) {
        this.giudice = giudice;
    }

    public List<String> getMentori() {
        return mentori;
    }

    public void setMentori(List<String> mentori) {
        this.mentori = mentori;
    }
}
