package unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon;

import java.time.LocalDateTime;

/**
 * Rappresenta lo stato "In valutazione" dell'hackathon. La sua durata è di 14 giorni
 * e durante questo periodo il giudice valuta le sottomissioni caricate
 */
public class InValutazione implements StatoHackathon{

    @Override
    public void cambiaStato(Hackathon hackathon){
        // controllo se tutte le sottomissioni presentate per questo hackathon sono state valutate
        if (hackathon.sottomissioniValutate()) {
            StatoHackathon concluso = new Concluso();
            hackathon.setStato(concluso);
            // impostato il momento esatto della fine
            hackathon.setDataInizioStato(LocalDateTime.now());
        }
    }

    @Override
    public String getNomeStato(){
        return "IN_VALUTAZIONE";
    }

}
