package unicam.ids2526.gal.progetto_hackhub_gal.core.utenti;

public interface BuilderUtente {
    public void setEmail(String email);
    public void setUsername(String username);
    public void setPassword(String password);
    public void setRuolo(String ruolo);
    public Utente getUtenteFinale();
    public void resetUtente();
}
