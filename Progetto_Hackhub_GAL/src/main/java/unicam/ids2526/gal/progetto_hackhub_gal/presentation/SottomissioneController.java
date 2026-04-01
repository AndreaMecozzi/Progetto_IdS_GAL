package unicam.ids2526.gal.progetto_hackhub_gal.presentation;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unicam.ids2526.gal.progetto_hackhub_gal.application.handlers.SottomissioneHandler;
import unicam.ids2526.gal.progetto_hackhub_gal.core.sottomissioni.Sottomissione;
import unicam.ids2526.gal.progetto_hackhub_gal.core.sottomissioni.SottomissioneDTO;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/sottomissioni")
public class SottomissioneController {
    private final SottomissioneHandler sottomissioneHandler;

    public SottomissioneController(SottomissioneHandler sottomissioneHandler){
        this.sottomissioneHandler = sottomissioneHandler;
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @PostMapping("/crea")
    public ResponseEntity<Object> creaSottomissione(Authentication authentication){
        String username = authentication.getName();
        try{
            sottomissioneHandler.creaSottomissione(username);
            return new ResponseEntity<Object>("Sottomissione creata", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasAuthority('UTENTE')")
    @PostMapping(value = "/aggiorna", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> aggiornaSottomissione(Authentication authentication,
                                                        @RequestParam MultipartFile file) {
        String username = authentication.getName();
        try {
            sottomissioneHandler.aggiornaSottomissione(username, file);
            return new ResponseEntity<>("Sottomissione aggiornata con il nuovo file", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasAuthority('UTENTE')")
    @GetMapping("/utente/visualizza")
    public ResponseEntity<Object> visualizzaSottomissione(Authentication authentication) {
        String username = authentication.getName();
        try {
            // Restituisce un singolo SottomissioneDTO
            Sottomissione sottomissione = sottomissioneHandler.visualizzaSottomissione(username);
            SottomissioneDTO sottomissioneDTO = new SottomissioneDTO(
                    sottomissione.getSottomissioneID(),
                    sottomissione.getNome(),
                    (sottomissione.getValutazione() != null) ? String.valueOf(sottomissione.getValutazione().getVoto()) : "Non ancora valutata"
            );
            return new ResponseEntity<>(sottomissioneDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('GIUDICE')")
    @GetMapping("/giudice/visualizza")
    public ResponseEntity<Object> visualizzaSottomissioni(Authentication authentication,
                                                          @RequestParam String nomeHackathon) {
        String username = authentication.getName();
        try {
            List<SottomissioneDTO> SottomissioneDTOS = sottomissioneHandler.visualizzaSottomissioni(username, nomeHackathon);
            return new ResponseEntity<>(SottomissioneDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('GIUDICE')")
    @PostMapping("/valuta")
    public ResponseEntity<Object> valutaSottomissione(Authentication authentication,
                                                      @RequestParam String nomeTeam,
                                                      @RequestParam int voto,
                                                      @RequestParam String descrizione) {
        String username= authentication.getName();
        try{
            sottomissioneHandler.valutaSottomissione(username, nomeTeam, voto, descrizione);
            return new ResponseEntity<>("Sottomissione valutata con successo",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('UTENTE', 'GIUDICE')")
    @GetMapping("/scarica")
    public ResponseEntity<Resource> scaricaSottomissione(
            Authentication authentication,
            @RequestParam Long sottomissioneId) {
        String username = authentication.getName();
        boolean isGiudice = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("GIUDICE"));
        try {
            String filePath = sottomissioneHandler.scaricaSottomissione(
                    username, sottomissioneId, isGiudice);
            File file = new File(filePath);
            if (!file.exists()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Resource resource = new FileSystemResource(file);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + file.getName() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
