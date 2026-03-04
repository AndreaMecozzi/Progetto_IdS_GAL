package unicam.ids2526.gal.progetto_hackhub_gal.profili;

public interface AccountCreator {
    // Metodo factory che tutte le classi "Creator" dovranno implementare.

    // restituisce un oggetto generico di tipo Account
    Account createAccount(String email, String username, String password);
}
