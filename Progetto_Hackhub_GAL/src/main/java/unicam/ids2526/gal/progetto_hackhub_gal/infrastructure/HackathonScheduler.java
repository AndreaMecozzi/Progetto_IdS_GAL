package unicam.ids2526.gal.progetto_hackhub_gal.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.Hackathon;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class HackathonScheduler {

    private HackathonRepository hackathonRep;

    public HackathonScheduler(HackathonRepository hackathonRep) {
        this.hackathonRep = hackathonRep;
    }


    //@Scheduled(cron = "* * * * * *") // --> scatta ogni secondo per una precisione maggiore
    @Scheduled(cron = "0 * * * * *") //--> Scatta ogni minuto, per il testing
    @Transactional
    public void controllaScadenze(){
        List<Hackathon> attivi = hackathonRep.findAllByStatoNot("CONCLUSO");

        for (Hackathon h : attivi) {
            // recupero dello stato
            String statoPrecedente = h.getStato();

            // cambiamento dello stato dell'hackathon
            h.cambiaStato();

            // salvataggio dello stato solo in caso di effettivo cambiamento
            if (!statoPrecedente.equals(h.getStato())) {
                hackathonRep.save(h);
            }
        }
    }
}
