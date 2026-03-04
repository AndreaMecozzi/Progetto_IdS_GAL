package unicam.ids2526.gal.progetto_hackhub_gal.profili;

import unicam.ids2526.gal.progetto_hackhub_gal.Hackathon.Hackathon;

import java.util.ArrayList;
import java.util.Iterator;

public class Organizzatore extends MembroDelloStaff{
    private ArrayList<Hackathon> hackathons;
    public Organizzatore(String email, String username, String password){
        super(email,username,password);
    }

    public void creaHackathon(long hackathonID, String nome, String descrizione, Giudice giudice, Mentore mentore){
        hackathons.add(new Hackathon(hackathonID, nome, descrizione, giudice, mentore));
    }

    public void removeHackathon(Hackathon h) {
        Iterator<Hackathon> it = hackathons.iterator();
        while (it.hasNext()) {
            if (it.next().getHackathonID() == h.getHackathonID()) {
                it.remove();
                break;
            }
        }
    }

    public void addMentore (Hackathon hackathon, Mentore mentore){
        hackathon.addMentore(mentore);
    }

    public void proclamaVincitore(){
        //TODO
    }
}
