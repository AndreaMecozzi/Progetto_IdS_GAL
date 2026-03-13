package unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon;

/**
 * Rappresenta lo stato "Concluso" dell'hackathon. La durata di questo stato è permanente
 * ed all'interno di esso non è possibile svolgere alcuna operazione
 */
public class Concluso implements StatoHackathon{

    private Hackathon hackathon;

    @Override
    public void cambiaStato(){
        /// Un hackathon concluso non cambia stato
    }

    @Override
    public String getNomeStato(){
        return "CONCLUSO";
    }

}
