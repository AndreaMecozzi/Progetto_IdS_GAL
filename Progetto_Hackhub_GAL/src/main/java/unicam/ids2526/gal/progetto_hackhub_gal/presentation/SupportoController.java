package unicam.ids2526.gal.progetto_hackhub_gal.presentation;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import unicam.ids2526.gal.progetto_hackhub_gal.application.handlers.SupportoHandler;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.HackathonDTO;
import unicam.ids2526.gal.progetto_hackhub_gal.core.supporto.Supporto;
import unicam.ids2526.gal.progetto_hackhub_gal.core.supporto.SupportoDTO;

import java.util.List;

@RestController
@RequestMapping("/supporto")
public class SupportoController {
    private final SupportoHandler supportoHandler;

    public SupportoController(SupportoHandler supportoHandler) {
        this.supportoHandler = supportoHandler;
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @PostMapping("/richiedi")
    public ResponseEntity<Object> richiediSupporto(Authentication authentication,
                                                   @RequestBody String richiesta) {
        String username = authentication.getName();
        try {
            supportoHandler.richiediSupporto(username, richiesta);
            return new ResponseEntity<>("Richiesta di supporto inviata con successo", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('MENTORE')")
    @GetMapping("/visualizzaRichieste")
    public ResponseEntity<Object> visualizzaRichieste(Authentication authentication) {
        String username = authentication.getName();
        try {
            List<SupportoDTO> supportiDTOs = supportoHandler.visualizzaRichieste(username);
            return new ResponseEntity<>(supportiDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
