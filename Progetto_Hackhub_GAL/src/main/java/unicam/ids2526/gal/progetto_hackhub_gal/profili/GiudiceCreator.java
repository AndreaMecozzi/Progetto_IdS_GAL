package unicam.ids2526.gal.progetto_hackhub_gal.profili;

public class GiudiceCreator implements AccountCreator {
    @Override
    public Account createAccount(String email, String username, String password) {
        return new Giudice(email,username,password); // Organizzatore è un MembroDelloStaff, che è un ConcreteAccount, che è un Account!
    }
}
