package unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Rappresenta lo stato "In corso" dell'hackathon. La sua durata è di 30 giorni
 * e durante esso i team possono creare ed aggiornare le proprie sottomissioni
 */
public class InCorso implements StatoHackathon {

    // Costante con i giorni convertiti esattamente in secondi
    // private static final long SECONDI_IN_7_GIORNI = 2592000L;

    // passaggio da "IN_CORSO" a "IN_VALUTAZIONE" -> 5 minuti
    private static final long SECONDI_IN_30_GIORNI = 120;

    @Override
    public void cambiaStato(Hackathon hackathon){
        long secondiTrascorsi = ChronoUnit.SECONDS.between(hackathon.getDataInizioStato(), LocalDateTime.now());

        // controllo se è passato il tempo per cambiare stato
        if (secondiTrascorsi >= SECONDI_IN_30_GIORNI) {
            StatoHackathon inValutazione = new InValutazione();
            hackathon.setStato(inValutazione);
            // reset del timer per il nuovo stato
            hackathon.setDataInizioStato(LocalDateTime.now());
        }
    }

    @Override
    public String getNomeStato(){
        return "IN_CORSO";
    }

}
