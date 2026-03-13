package unicam.ids2526.gal.progetto_hackhub_gal.application.handlers;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.Hackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.InIscrizione;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.StatoHackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Ruolo;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.HackathonRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.UtenteRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                              String userOrg, String userGiudice, List<String> usersMentori) throws Exception {
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

        Utente giudice = utenteRep.findByUsername(userGiudice).orElseThrow(
                () -> new Exception("Giudice non esistente")
        );

        if(giudice.getRuolo()!= Ruolo.GIUDICE){
            throw new Exception("Il giudice deve avere il ruolo GIUDICE");
        }

        List<Utente> mentori= new ArrayList<>() {
        };
        for(String user: usersMentori){
            Utente mentore=utenteRep.findByUsername(user).orElseThrow(
                    () -> new Exception("Mentore non esistente"));
            if(mentore.getRuolo()!=Ruolo.MENTORE){
                throw new Exception("Il mentore deve avere il ruolo MENTORE");
            }
            mentori.add(mentore);
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

        Utente organizzatore=utenteRep.findByUsername(userOrg).orElseThrow();

        Hackathon hackathon=new Hackathon(nomeHackathon, premio, dimensioneTeam, regolamentoFile.getPath(),
                organizzatore, giudice, mentori);
        StatoHackathon stato=new InIscrizione();
        hackathon.setStato(stato);
        hackathonRep.save(hackathon);
    }
}
