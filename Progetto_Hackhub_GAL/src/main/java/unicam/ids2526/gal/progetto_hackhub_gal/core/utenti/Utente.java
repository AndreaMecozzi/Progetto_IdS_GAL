package unicam.ids2526.gal.progetto_hackhub_gal.core.utenti;
import jakarta.persistence.*;

/**
 * Rappresenta un utente generico sulla piattaforma Hackub. Un utente è caratterizzato dalle credenziali
 * del proprio account e da un RUOLO, in modo da poter svolgere delle specifiche azioni
 */

@Entity
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Ruolo ruolo;

    public Utente() {
        this.username = null;
        this.email = null;
        this.password = null;
        this.ruolo = null;
    }

    public Utente(String username, String email, String password, Ruolo ruolo) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }
}
