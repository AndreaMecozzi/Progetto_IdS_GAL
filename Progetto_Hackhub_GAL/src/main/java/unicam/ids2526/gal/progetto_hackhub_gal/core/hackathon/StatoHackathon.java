package unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon;

/**
 * Rappresenta uno "stato" dell'hackathon, ossia un periodo in cui è possibile
 * svolgere determinate operazioni all'interno dell'hackathon. Contiene i metodi
 * per il cambio dello stato e per il salvataggio dello stato all'interno del database
 */
public interface StatoHackathon {

    /**
     * Cambia lo stato dell'hackathon se il periodo è terminato
     */
    public void cambiaStato();

    /**
     * Serve a salvare lo stato all'interno del Database nella tabella
     * degli hackathon.
     *
     * @return il nome dello stato
     */
    public String getNomeStato();
}
