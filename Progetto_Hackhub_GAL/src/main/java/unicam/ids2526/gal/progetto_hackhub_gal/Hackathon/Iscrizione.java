package unicam.ids2526.gal.progetto_hackhub_gal.Hackathon;

import unicam.ids2526.gal.progetto_hackhub_gal.Sottomissione;
import unicam.ids2526.gal.progetto_hackhub_gal.Team;
import unicam.ids2526.gal.progetto_hackhub_gal.Valutazione;

/**
 * Rappresenta lo stato "In iscrizione" dell'Hackathon. Durante questa fase è possibile solamente far iscrivere team
 */
public class Iscrizione implements HackathonState{
    private Hackathon hackathon;

    public Iscrizione(Hackathon hackathon){
        this.hackathon = hackathon;
    }
    @Override
    public boolean iscriviTeam(Team team) {
        return this.hackathon.iscriviTeam(team);
    }

    @Override
    public boolean caricaSottomissione(Sottomissione sottomissione) {
        return false;
    }

    @Override
    public boolean modificaSottomissione(Sottomissione sottomissione) {
        return false;
    }

    @Override
    public boolean valutaSottomissione(Sottomissione sottomissione, Valutazione valutazione) {
        return false;
    }
}
