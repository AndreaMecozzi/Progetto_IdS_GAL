package unicam.ids2526.gal.progetto_hackhub_gal.application.handlers;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.Hackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.HackathonDTO;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.InIscrizione;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.StatoHackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.team.Team;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Ruolo;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.HackathonRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.TeamRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.UtenteRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestisce la logica applicativa relativa agli hackathon
 *
 * Coordina le operazioni tra i repository di hackathon , utenti e team
 * garantendo la conoscienza dei dati tramite gestione trasazionale.
 */
@Service
@Transactional
public class HackathonHandler {
    private final HackathonRepository hackathonRep;
    private final UtenteRepository  utenteRep;
    private final TeamRepository teamRep;

    /**
     * Inizializza il servizio con i repository necessari
     *
     * @param hackathonRep repository per l'accesso ai dati degli hackathon
     * @param utenteRep    repository per l'accesso ai dati degli utenti
     * @param teamRep      repository per l'accesso ai dati dei team
     */
    public HackathonHandler(HackathonRepository hackathonRep, UtenteRepository utenteRep,
                            TeamRepository teamRep) {
        this.hackathonRep = hackathonRep;
        this.utenteRep = utenteRep;
        this.teamRep = teamRep;
    }

    /**
     * Crea un nuovo hackathon e lo salva nel sistema
     *
     * @param nomeHackathon   il nome univoco dell'hackathon
     * @param premio          il montepremi dell'hackathon (minimo 100)
     * @param dimensioneTeam  il numero massimo di membri per team (minimo 1)
     * @param regolamento     il file del regolamento in formato PDF o TXT
     * @param userOrg         lo username dell'utente organizzatore
     * @param userGiudice     lo username dell'utente con ruolo GIUDICE
     * @param usersMentori    la lista degli username degli utenti con ruolo MENTORE
     *
     * @throws Exception se il nome dell'hackathon è già in uso o vuoto
     * @throws Exception se il premio è invalido o inferiore a 100
     * @throws Exception se la dimensione del team è nulla o inferiore a 1
     * @throws Exception se il regolamento è nullo, vuoto o in un formato non supportato
     * @throws Exception se il giudice non esiste o non possiede il ruolo GIUDICE
     * @throws Exception se uno dei mentori non esiste o non possiede il ruolo MENTORE
     * @throws RuntimeException se si verifica un errore durante il salvataggio del file regolamento
     */
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


    /**
     * Iscrive il team di un utente ad un hackathon
     *
     * @param username      l' username dell'utente che richiede l'iscrizione
     * @param nomeHackathon il nome dell'hackathon a cui iscriversi
     *
     * @throws Exception se l'utente non appartiene ad alcun team
     * @throws Exception se il team è già iscritto ad un hackathon
     * @throws Exception se l'hackathon specificato non esiste
     * @throws Exception se l'hackathon non è nella fase di iscrizione
     * @throws Exception se la dimensione del team supera quella massima prevista dall'hackathon
     */
    public void iscriviTeam(String username, String nomeHackathon) throws Exception {
        Team team=teamRep.findByUtenti_Username(username).orElseThrow(
                ()->new Exception("Bisogna avere un team per iscriversi"));

        if(team.getHackathon()!=null){
            throw new Exception("Il team è già iscritto ad un hackathon");
        }

        Hackathon hackathon=hackathonRep.findByNome(nomeHackathon).orElseThrow(
                ()->new Exception("Hackathon non esistente"));

        if(!hackathon.getStato().equals("IN_ISCRIZIONE")){
            throw new Exception("Il periodo di iscrizione è terminato");
        }

        if(hackathon.getDimenisoneTeam()<team.getUtenti().size()){
            throw new Exception("Dimensione del team non adatta per l'iscrizione");
        }

        hackathon.addTeam(team);
        hackathonRep.save(hackathon);

        team.setHackathon(hackathon);
        teamRep.save(team);
    }


    /**
     * Recupera il file del regolamento di un hackathon
     *
     * @param nomeHackathon il nome dell'hackathon di cui visualizzare il regolamento
     *
     * @return il file del regolamento associato all'hackathon
     *
     * @throws Exception se l'hackathon specificato non esiste
     */
    public File visualizzaRegolamento(String nomeHackathon) throws Exception{
        // recupero l'Hackathon dal nome
        Hackathon h = hackathonRep.findByNome(nomeHackathon).orElseThrow(
                ()->new Exception("Hackathon non esistente"));

        File fileRegolamento=new File(h.getRegolamento());
        return fileRegolamento;
    }


    /**
     * Restituisce l'elenco di tutti gli hackathon presenti nel sistema
     *
     * @return la lista di tutti gli hackathon registrati
     *
     * @throws Exception se non è presente alcun hackathon nel sistema
     */
    public List<HackathonDTO> elencoHackathon() throws Exception {
        List<Hackathon> listaHackathon = hackathonRep.findAll();

        if (listaHackathon.isEmpty()) {
            throw new Exception("Non ci sono Hackathon");
        }

        // Convertiamo ogni Hackathon della lista in un HackathonDTO
        return listaHackathon.stream().map(h -> new HackathonDTO(
                h.getNome(),
                h.getPremio(),
                h.getDimenisoneTeam(),
                h.getDataInizioStato().toString(),
                h.getStato(),
                h.getTeamPartecipanti().stream().map(Team::getNome).toList(),
                h.getOrganizzatore().getUsername(),
                h.getOrganizzatore().getEmail(),
                h.getGiudice().getEmail(),
                h.getMentori().stream().map(Utente::getUsername).toList(),
                h.getMentori().stream().map(Utente::getEmail).toList()
        )).toList();
    }


    /**
     * Rimuove un hackathon dal sistema se ancora in fase di iscrizione
     *
     * @param username      l' username dell'organizzatore che richiede la rimozione
     * @param nomeHackathon il nome dell'hackathon da rimuovere
     *
     * @throws Exception se l'hackathon specificato non esiste
     * @throws Exception se l'utente non è l'organizzatore dell'hackathon
     * @throws Exception se l'hackathon non è più in fase di iscrizione
     */
    @Transactional
    public void rimuoviHackathon(String username, String nomeHackathon) throws Exception{
        // recupero l'hackathon dal nome
        Hackathon hackathon= hackathonRep.findByNome(nomeHackathon).orElseThrow(
                ()->new Exception("Errore: Hackathon non esistente"));

        // recupero l'organizzatore
        if(!username.equals(hackathon.getOrganizzatore().getUsername())){
            throw new Exception("Errore: L'hackathon specificato non è organizzato da te");
        }

        // controllo che l'hackathon sia ancora in fase "IN_ISCRIZIONE"
        if(!hackathon.getStato().equals("IN_ISCRIZIONE")){
            throw new Exception("Errore: L'hackathon è cominciato e non è possibile cancellarlo");
        }

        //disiscrivo i team dall'hackathon
        List<Team> teams=hackathon.getTeamPartecipanti();
        for(Team team:teams){
            team.setHackathon(null);
            teamRep.save(team);
        }

        //rimozione dell'hackathon
        hackathonRep.deleteByNome(nomeHackathon);
    }


    /**
     * Aggiorna il regolamento di un hackathon ancora in fase di iscrizione
     *
     * @param username      l? username dell'organizzatore che richiede l'aggiornamento
     * @param nomeHackathon il nome dell'hackathon di cui aggiornare il regolamento
     * @param regolamento   il nuovo file del regolamento in formato PDF o TXT
     *
     * @throws Exception se l'hackathon specificato non esiste
     * @throws Exception se l'utente non è l'organizzatore dell'hackathon
     * @throws Exception se l'hackathon non è più in fase di iscrizione
     * @throws Exception se il file del regolamento è nullo o vuoto
     * @throws Exception se il formato del file non è PDF o TXT
     * @throws RuntimeException se si verifica un errore durante il salvataggio del nuovo file regolamento
     */
    public void aggiornaRegolamento(String username, String nomeHackathon, MultipartFile regolamento) throws Exception{
        // recupero l'hackathon dal nome
        Hackathon hackathon= hackathonRep.findByNome(nomeHackathon).orElseThrow(
                ()->new Exception("Errore: Hackathon non esistente"));

        // recupero l'organizzatore
        if(!username.equals(hackathon.getOrganizzatore().getUsername())){
            throw new Exception("Errore: L'hackathon specificato non è organizzato da te");
        }

        // controllo che l'hackathon sia ancora in fase "IN_ISCRIZIONE"
        if(!hackathon.getStato().equals("IN_ISCRIZIONE")){
            throw new Exception("Errore: Impossibile aggiornare il regolamento");
        }

        //controlli sul file del nuovo regolamento
        if(regolamento==null||regolamento.isEmpty()){
            System.out.println(regolamento);
            throw new Exception("Errore: File non valido");
        }

        if((!regolamento.getOriginalFilename().endsWith(".pdf")) && (!regolamento.getOriginalFilename().endsWith(".txt"))){
            throw new Exception("Errore: Formato del file non valido");
        }


        // creato il file specificando il percorso e il nome
        File fileRegolamento= new File("src/main/resources/static/regolamenti/"+regolamento.getOriginalFilename());

        if (!fileRegolamento.getParentFile().exists()) {
            fileRegolamento.getParentFile().mkdirs();
        }

        try{
            fileRegolamento.createNewFile();
            FileOutputStream fileStream = new FileOutputStream(fileRegolamento);
            fileStream.write(regolamento.getBytes());
            fileStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        // assegnazione del nuovo regolamento e salvataggio
        hackathon.setRegolamento(fileRegolamento.getPath());
        hackathonRep.save(hackathon);
    }


    /**
     * Disiscrive il team di un utente dall'hackathon a cui è attualmente iscritto
     *
     * @param username l' username dell'utente che richiede la disiscrizione
     *
     * @throws Exception se l'utente non appartiene ad alcun team
     * @throws Exception se il team non è iscritto ad alcun hackathon
     * @throws Exception se l'hackathon è in fase di valutazione o è già concluso
     */
    public void disiscriviTeam(String username) throws Exception {
        // recupero del team del richiedente
        Team t = teamRep.findByUtenti_Username(username).orElseThrow(
                ()->new Exception("Errore: Devi far parte di un team"));

        // recupero dell'hackathon a cui il team è iscritto
        Hackathon h = t.getHackathon();

        if (h == null){
            throw new Exception("Errore: Devi essere iscritto ad un hackathon");
        }

        // controllo sullo stato dell'hackathon
        String stato = h.getStato();
        if(stato.equals("IN_VALUTAZIONE") || stato.equals("CONCLUSO")){
            throw new Exception("Errore: Impossibile disiscriversi dall'hackathon");
        }

        // rimozione del team dalla lista dei team partecipanti all'hackathon
        h.removeTeam(t);

        // campo Hackathon resettato a null nella classe team
        t.setHackathon(null);

        //salvataggio
        hackathonRep.save(h);
        teamRep.save(t);
    }


    /**
     * Aggiunge un mentore ad un hackathon ancora in fase di iscrizione
     *
     * @param username        l'username dell'organizzatore che richiede l'aggiunta
     * @param nomeHackathon   il nome dell'hackathon a cui aggiungere il mentore
     * @param usernameMentore l'username dell'utente da aggiungere come mentore
     *
     * @throws Exception se l'hackathon specificato non esiste
     * @throws Exception se l'hackathon non è in fase di iscrizione
     * @throws Exception se l'utente specificato come mentore non esiste
     * @throws Exception se l'utente specificato non possiede il ruolo MENTORE
     * @throws Exception se il mentore è già assegnato all'hackathon
     */
    public void aggiungiMentore(String username, String nomeHackathon, String usernameMentore) throws Exception{
        // recupero l'hackathon
        Hackathon hackathon = hackathonRep.findByNome(nomeHackathon).orElseThrow(
                ()->new Exception("Errore: L'hackathon non esiste"));

        // controllo dello stato dell'hackathon
        if(!hackathon.getStato().equals("IN_ISCRIZIONE")){
            throw new Exception("Errore: Impossibile aggiungere mentori");
        }

        // controlli sul mentore
        Utente mentore = utenteRep.findByUsername(usernameMentore).orElseThrow(
                ()->new Exception("Errore: il mentore non esiste"));
        if(!mentore.getRuolo().equals(Ruolo.MENTORE)){
            throw new Exception("Errore: l'utente passato non è un mentore");
        }

        List<Utente> mentori = hackathon.getMentori();
        for (Utente m : mentori) {
            if (m.getUserId() == mentore.getUserId()) {
                throw new Exception("Errore: questo mentore è già assegnato a questo hackathon");
            }
        }

        // aggiunta del mentore e salvataggio
        hackathon.addMentore(mentore);
        hackathonRep.save(hackathon);

    }


    /**
     * Rimuove un mentore da un hackathon ancora in fase di iscrizione
     *
     * @param username        l'username dell'organizzatore che richiede la rimozione
     * @param nomeHackathon   il nome dell'hackathon da cui rimuovere il mentore
     * @param usernameMentore lo username del mentore da rimuovere
     *
     * @throws Exception se l'hackathon specificato non esiste
     * @throws Exception se l'hackathon non è in fase di iscrizione
     * @throws Exception se l'utente specificato come mentore non esiste
     * @throws Exception se l'utente specificato non possiede il ruolo MENTORE
     * @throws Exception se il mentore è l'ultimo rimasto nell'hackathon
     * @throws Exception se il mentore non è assegnato all'hackathon
     */
    public void rimuoviMentore(String username, String nomeHackathon, String usernameMentore) throws Exception{
        // recupero l'hackathon
        Hackathon hackathon = hackathonRep.findByNome(nomeHackathon).orElseThrow(
                ()->new Exception("Errore: L'hackathon non esiste"));

        // controllo dello stato dell'hackathon
        if(!hackathon.getStato().equals("IN_ISCRIZIONE")){
            throw new Exception("Errore: Impossibile rimuovere mentore");
        }

        // controlli sul mentore
        Utente mentore = utenteRep.findByUsername(usernameMentore).orElseThrow(
                ()->new Exception("Errore: il mentore non esiste"));
        if(!mentore.getRuolo().equals(Ruolo.MENTORE)){
            throw new Exception("Errore: l'utente passato non è un mentore");
        }

        List<Utente> mentori = hackathon.getMentori();

        if(mentori.size()==1){
            throw new Exception("Errore: impossibile eliminare l'ultimo mentore rimasto");
        }

        if(mentori.contains(mentore)){
            // rimozione del mentore e salvataggio
            hackathon.removeMentore(mentore);
            hackathonRep.save(hackathon);
        }else{
            throw new Exception("Errore: il mentore non è assegnato a questo hackathon");
        }
    }

    /**
     * Restituisce le informazioni dettagliate di un hackathon per un membro dello staff
     *
     * @param username    l'username del richiedente (deve essere GIUDICE, MENTORE o ORGANIZZATORE)
     * @param hackathonId l'id dell'hackathon da visualizzare
     *
     * @return l'oggetto HackathonDTO con le informazioni dell'hackathon
     *
     * @throws Exception se l'utente non esiste
     * @throws Exception se l'utente non possiede un ruolo staff valido
     * @throws Exception se l'hackathon con l'id specificato non esiste
     */
    public HackathonDTO visualizzaHackathon(String username, Long hackathonId) throws Exception {
           // recupero il richiedente
        Utente richiedente = utenteRep.findByUsername(username).orElseThrow(
                () -> new Exception("Errore: Utente non trovato"));

        // recupero l'hackathon tramite id
        Hackathon hackathon = hackathonRep.findById(hackathonId).orElseThrow(
                () -> new Exception("Errore: Hackathon non trovato"));

        // costruzione del DTO
        List<String> nomiTeam = hackathon.getTeamPartecipanti().stream()
                .map(t -> t.getNome())
                .toList();

        List<String> nomiMentori = hackathon.getMentori().stream()
                .map(u -> u.getUsername())
                .toList();

        List<String> emailMentori = hackathon.getMentori().stream()
                .map(u -> u.getEmail())
                .toList();

        return new HackathonDTO(
                hackathon.getNome(),
                hackathon.getPremio(),
                hackathon.getDimenisoneTeam(),
                hackathon.getDataInizioStato().toString(),
                hackathon.getStato(),
                nomiTeam,
                hackathon.getOrganizzatore().getUsername(),
                hackathon.getOrganizzatore().getEmail(),
                hackathon.getGiudice().getEmail(),
                nomiMentori,
                emailMentori
        );
    }
}
