package unicam.ids2526.gal.progetto_hackhub_gal.application.handlers;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.Hackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.InIscrizione;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.StatoHackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.HackathonRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.UtenteRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class HackathonHandler {
    private final HackathonRepository hackathonRep;
    private final UtenteRepository  utenteRep;

    public HackathonHandler(HackathonRepository hackathonRep, UtenteRepository utenteRep) {
        this.hackathonRep = hackathonRep;
        this.utenteRep = utenteRep;
    }

    public void creaHackathon(String nomeHackathon, Double premio,
                              Integer dimensioneTeam, MultipartFile regolamento,
                              String username) throws Exception {
        //TODO implementare giudice e mentori

        /// Validazione della richiesta
        if(hackathonRep.findByNome(nomeHackathon).isPresent()||nomeHackathon.isEmpty()){
            throw new Exception("Nome per l'hackathon non valido");
        }

        if(premio.isNaN()||premio<100){
            throw new Exception("Premio non valido");
        }

        if(dimensioneTeam==null||dimensioneTeam<1){
            throw new Exception("Dimensione team non valida");
        }

        if(regolamento==null||regolamento.isEmpty()){
            System.out.println(regolamento);
            throw new Exception("Regolamento non valido");
        }

        if(!regolamento.getOriginalFilename().endsWith(".pdf")
                && !regolamento.getOriginalFilename().endsWith(".txt")){
            throw new Exception("Formato del regolamento non valido");
        }

        /// Creazione dell'hackathon
        File regolamentoFile=new File("src/main/resources/static/regolamenti/"+regolamento.getOriginalFilename());
        try{
            regolamentoFile.createNewFile();
            FileOutputStream fileStream = new FileOutputStream(regolamentoFile);
            fileStream.write(regolamento.getBytes());
            fileStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        Utente organizzatore=utenteRep.findByUsername(username).orElseThrow();
        Hackathon hackathon=new Hackathon(nomeHackathon, premio, dimensioneTeam, regolamentoFile.getPath(), organizzatore);
        StatoHackathon stato=new InIscrizione();
        hackathon.setStato(stato);
        hackathonRep.save(hackathon);
    }
}
