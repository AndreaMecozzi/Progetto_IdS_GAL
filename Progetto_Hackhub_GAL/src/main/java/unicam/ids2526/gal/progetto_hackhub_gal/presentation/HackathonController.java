package unicam.ids2526.gal.progetto_hackhub_gal.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unicam.ids2526.gal.progetto_hackhub_gal.application.dto.CreaHackathonDTO;
import unicam.ids2526.gal.progetto_hackhub_gal.application.handlers.HackathonHandler;

import java.util.List;

@RestController
@RequestMapping("/hackathon")
public class HackathonController {
    private final HackathonHandler hackathonHandler;

    public HackathonController(HackathonHandler hackathonHandler) {
        this.hackathonHandler = hackathonHandler;
    }

    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @PostMapping(value = "/creaHackathon",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
}
