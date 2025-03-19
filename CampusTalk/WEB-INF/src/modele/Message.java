package modele;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Message {
    private int id;
    private String contenu;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime datePublication;
    private int filId;
    private String auteurEmail;
    private String fileName;
    private boolean likeCount;

    public Message() {
    }

    public Message(int id, String contenu, LocalDateTime datePublication, int filId, String auteurEmail,
            String fileName) {
        this.id = id;
        this.contenu = contenu;
        this.datePublication = datePublication;
        this.filId = filId;
        this.auteurEmail = auteurEmail;
        this.fileName = fileName;
        this.likeCount = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDateTime getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(LocalDateTime datePublication) {
        this.datePublication = datePublication;
    }

    public int getfilId() {
        return filId;
    }

    public void setFilId(int filId) {
        this.filId = filId;
    }

    public String getAuteurEmail() {
        return auteurEmail;
    }

    public void setAuteurEmail(String auteurEmail) {
        this.auteurEmail = auteurEmail;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(boolean likeCount) {
        this.likeCount = likeCount;
    }

    public String afficherLike() {
        if (likeCount == true) {
            return "Vous avez aimé ce message";
        }
        return "Vous n'avez pas aimé ce message";
    }

    @Override
    public String toString() {
        return "Message{" + "id=" + id + ", contenu=" + contenu + ", datePublication=" + datePublication + ", filId="
                + filId + ", auteurEmail=" + auteurEmail + "like" + afficherLike() +'}';
    }
}
