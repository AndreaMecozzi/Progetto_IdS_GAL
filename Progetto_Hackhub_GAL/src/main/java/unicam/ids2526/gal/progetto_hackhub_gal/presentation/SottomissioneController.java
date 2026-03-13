package unicam.ids2526.gal.progetto_hackhub_gal.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unicam.ids2526.gal.progetto_hackhub_gal.application.handlers.SottomissioneHandler;

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
    public ResponseEntity<Object> creaSottomissione(Authentication authentication){
        String username = authentication.getName();
        try{
            sottomissioneHandler.creaSottomissione(username);
            return new ResponseEntity<Object>("Sottomissione creata", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasAuthority('UTENTE')")
    @PostMapping(value = "/aggiorna", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> aggiornaSottomissione(Authentication authentication, @RequestParam MultipartFile file) {
        String username = authentication.getName();
        try {
            sottomissioneHandler.aggiornaSottomissione(username, file);
            return new ResponseEntity<>("Sottomissione aggiornata con il nuovo file", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }





    @PreAuthorize("hasAuthority('UTENTE')")
    @GetMapping("/visualizza")
    public ResponseEntity<Object> visualizzaSottomissione(Authentication authentication) {
        String username= authentication.getName();
        try {
            return new ResponseEntity<>(sottomissioneHandler.visualizzaSottomissione(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
