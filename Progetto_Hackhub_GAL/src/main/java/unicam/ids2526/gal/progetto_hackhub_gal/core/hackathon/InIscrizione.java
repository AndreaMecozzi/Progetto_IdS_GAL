package unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Rappresenta lo stato "In valutazione" dell'hackathon. La sua durata è di 7 giorni
 * e durante questo periodo è possibile solamente iscriversi
 */
public class InIscrizione implements StatoHackathon{
    // Costante con i giorni convertiti esattamente in secondi
    // private static final long SECONDI_IN_7_GIORNI = 604800L;

    // passaggio da "IN_ISCRIZIONE" a "IN_CORSO" -> 3 minuti
    private static final long SECONDI_IN_7_GIORNI = 120;

    @Override
    public void cambiaStato(Hackathon hackathon){
        long secondiTrascorsi = ChronoUnit.SECONDS.between(hackathon.getDataInizioStato(), LocalDateTime.now());

        // controllo se è passato il tempo per cambiare stato
        if (secondiTrascorsi >= SECONDI_IN_7_GIORNI) {
            StatoHackathon inCorso = new InCorso();
            hackathon.setStato(inCorso);
            // reset del timer per il nuovo stato
            hackathon.setDataInizioStato(LocalDateTime.now());
        }
    }

    @Override
    public String getNomeStato(){
        return "IN_ISCRIZIONE";
    }

}
