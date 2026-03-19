package unicam.ids2526.gal.progetto_hackhub_gal.presentation;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unicam.ids2526.gal.progetto_hackhub_gal.application.dto.CreaHackathonDTO;
import unicam.ids2526.gal.progetto_hackhub_gal.application.handlers.HackathonHandler;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@RestController
@RequestMapping("/hackathon")
public class HackathonController {
    private final HackathonHandler hackathonHandler;

    public HackathonController(HackathonHandler hackathonHandler) {
        this.hackathonHandler = hackathonHandler;
    }

    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @PostMapping(value = "/crea",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> creaHackathon(Authentication authentication,
                                                @ModelAttribute CreaHackathonDTO creaDTO){
        String nomeHackhathon = creaDTO.getNomeHackathon();
        Double premio = creaDTO.getPremio();
        Integer dimensioneTeam= creaDTO.getDimensioneTeam();
        MultipartFile regolamento= creaDTO.getRegolamento();
        String userOrg=authentication.getName();
        String userGiudice=creaDTO.getGiudice();
        List<String> usersMentori=creaDTO.getMentori();

        try{
            hackathonHandler.creaHackathon(nomeHackhathon, premio, dimensioneTeam, regolamento,
                    userOrg, userGiudice, usersMentori);
            return new ResponseEntity<>("Hackathon creato con successo", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @PostMapping("/iscrivi")
    public ResponseEntity<Object> iscriviTeam(Authentication authentication, @RequestBody String nomeHackathon){
        String username=authentication.getName();
        try{
            hackathonHandler.iscriviTeam(username, nomeHackathon);
            return new ResponseEntity<>("Team iscritto con successo",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/visualizzaRegolamento")
    public ResponseEntity<Object> visualizzaRegolamento(@RequestBody String nomeHackathon){
        try{
            File regolamento=hackathonHandler.visualizzaRegolamento(nomeHackathon);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(regolamento));

            HttpHeaders header = new HttpHeaders();
            header.add("Content-Disposition", String.format("attachment; fileName=\"%s\"",
                    regolamento.getName()));

            ResponseEntity<Object> response = ResponseEntity.ok().headers(header)
                    .contentLength(regolamento.length())
                    .contentType(MediaType.parseMediaType("application/txt")).body(resource);
            return response;
            //return new ResponseEntity<>(hackathonHandler.visualizzaRegolamento(nomeHackathon),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/elenco")
    public ResponseEntity<Object> elencoHackathon(){
        try{
            return new ResponseEntity<>(hackathonHandler.elencoHackathon(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @PostMapping("/rimuovi")
    public ResponseEntity<Object> rimuoviHackathon(Authentication authentication,@RequestBody String nomeHackathon){
        String username=authentication.getName();
        try{
            hackathonHandler.rimuoviHackathon(username, nomeHackathon);
            return new ResponseEntity<>("Hackathon rimosso con successo",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @PostMapping(value = "/aggiornaRegolamento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> aggiornaRegolamento(Authentication authentication,
                                                      @RequestParam  String nomeHackathon,
                                                      @RequestParam MultipartFile file){
        String username=authentication.getName();
        try{
            hackathonHandler.aggiornaRegolamento(username, nomeHackathon, file);
            return new ResponseEntity<>("regolamento aggiornato con successo",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @PostMapping("/disiscrivi")
    public ResponseEntity<Object> disiscriviTeam(Authentication authentication){
        String username=authentication.getName();
        try{
            hackathonHandler.disiscriviTeam(username);
            return new ResponseEntity<>("Team disiscritto dall'hackathon",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
