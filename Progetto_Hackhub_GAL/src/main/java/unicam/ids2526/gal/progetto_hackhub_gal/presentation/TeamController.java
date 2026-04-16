package unicam.ids2526.gal.progetto_hackhub_gal.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import unicam.ids2526.gal.progetto_hackhub_gal.application.handlers.TeamHandler;
import unicam.ids2526.gal.progetto_hackhub_gal.core.team.Team;
import unicam.ids2526.gal.progetto_hackhub_gal.core.team.TeamDTO;

import java.util.List;

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
        try {
            String username = authentication.getName();
            TeamDTO teamDto = teamHandler.visualizzaInfoTeam(username);
            return new ResponseEntity<>(teamDto, HttpStatus.OK);
        } catch (Exception e) {
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

    @PreAuthorize("hasAuthority('UTENTE')")
    @PostMapping("/abbandona")
    public ResponseEntity<Object> abbandonaTeam (Authentication authentication){
        String username=authentication.getName();
        try{
            teamHandler.abbandonaTeam(username);
            return new ResponseEntity<>("Abbandono del team avvenuto con successo",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('GIUDICE','MENTORE','ORGANIZZATORE')")
    @GetMapping("/visualizza")
    public ResponseEntity<Object> visualizzaTeam(Authentication authentication,
                                                 @RequestParam Long teamId) {
        String username = authentication.getName();
        try {
            TeamDTO teamDTO = teamHandler.visualizzaTeam(username, teamId);
            return new ResponseEntity<>(teamDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/elenco")
    public ResponseEntity<Object> elencoTeam() {
        try {
            List<TeamDTO> teamDTOs = teamHandler.consultareElencoTeam();
            return new ResponseEntity<>(teamDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @PostMapping("/rimuovi")
    public ResponseEntity<Object> rimuoviHackathon(Authentication authentication,@RequestBody String nomeTeam){
        String username=authentication.getName();
        try{
            teamHandler.rimuoviTeam(username, nomeTeam);
            return new ResponseEntity<>("Team rimosso con successo",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}


