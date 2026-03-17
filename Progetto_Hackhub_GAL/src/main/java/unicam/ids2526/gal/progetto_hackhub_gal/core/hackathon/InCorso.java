package unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon;

import java.time.LocalDateTime;

/**
 * Rappresenta lo stato "In corso" dell'hackathon. La sua durata è di 30 giorni
 * e durante esso i team possono creare ed aggiornare le proprie sottomissioni
 */
public class InCorso implements StatoHackathon {

    private Hackathon hackathon;

    @Override
    public void cambiaStato(){
        if(LocalDateTime.now().isAfter(hackathon.getDataInizioStato().plusDays(30))){
            StatoHackathon inCorso=new InCorso();
            hackathon.setStato(inCorso);
            hackathon.setDataInizioStato(LocalDateTime.now());
        }
    }

    @Override
    public String getNomeStato(){
        return "IN_CORSO";
    }

}
