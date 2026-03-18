package unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon;

import java.time.LocalDateTime;

/**
 * Rappresenta lo stato "In valutazione" dell'hackathon. La sua durata è di 7 giorni
 * e durante questo periodo è possibile solamente iscriversi
 */
public class InIscrizione implements StatoHackathon{

    @Override
    public void cambiaStato(Hackathon hackathon){
        /// Durata effettiva 7 giorni, ma commentato per testare
        /**if(LocalDateTime.now().isAfter(hackathon.getDataInizioStato().plusDays(7))){
            StatoHackathon inCorso=new InCorso();
            hackathon.setStato(inCorso);
            hackathon.setDataInizioStato(LocalDateTime.now());
        }*/
        if(LocalDateTime.now().isAfter(hackathon.getDataInizioStato().plusMinutes(1))){
            StatoHackathon inCorso=new InCorso();
            hackathon.setStato(inCorso);
            hackathon.setDataInizioStato(LocalDateTime.now());
        }
    }

    @Override
    public String getNomeStato(){
        return "IN_ISCRIZIONE";
    }

}
