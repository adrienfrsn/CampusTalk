package modele;

import java.time.LocalDate;

public class Abonnement {
    private String utilisateurEmail;
    private int idFil;
    private LocalDate dateAbonnement;

    public Abonnement() {
    }

    public Abonnement(String utilisateurEmail, int idFil, LocalDate dateAbonnement) {
        this.utilisateurEmail = utilisateurEmail;
        this.idFil = idFil;
        this.dateAbonnement = dateAbonnement;
    }

    public String getUtilisateurEmail() {
        return utilisateurEmail;
    }

    public void setUtilisateurEmail(String utilisateurEmail) {
        this.utilisateurEmail = utilisateurEmail;
    }

    public int getIdFil() {
        return idFil;
    }

    public void setIdFil(int idFil) {
        this.idFil = idFil;
    }

    public LocalDate getDateAbonnement() {
        return dateAbonnement;
    }

    public void setDateAbonnement(LocalDate dateAbonnement) {
        this.dateAbonnement = dateAbonnement;
    }

    @Override
    public String toString() {
        return "Abonnement{" +
                "utilisateurEmail=" + utilisateurEmail +
                ", idFil=" + idFil +
                ", dateAbonnement=" + dateAbonnement +
                '}';
    }
}
