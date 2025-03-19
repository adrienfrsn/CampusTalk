package modele;

import java.time.LocalDateTime;

public class Utilisateur {
    private String nom;
    private String email;
    private String motDePasse; // HASH
    private LocalDateTime dateInscription;
    String token;

    public Utilisateur() {
    }

    public Utilisateur(String nom, String email, String motDePasse, LocalDateTime dateInscription, String token) {
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.dateInscription = dateInscription;
        this.token = token;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public LocalDateTime getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDateTime dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Utilisateur [dateInscription=" + dateInscription + ", email=" + email + ", nom=" + nom + "]";
    }
}
