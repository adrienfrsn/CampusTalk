package modele;

import java.time.LocalDateTime;

public class FilDeDiscussion {
    private int id;
    private String nom;
    private LocalDateTime dateCreation;
    private String createurEmail;
    private String description;
    private String logo;

    public FilDeDiscussion() {
    }

    public FilDeDiscussion(int id, String nom, LocalDateTime dateCreation, String createurEmail, String description, String logo) {
        this.id = id;
        this.nom = nom;
        this.dateCreation = dateCreation;
        this.createurEmail = createurEmail;
        this.description = description;
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getCreateurEmail() {
        return createurEmail;
    }

    public void setCreateurEmail(String createurEmail) {
        this.createurEmail = createurEmail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
