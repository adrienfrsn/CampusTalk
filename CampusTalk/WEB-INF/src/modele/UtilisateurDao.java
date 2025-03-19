package modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UtilisateurDao {
    
    public Utilisateur findUtilisateur(String email) {
        Utilisateur utilisateur = new Utilisateur();
        try(Connection con = DS.instance.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM utilisateur WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                utilisateur.setNom(rs.getString("nom"));
                utilisateur.setEmail(rs.getString("email"));
                utilisateur.setMotDePasse(rs.getString("motdepasse"));
                utilisateur.setDateInscription(rs.getTimestamp("dateinscription").toLocalDateTime());
                utilisateur.setToken(rs.getString("token"));
            } else {
                System.out.println("Utilisateur inexistant");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return utilisateur;
    }

    public boolean emailExists(String email) {
        try(Connection con = DS.instance.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Utilisateur WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void delete(String email) {
        try(Connection con = DS.instance.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM Utilisateur WHERE email = ?");
            ps.setString(1, email);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Utilisateur utilisateur, String oldEmail) {
        try(Connection con = DS.instance.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE Utilisateur SET nom = ?, email = ?, motdepasse = MD5(?), token = ? WHERE email = ?");
            String newToken = GenerateToken.generateToken(utilisateur.getEmail(), utilisateur.getMotDePasse());
            utilisateur.setToken(newToken);
            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getEmail());
            ps.setString(3, utilisateur.getMotDePasse());
            ps.setString(4, utilisateur.getToken());
            ps.setString(5, oldEmail);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(Utilisateur utilisateur) {
        try(Connection con = DS.instance.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO Utilisateur (nom, email, motdepasse, token) VALUES (?, ?, MD5(?), ?)");
            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getEmail());
            ps.setString(3, utilisateur.getMotDePasse());
            ps.setString(4, utilisateur.getToken());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean check(String email, String motDePasse) {
        try(Connection con = DS.instance.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Utilisateur WHERE email = ? AND motdepasse = MD5(?)");
            ps.setString(1, email);
            ps.setString(2, motDePasse);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean check(String token) {
        try(Connection con = DS.instance.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Utilisateur WHERE token = ?");
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public String findEmailByToken(String token) {
        try(Connection con = DS.instance.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT email FROM Utilisateur WHERE token = ?");
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("email");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
