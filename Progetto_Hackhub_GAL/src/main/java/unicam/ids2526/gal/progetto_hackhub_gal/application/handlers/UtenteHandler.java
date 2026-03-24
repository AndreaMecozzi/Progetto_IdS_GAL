package unicam.ids2526.gal.progetto_hackhub_gal.application.handlers;

import org.springframework.stereotype.Service;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.BuilderUtente;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.ConcreteBuilderUtente;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.UtenteRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.security.JwtUtil;
import java.util.Optional;

@Service
public class UtenteHandler {

    private final UtenteRepository utenteRep;
    private final BuilderUtente builderUtente;
    private final JwtUtil jwtUtil;

    public UtenteHandler(UtenteRepository utenteRep, JwtUtil jwtUtil) {
        this.utenteRep = utenteRep;
        this.jwtUtil = jwtUtil;
        this.builderUtente = new ConcreteBuilderUtente();
    }

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