package unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon;

import java.time.LocalDateTime;

/**
 * Rappresenta lo stato "In corso" dell'hackathon. La sua durata è di 30 giorni
 * e durante esso i team possono creare ed aggiornare le proprie sottomissioni
 */
public class InCorso implements StatoHackathon {

    @Override
    public void cambiaStato(Hackathon hackathon){
        /// Durata effettiva 30 giorni, ma commentato per testare
        /**if(LocalDateTime.now().isAfter(hackathon.getDataInizioStato().plusDays(30))){
            StatoHackathon inValutazione=new InValutazione();
            hackathon.setStato(inValutazione);
            hackathon.setDataInizioStato(LocalDateTime.now());
        }*/
        if(LocalDateTime.now().isAfter(hackathon.getDataInizioStato().plusMinutes(1))){
            StatoHackathon inValutazione=new InValutazione();
            hackathon.setStato(inValutazione);
            hackathon.setDataInizioStato(LocalDateTime.now());
        }
    }

    @Override
    public String getNomeStato(){
        return "IN_CORSO";
    }

}
