package unicam.ids2526.gal.progetto_hackhub_gal.Hackathon;

import unicam.ids2526.gal.progetto_hackhub_gal.Sottomissione;
import unicam.ids2526.gal.progetto_hackhub_gal.Team;
import unicam.ids2526.gal.progetto_hackhub_gal.Valutazione;

public interface HackathonState {
    /**
     * Metodo per iscrivere un determinato team all'Hackathon
     *
     * @param team il team che deve essere iscritto
     * @return true se il team è stato iscritto, false altrimenti
     */
    public boolean iscriviTeam(Team team);
    /**
     * Metodo per caricare una sottomissione all'interno dell'Hackathon
     *
     * @param sottomissione la sottomissione da caricare
     * @return true se la sottomissione è stata caricata, false altrimenti
     */
    public boolean caricaSottomissione(Sottomissione sottomissione);
    /**
     * Metodo per modificare una sottomissione
     *
     * @param sottomissione la sottomissione da caricare
     * @return true se la sottomissione è stata modificata, false altrimenti
     */
    public boolean modificaSottomissione(Sottomissione sottomissione);

    /**
     * Metodo per valutare una sottomissione
     *
     * @param sottomissione la sottomissione da valutare
     * @return true se la sottomissione è stata caricata, false altrimenti
     */
    public boolean valutaSottomissione(Sottomissione sottomissione, Valutazione valutazione);
}
