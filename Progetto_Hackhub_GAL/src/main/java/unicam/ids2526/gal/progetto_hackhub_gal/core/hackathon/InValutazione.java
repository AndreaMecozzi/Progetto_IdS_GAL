package unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon;

import java.time.LocalDateTime;

/**
 * Rappresenta lo stato "In valutazione" dell'hackathon. La sua durata è di 14 giorni
 * e durante questo periodo il giudice valuta le sottomissioni caricate
 */
public class InValutazione implements StatoHackathon{

    private Hackathon hackathon;
    @Override
    public void cambiaStato(){
        if(LocalDateTime.now().isAfter(hackathon.getDataInizioStato().plusDays(14))){
            StatoHackathon inCorso=new InCorso();
            hackathon.setStato(inCorso);
            hackathon.setDataInizioStato(LocalDateTime.now());
        }
    }

    @Override
    public String getNomeStato(){
        return "IN_VALUTAZIONE";
    }

}
