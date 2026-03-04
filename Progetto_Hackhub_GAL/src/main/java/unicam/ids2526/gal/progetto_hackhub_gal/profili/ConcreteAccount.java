package unicam.ids2526.gal.progetto_hackhub_gal.profili;

public class ConcreteAccount implements Account{

    private long accountID;
    private String email;
    private String username;
    private String password;

    public ConcreteAccount (String email, String username, String password){
        // TODO
        // this.accountID= pesca dal DB primo ID disponibile;
        this.email= email;
        this.username= username;
        this.password= password;
    }
    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void login() {
        //TODO IMPLEMENTARE
    }

    @Override
    public void logout() {
        //TODO IMPLEMENTARE
    }
}
