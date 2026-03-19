package unicam.ids2526.gal.progetto_hackhub_gal.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.Hackathon;

import java.util.List;

@Component
public class HackathonScheduler {

    private HackathonRepository hackathonRep;

    public HackathonScheduler(HackathonRepository hackathonRep) {
        this.hackathonRep = hackathonRep;
    }

    @Scheduled(cron = "0 0 * * * *") //--> Scatta ad ogni ora
    /// @Scheduled(cron = "0 * * * * *") //--> Scatta ogni minuto, per il testing
    @Transactional
    public void controllaScadenze(){
        List<Hackathon> attivi = hackathonRep.findAllByStatoNot("CONCLUSO");
        if(!attivi.isEmpty()){
            for (Hackathon h : attivi) {
                h.cambiaStato();
                hackathonRep.save(h);
            }
        }
    }
}
