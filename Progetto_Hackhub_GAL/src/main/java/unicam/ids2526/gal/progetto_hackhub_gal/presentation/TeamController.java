package unicam.ids2526.gal.progetto_hackhub_gal.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import unicam.ids2526.gal.progetto_hackhub_gal.application.handlers.TeamHandler;
import unicam.ids2526.gal.progetto_hackhub_gal.core.team.Team;

@RestController
@RequestMapping("/team")
public class TeamController {
    private final TeamHandler teamHandler;

    public TeamController(TeamHandler teamHandler) {
        this.teamHandler = teamHandler;
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @PostMapping("/crea")
    public ResponseEntity<Object> creaTeam(Authentication authentication, @RequestBody String nomeTeam) {
        String username= authentication.getName();
        try{
            teamHandler.creaTeam(username, nomeTeam);
            return new ResponseEntity<>("Team creato con successo", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @GetMapping("/visualizzaInfo")
    public ResponseEntity<Object> visualizzaInfoTeam(Authentication authentication) {
        try{
            //estraggo dal JWT l'username
            String username = authentication.getName();

            //chiama il metodo dell'handler passando l'username
            Team team = teamHandler.visualizzaTeam(username);
            return new ResponseEntity<>(team, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @PostMapping("/modifica")
    public ResponseEntity<Object> modificaTeam(Authentication authentication, @RequestBody String nuovoNome){
        String username=authentication.getName();
        try{
            teamHandler.modificaTeam(username, nuovoNome);
            return new ResponseEntity<>("Team modificato con successo",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
