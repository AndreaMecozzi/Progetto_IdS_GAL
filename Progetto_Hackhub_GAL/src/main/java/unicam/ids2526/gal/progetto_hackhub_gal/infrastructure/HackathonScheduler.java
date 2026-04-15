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

    /*
    // Costanti con i giorni convertiti esattamente in secondi
    private static final long SECONDI_IN_7_GIORNI = 604800L;
    private static final long SECONDI_IN_30_GIORNI = 2592000L;

     */

    // Costanti con i giorni convertiti in secondi (semplificati per i test)
    // passaggio da "IN_ISCRIZIONE" a "IN_CORSO" -> 3 minuti
    private static final long SECONDI_IN_7_GIORNI = 120;
    // passaggio da "IN_CORSO" a "IN_VALUTAZIONE" -> 5 minuti
    private static final long SECONDI_IN_30_GIORNI = 120;

    //@Scheduled(cron = "* * * * * *") // --> scatta ogni secondo per una precisione maggiore
    @Scheduled(cron = "0 * * * * *") //--> Scatta ogni minuto, per il testing
    @Transactional
    public void controllaScadenze(){
        List<Hackathon> attivi = hackathonRep.findAllByStatoNot("CONCLUSO");

        for (Hackathon h : attivi) {
            String statoAttuale = h.getStato();
            boolean prontoPerAvanzare = false;

            // calcolo dei secondi reali trascorsi dall'inizio dello stato attuale
            long secondiTrascorsi = ChronoUnit.SECONDS.between(h.getDataInizioStato(), LocalDateTime.now());

            switch (statoAttuale) {
                case "IN_ISCRIZIONE":
                    if (secondiTrascorsi >= SECONDI_IN_7_GIORNI) {
                        prontoPerAvanzare = true;
                    }
                    break;

                case "IN_CORSO":
                    if (secondiTrascorsi >= SECONDI_IN_30_GIORNI) {
                        prontoPerAvanzare = true;
                    }
                    break;

                case "IN_VALUTAZIONE":
                    // Il tempo non conta, si aspetta solo il Giudice
                    System.out.println(h.sottomissioniValutate());
                    if (h.sottomissioniValutate()) {
                        prontoPerAvanzare = true;
                    }
                    break;
            }

            if (prontoPerAvanzare) {
                h.cambiaStato();
                h.setDataInizioStato(LocalDateTime.now()); // Resetta il timer
                hackathonRep.save(h);

                System.out.println("L'Hackathon '" + h.getNome() + "' è avanzato allo stato: " + h.getStato());
            }
        }
    }
}
