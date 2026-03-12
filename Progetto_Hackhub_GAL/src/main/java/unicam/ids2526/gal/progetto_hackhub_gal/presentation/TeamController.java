package unicam.ids2526.gal.progetto_hackhub_gal.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unicam.ids2526.gal.progetto_hackhub_gal.application.TeamHandler;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Team;

@RestController
@RequestMapping("/team")
public class TeamController {
    private final TeamHandler teamHandler;

    public TeamController(TeamHandler teamHandler) {
        this.teamHandler = teamHandler;
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @GetMapping("/visualizzaInfoTeam/{nomeTeam}")
    public ResponseEntity<Object> visualizzaInfoTeam(Authentication authentication , @PathVariable String nomeTeam) {
        try{
            //estraggo dal JWT l'username
            String username = authentication.getName();

            //chiama il metodo dell'handler passando l'username e il nome del team
            Team team = teamHandler.visualizzaTeam(username,nomeTeam);
            return new ResponseEntity<>(team, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
