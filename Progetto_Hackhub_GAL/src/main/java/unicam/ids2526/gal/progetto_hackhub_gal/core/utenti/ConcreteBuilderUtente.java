package unicam.ids2526.gal.progetto_hackhub_gal.core.utenti;

public class ConcreteBuilderUtente implements BuilderUtente{
    private Utente utente;

    public ConcreteBuilderUtente() {
        this.utente = new Utente();
    }

    @Override
    public void setEmail(String email) {
        utente.setEmail(email);
    }

    @Override
    public void setUsername(String username) {
        utente.setUsername(username);
    }

    @Override
    public void setPassword(String password) {
        utente.setPassword(password);
    }

    @Override
    public void setRuolo(String ruolo) {
        ruolo = ruolo.toUpperCase();
        switch (ruolo) {
            case "UTENTE"->utente.setRuolo(Ruolo.UTENTE);
            case "GIUDICE"->utente.setRuolo(Ruolo.GIUDICE);
            case "ORGANIZZATORE"->utente.setRuolo(Ruolo.ORGANIZZATORE);
            case "MENTORE"->utente.setRuolo(Ruolo.MENTORE);
        }
    }

    @Override
    public Utente getUtenteFinale() {
        return this.utente;
    }

    @Override
    public void resetUtente() {
        this.utente = new Utente();
    }
}
