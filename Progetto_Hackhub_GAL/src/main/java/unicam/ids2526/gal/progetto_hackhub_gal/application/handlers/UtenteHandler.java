package unicam.ids2526.gal.progetto_hackhub_gal.application.handlers;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.BuilderUtente;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.ConcreteBuilderUtente;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.UtenteRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.security.JwtUtil;
import java.util.Optional;

@Service
@Transactional
public class UtenteHandler {
    private final UtenteRepository utenteRep;
    private final BuilderUtente builderUtente;
    private final JwtUtil jwtUtil;

    public UtenteHandler(UtenteRepository utenteRep, JwtUtil jwtUtil) {
        this.utenteRep = utenteRep;
        this.jwtUtil = jwtUtil;
        this.builderUtente = new ConcreteBuilderUtente();
    }


    /**
     * Effettua il login di un utente e restituisce un token JWT se le credenziali sono corrette
     *
     * @param username l'username dell'utente che vuole effettuare il login
     * @param password la password dell'utente
     * @return Optional contenente il token JWT se il login ha successo, Optional vuoto altrimenti
     */
    public Optional<String> login(String username, String password) {
        Optional<Utente> utenteOpt = utenteRep.findByUsername(username);

        if (utenteOpt.isPresent()) {
            Utente utente = utenteOpt.get();
            // Logica di business: confronto password e generazione token
            if (utente.getPassword().equals(password)) {
                return Optional.of(jwtUtil.generateToken(utente.getUsername(), utente.getRuolo().name()));
            }
        }
        return Optional.empty();
    }


    /**
     * Registra un nuovo utente nel sistema
     *
     * @param email l'email dell'utente da registrare
     * @param username l'username scelto dall'utente
     * @param password la password dell'utente
     * @param ruolo il ruolo dell'utente (UTENTE, GIUDICE, ORGANIZZATORE, MENTORE)
     * @return void
     * @throws Exception se l'email non è valida, se l'username è nullo o già utilizzato,
     *                   se la password è nulla o se il ruolo non è valido
     */
    public void registrazione(String email,
                              String username,
                              String password,
                              String ruolo) throws Exception {

        if(email==null || !email.contains("@")){
            throw new Exception("Errore: Email non valida");
        }
        if(username==null || utenteRep.findByUsername(username).isPresent()){
            throw new Exception("Errore: Username non valido o già utilizzato");
        }
        if(password==null){
            throw new Exception("Errore: Password non valida");
        }
        ruolo=ruolo.toUpperCase();
        switch (ruolo){
            case "UTENTE", "GIUDICE", "ORGANIZZATORE", "MENTORE"->{}
            default ->  throw new Exception("Errore: Ruolo non valido");
        }

        /// Costruzione dell'utente tramite il builder
        builderUtente.resetUtente();
        builderUtente.setEmail(email);
        builderUtente.setUsername(username);
        builderUtente.setPassword(password);
        builderUtente.setRuolo(ruolo);
        Utente utente=builderUtente.getUtenteFinale();

        utenteRep.save(utente);
    }
}