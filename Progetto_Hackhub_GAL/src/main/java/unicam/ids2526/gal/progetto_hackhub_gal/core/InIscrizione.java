package unicam.ids2526.gal.progetto_hackhub_gal.core;

import java.time.LocalDateTime;

/**
 * Rappresenta lo stato "In valutazione" dell'hackathon. La sua durata è di 7 giorni
 * e durante questo periodo è possibile solamente iscriversi
 */
public class InIscrizione implements StatoHackathon{

    private Hackathon hackathon;

    @Override
    public void cambiaStato(){
        if(LocalDateTime.now().isAfter(LocalDateTime.now().plusDays(7))){
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
