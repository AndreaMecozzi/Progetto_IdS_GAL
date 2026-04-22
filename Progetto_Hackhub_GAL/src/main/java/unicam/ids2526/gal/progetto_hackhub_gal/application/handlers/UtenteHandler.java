package unicam.ids2526.gal.progetto_hackhub_gal.application.handlers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.BuilderUtente;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.ConcreteBuilderUtente;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.UtenteRepository;

/**
 * Gestisce la logica applicativa relativa agli utenti.
 *
 * Coordina le operazioni di creazione e gestione degli utenti,
 * utilizzando il pattern Builder per la costruzione degli oggetti utente
 * e delegando l'autenticazione a Spring Security.
 */
@Service
@Transactional
public class UtenteHandler {

    private final UtenteRepository utenteRep;
    private final BuilderUtente builderUtente;
    private final PasswordEncoder passwordEncoder;

    /**
     * Inizializza il servizio con i componenti necessari.
     *
     * @param utenteRep       repository per l’accesso ai dati degli utenti
     * @param passwordEncoder utility di Spring Security per criptare le password
     */
    public UtenteHandler(UtenteRepository utenteRep, PasswordEncoder passwordEncoder) {
        this.utenteRep = utenteRep;
        this.passwordEncoder = passwordEncoder;
        this.builderUtente = new ConcreteBuilderUtente();
    }

    /**
     * Recupera il ruolo di un utente a partire dal suo username.
     * Questo metodo viene utilizzato dal Controller dopo l'autenticazione di Spring Security
     * per inserire il ruolo corretto all'interno del Token JWT.
     *
     * @param username l'username dell'utente
     * @return Stringa che rappresenta il ruolo dell'utente
     * @throws Exception se l'utente non viene trovato nel database
     */
    public String getRuoloByUsername(String username) throws Exception {
        Utente utente = utenteRep.findByUsername(username)
                .orElseThrow(() -> new Exception("Errore: Utente non trovato"));
        return utente.getRuolo().name();
    }

    /**
     * Registra un nuovo utente nel sistema, criptando la password in modo sicuro.
     *
     * @param email    l'email dell'utente da registrare
     * @param username l'username scelto dall'utente
     * @param password la password in chiaro fornita dall'utente
     * @param ruolo    il ruolo dell'utente (UTENTE, GIUDICE, ORGANIZZATORE, MENTORE)
     * @throws Exception se l'email non è valida, se l'username è nullo o già utilizzato,
     * se la password è nulla o se il ruolo non è valido
     */
    public void registrazione(String email,
                              String username,
                              String password,
                              String ruolo) throws Exception {

        if (email == null || !email.contains("@")) {
            throw new Exception("Errore: Email non valida");
        }
        if (username == null || utenteRep.findByUsername(username).isPresent()) {
            throw new Exception("Errore: Username non valido o già utilizzato");
        }
        if (password == null || password.isBlank()) {
            throw new Exception("Errore: Password non valida");
        }

        ruolo = ruolo.toUpperCase();
        switch (ruolo) {
            case "UTENTE", "GIUDICE", "ORGANIZZATORE", "MENTORE" -> {}
            default -> throw new Exception("Errore: Ruolo non valido");
        }

        //criptaggio della password tramite Spring Security (BCrypt)
        String passwordCriptata = passwordEncoder.encode(password);

        //costruzione dell'utente tramite il builder
        builderUtente.resetUtente();
        builderUtente.setEmail(email);
        builderUtente.setUsername(username);
        builderUtente.setPassword(passwordCriptata);
        builderUtente.setRuolo(ruolo);

        Utente utente = builderUtente.getUtenteFinale();

        //salvataggio nel database
        utenteRep.save(utente);
    }
}