package unicam.ids2526.gal.progetto_hackhub_gal.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.Hackathon;

import java.util.List;

@Component
public class HackathonScheduler {

    @Autowired
    private HackathonRepository hackathonRep;

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void controllaScadenze() throws Exception {
        List<Hackathon> attivi = hackathonRep.findAllByStatoNot("CONCLUSO");
        if(attivi.isEmpty()){
            throw new Exception("Errore: non ci sono hackathon");
        }

        for (Hackathon h : attivi) {
            h.cambiaStato();
            hackathonRep.save(h);
        }
    }
}
