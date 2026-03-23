package unicam.ids2526.gal.progetto_hackhub_gal.application.handlers;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.Hackathon;
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

@Service
public class HackathonHandler {
    private final HackathonRepository hackathonRep;
    private final UtenteRepository  utenteRep;
    private final TeamRepository teamRep;

    public HackathonHandler(HackathonRepository hackathonRep, UtenteRepository utenteRep,
                            TeamRepository teamRep) {
        this.hackathonRep = hackathonRep;
        this.utenteRep = utenteRep;
        this.teamRep = teamRep;
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

    public File visualizzaRegolamento(String nomeHackathon) throws Exception{
        // recupero l'Hackathon dal nome
        Hackathon h = hackathonRep.findByNome(nomeHackathon).orElseThrow(
                ()->new Exception("Hackathon non esistente"));

        File regolamento=new File(h.getRegolamento());
        return regolamento;
    }

    public List<Hackathon> elencoHackathon() throws Exception{
        List<Hackathon> listaHackathon = hackathonRep.findAll();

        // controllo se la lista è vuota
        if (listaHackathon.isEmpty()) {
            throw new Exception("Non ci sono Hackathon");
        }

        // restituisco gli hackathon Trovati;
        return listaHackathon;
    }

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

        List<Utente> Mentori = hackathon.getMentori();
        for (Utente m : Mentori) {
            if (m.getUserId() == mentore.getUserId()) {
                throw new Exception("Errore: questo mentore è già assegnato a questo hackathon");
            }
        }

        // aggiunta del mentore e salvataggio
        hackathon.addMentore(mentore);
        hackathonRep.save(hackathon);

    }
}
