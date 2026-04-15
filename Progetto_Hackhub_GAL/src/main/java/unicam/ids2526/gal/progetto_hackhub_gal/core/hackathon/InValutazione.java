package unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon;

import java.time.LocalDateTime;

/**
 * Rappresenta lo stato "In valutazione" dell'hackathon. La sua durata è di 14 giorni
 * e durante questo periodo il giudice valuta le sottomissioni caricate
 */
public class InValutazione implements StatoHackathon{

    @Override
    public void cambiaStato(Hackathon hackathon){
        /// Durata effettiva 14 giorni, ma commentato per testare
        /**if(LocalDateTime.now().isAfter(hackathon.getDataInizioStato().plusDays(14))){
            StatoHackathon concluso=new Concluso();
            hackathon.setStato(concluso);
            hackathon.setDataInizioStato(LocalDateTime.now());
        }*/

            StatoHackathon concluso=new Concluso();
            hackathon.setStato(concluso);


    }

    @Override
    public String getNomeStato(){
        return "IN_VALUTAZIONE";
    }

}
