package unicam.ids2526.gal.progetto_hackhub_gal.application.handlers;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.Hackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.sottomissioni.Sottomissione;
import unicam.ids2526.gal.progetto_hackhub_gal.core.sottomissioni.Valutazione;
import unicam.ids2526.gal.progetto_hackhub_gal.core.sottomissioni.SottomissioneDTO;
import unicam.ids2526.gal.progetto_hackhub_gal.core.team.Team;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Ruolo;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SottomissioneHandler {

    private final SottomissioneRepository sottomissioneRep;
    private final TeamRepository teamRep;
    private final HackathonRepository hackathonRep;
    private final UtenteRepository utenteRep;
    private final ValutazioneRepository valutazioneRep;

    public SottomissioneHandler(SottomissioneRepository sottomissioneRep,
                                TeamRepository teamRep,
                                HackathonRepository hackathonRep,
                                UtenteRepository utenteRep,
                                ValutazioneRepository valutazioneRep) {
        this.sottomissioneRep = sottomissioneRep;
        this.teamRep = teamRep;
        this.hackathonRep = hackathonRep;
        this.utenteRep = utenteRep;
        this.valutazioneRep=valutazioneRep;
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

        if(h.getStato().equals("IN_CORSO")){
            // Creazione dell'entità sottomissione legata al team trovato
            Sottomissione sottomissione = new Sottomissione(t);
            // Aggiunge la sottomissione al team
            t.setSottomissione(sottomissione);
            sottomissioneRep.save(sottomissione);
            teamRep.save(t);
        }else{
            switch(h.getStato()){
                case "IN_ISCRIZIONE"->throw new Exception("Errore: L'hackathon non è ancora iniziato");
                case "IN_VALUTAZIONE"->throw new Exception("Errore: La sottomissione è in valutazione");
                case "CONCLUSO"->throw new Exception("Errore: L'hackathon è concluso");
            }
        }

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

        // ricava l'hackathon a cui il team partecipa
        Hackathon h = t.getHackathon();
        if (h == null) {
            throw new RuntimeException("Errore: Hackathon non trovato per questo team");
        }

        // verifica sullo stato dell'Hackathon e sul formato del file che si vuole aggiungere

        if(h.getStato().equals("IN_CORSO")){
            if(file==null||file.isEmpty()){
                System.out.println(file);
                throw new Exception("Errore: File non valido");
            }
            if(!file.getOriginalFilename().endsWith(".zip")){
                throw new Exception("Errore: Formato del file non valido");
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
        }else{
            switch(h.getStato()){
                case "IN_ISCRIZIONE"->throw new Exception("Errore: L'hackathon non è ancora iniziato");
                case "IN_VALUTAZIONE"->throw new Exception("Errore: La sottomissione è in valutazione");
                case "CONCLUSO"->throw new Exception("Errore: L'hackathon è concluso");
            }
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

    public List<SottomissioneDTO> visualizzaSottomissioni(String username,
                                                          String nomeHackathon) throws Exception {
        Hackathon hackathon=hackathonRep.findByNome(nomeHackathon).orElseThrow(
                ()->new Exception("Errore: Hackathon non esistente"));

        List<Team> teams=hackathon.getTeamPartecipanti();
        List<Sottomissione> sottomissioni=new ArrayList<Sottomissione>();
        List<SottomissioneDTO> sottomissioniDTO = new ArrayList<>();

        for(Team team:teams){
            Sottomissione s = team.getSottomissione();
            if(s!=null){
                sottomissioni.add(s);
                String voto = (s.getValutazione() != null) ? String.valueOf(s.getValutazione().getVoto()) : "Non ancora valutata";

                SottomissioneDTO dto = new SottomissioneDTO(
                        s.getSottomissioneID(),
                        s.getNome(),
                        voto
                );
                sottomissioniDTO.add(dto);
            }

        }

        if(sottomissioniDTO.isEmpty()){
            throw new Exception("Errore: non ci sono sottomissioni caricate");
        }
        return sottomissioniDTO;
    }

    public void valutaSottomissione(String username,
                                    String nomeTeam,
                                    int voto,
                                    String descrizione) throws Exception {

        if(voto<0||voto>10){
            throw new Exception("Errore: Voto non valido");
        }

        if(descrizione.isEmpty()){
            throw new Exception("Errore: Descrizione non valida");
        }

        Team team=teamRep.findByNome(nomeTeam).orElseThrow(
                ()->new Exception("Errore: Team non esistente"));
        Hackathon hackathon=team.getHackathon();
        if(hackathon==null){
            throw new Exception("Errore: Il team non è iscritto a nessun hackathon");
        }

        if(!username.equals(hackathon.getGiudice().getUsername())){
            throw new Exception("Errore: Non sei nominato per l'hackathon " +
                    "a cui partecipa il team specificato");
        }

        if(hackathon.getStato().equals("IN_VALUTAZIONE")){
            Sottomissione sottomissione=team.getSottomissione();
            if(sottomissione==null){
                throw new Exception("Errore: Il team non ha creato una sottomissione");
            }
            Valutazione valutazione=new Valutazione(voto, descrizione);
            valutazioneRep.save(valutazione);
            sottomissione.setValutazione(valutazione);
            sottomissioneRep.save(sottomissione);
        }else{
            throw new Exception("Errore: L'Hackathon non è in valutazione");
        }

    }


    /**
     * Restituisce il percorso del file della sottomissione identificata dall'ID.
     * - UTENTE: verifica che appartenga al team di quella sottomissione
     * - GIUDICE: verifica che sia il giudice dell'hackathon di quella sottomissione
     *
     * @param username        l'utente autenticato
     * @param sottomissioneId l'ID della sottomissione da scaricare
     * @throws Exception se la sottomissione non esiste, il file manca, o l'utente non è autorizzato
     */
    public File scaricaSottomissione(String username,
                                       Long sottomissioneId) throws Exception {

        // recupero della sottomissione
        Sottomissione sottomissione = sottomissioneRep.findById(sottomissioneId).orElseThrow(
                () -> new Exception("Errore: Sottomissione non trovata"));
        // recupero del team
        Team team = sottomissione.getTeam();
        // recupero dell'hackathon
        Hackathon hackathon = team.getHackathon();

        // controlli sul richiedente
        Utente richiedente = utenteRep.findByUsername(username).orElseThrow(
                () -> new Exception("Errore: richiedente non esistente"));

        if(richiedente.getRuolo().equals(Ruolo.GIUDICE)){
            if(!hackathon.getGiudice().equals(richiedente)){
                throw new Exception("Errore: Non sei il giudice dell'hackathon in cui è stata caricata questa sottomissione");
            }
        }

        if(richiedente.getRuolo().equals(Ruolo.UTENTE)) {
            if (!team.getUtenti().contains(richiedente)) {
                throw new Exception("Errore: Non sei membro del team che ha creato questa sottomissione");
            }
        }

        String filePath = sottomissione.getFile();
        if (filePath == null || filePath.isBlank()) {
            throw new Exception("Errore: Nessun file caricato per questa sottomissione");
        }

        File fileSottomissione=new File(filePath);
        return fileSottomissione;
    }
}

