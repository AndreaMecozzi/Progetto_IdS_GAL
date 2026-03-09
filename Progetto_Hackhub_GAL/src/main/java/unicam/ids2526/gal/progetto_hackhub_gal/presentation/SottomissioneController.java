package unicam.ids2526.gal.progetto_hackhub_gal.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unicam.ids2526.gal.progetto_hackhub_gal.application.SottomissioneHandler;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Hackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Team;

import java.io.File;

@RestController
@RequestMapping("/sottomissioni")
public class SottomissioneController {
    private final SottomissioneHandler sottomissioneHandler;

    public SottomissioneController(SottomissioneHandler sottomissioneHandler){
        this.sottomissioneHandler = sottomissioneHandler;
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @PostMapping("/creaSottomisione")
    public ResponseEntity<Object> creaSottomissione(@RequestBody Team team){
        try{
            sottomissioneHandler.creaSottomissione(team);
            return new ResponseEntity<Object>("Sottomissione creata", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @PutMapping("/aggiorna/{nomeTeam}")
    public ResponseEntity<Object> aggiornaSottomissione(@PathVariable String nomeTeam, @RequestBody File file) {
        try {
            sottomissioneHandler.aggiornaSottomissione(nomeTeam, file);
            return new ResponseEntity<>("Sottomissione aggiornata con il nuovo file", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @GetMapping("/visualizza/{nomeTeam}/{nomeHackathon}")
    public ResponseEntity<Object> visualizzaSottomissione(@PathVariable String nomeTeam, @PathVariable String nomeHackathon) {
        try {
            return new ResponseEntity<>(sottomissioneHandler.visualizzaSottomissione(nomeTeam, nomeHackathon), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
