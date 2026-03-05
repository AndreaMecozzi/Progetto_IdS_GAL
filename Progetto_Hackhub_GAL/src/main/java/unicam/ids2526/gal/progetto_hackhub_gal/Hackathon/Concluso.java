package unicam.ids2526.gal.progetto_hackhub_gal.Hackathon;

import unicam.ids2526.gal.progetto_hackhub_gal.Sottomissione;
import unicam.ids2526.gal.progetto_hackhub_gal.Team;
import unicam.ids2526.gal.progetto_hackhub_gal.Valutazione;

public class Concluso implements HackathonState{
    @Override
    public boolean iscriviTeam(Team team) {
        return false;
    }

    @Override
    public boolean caricaSottomissione(Sottomissione sottomissione) {
        // TODO implementare
        return false;
    }

    @Override
    public boolean modificaSottomissione(Sottomissione sottomissione) {
        // TODO implementare
        return false;
    }

    @Override
    public boolean valutaSottomissione(Sottomissione sottomissione, Valutazione valutazione) {
        // TODO implementare
        return false;
    }
}
