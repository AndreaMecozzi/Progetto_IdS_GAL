package unicam.ids2526.gal.progetto_hackhub_gal.profili;

import unicam.ids2526.gal.progetto_hackhub_gal.Invito;
import unicam.ids2526.gal.progetto_hackhub_gal.Team;

import java.util.List;

public class Utente extends ConcreteAccount{

    private Team team;
    private List<Invito> inviti;

    public Utente(String email, String username, String password){
        super(email,username,password);
        this.team = null;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void addInvito (Invito invito){
        inviti.add(invito);
    }

    public void showInviti(){
        //mostrare gli inviti
    }

    public void invita(Utente destinatario, Invito invito){
        //TODO implementare
    }






}
