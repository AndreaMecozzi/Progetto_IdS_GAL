package unicam.ids2526.gal.progetto_hackhub_gal.application.handlers;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.Hackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.sottomissioni.Sottomissione;
import unicam.ids2526.gal.progetto_hackhub_gal.core.team.Team;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.HackathonRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.SottomissioneRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.TeamRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class SottomissioneHandler {

    private final SottomissioneRepository sottomissioneRep;
    private final TeamRepository teamRep;

    public SottomissioneHandler(SottomissioneRepository sottomissioneRep, TeamRepository teamRep,HackathonRepository hackathonRep) {
        this.sottomissioneRep = sottomissioneRep;
        this.teamRep = teamRep;
    }

    /**
     * Crea una nuova sottomissione per un team specifico
     *
     * @param username l'utente autenticato che crea la sottomissione
     * @throws Exception se il team fornito non esiste nel database
     */

    public void creaSottomissione(String username) throws Exception {
        Team t = teamRep.findByUtenti_Username(username).orElseThrow(
                () -> new Exception("Errore: Devi fare parte di un team"));

        // controllo esistenza sottomissione per questo team
        if (sottomissioneRep.existsByTeam(t)) {
            throw new Exception("Sottomissione già esistente");
        }

        // ricava l'hackathon a cui il team partecipa
        Hackathon h = t.getHackathon();
        if (h == null) {
            throw new RuntimeException("Hackathon non trovato per questo team");
        }

        // Creazione dell'entità sottomissione legata al team trovato
        Sottomissione sottomissione = new Sottomissione(t);
        // Aggiunge la sottomissione al team
        t.setSottomissione(sottomissione);
        sottomissioneRep.save(sottomissione);
        teamRep.save(t);
    }

    /**
     * Aggiorna una sottomissione esistente aggiungendo o modificando il file allegato
     *
     * @param username l'utente autenticato che aggiorna la sottomissione
     * @param file il nuovo documento/file da associare alla sottomissione
     * @throws Exception se non viene trovata alcuna sottomissione precedente per il team indicato
     */

    public void aggiornaSottomissione(String username, MultipartFile file) throws Exception {
        // cerca il team relativo all'utente che vuole aggiornare la sottomissione
        Team t = teamRep.findByUtenti_Username(username).orElseThrow(
                () -> new Exception("Errore: Devi fare parte di un team"));

        // recupera la sottomissione
        Sottomissione sottomissione = sottomissioneRep.findByTeamNome(t.getNome()).orElseThrow(
                () -> new Exception("Errore: Nessuna sottomissione trovata per questo team"));

        // ricava l'id del team
        Long teamId = sottomissione.getTeam().getTeamId();

        // ricava l'hackathon a cui il team partecipa
        Hackathon h = t.getHackathon();
        if (h == null) {
            throw new RuntimeException("Hackathon non trovato per questo team");
        }

        // verifica sullo stato dell'Hackathon e sul formato del file che si vuole aggiungere
        try{
            if(h.getStato().equals("IN_CORSO")){
                if(file==null||file.isEmpty()){
                    System.out.println(file);
                    throw new Exception("file non valido");
                }
                if(!file.getOriginalFilename().endsWith(".zip")){
                    throw new Exception("Formato del file non valido");
                }
                // creato il file specificando il percorso e il nome
                File fileSottomissione= new File("src/main/resources/static/sottomissioni/"+file.getOriginalFilename());

                if (!fileSottomissione.getParentFile().exists()) {
                    fileSottomissione.getParentFile().mkdirs();
                }

                try{
                    fileSottomissione.createNewFile();
                    FileOutputStream fileStream = new FileOutputStream(fileSottomissione);
                    fileStream.write(file.getBytes());
                    fileStream.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
                // assegnazione del file alla sottomissione e salvataggio
                sottomissione.setFile(fileSottomissione.getPath());
                sottomissioneRep.save(sottomissione);
            }
        }catch(Exception e){
            throw new Exception("Errore: impossibile aggiornare la sottomissione");
        }
    }

    /**
     * Restituisce la sottomissione effettuata da un team per un determinato hackathon
     *
     * @param username l'utente che richiede di visualizzare la sottomissione del proprio
     *                 team
     * @throws Exception se non esiste una sottomissione che soddisfi entrambi i criteri
     */
    public Sottomissione visualizzaSottomissione(String username) throws Exception {
        Team team=teamRep.findByUtenti_Username(username).orElseThrow(
                () -> new Exception("Errore: Team non esistente"));

        Long teamId= team.getTeamId();

        return sottomissioneRep.findByTeam_TeamId(teamId).orElseThrow(
                () -> new Exception("Errore: la sottomissione non esiste"));
    }
}

