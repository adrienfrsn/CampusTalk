package modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class AbonnementDao {
    
    public Abonnement findAbonnement(String utilisateurEmail, int idFil) {
        Abonnement abonnement = new Abonnement();
        try(Connection con = DS.instance.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM abonnement WHERE utilisateuremail = ? AND filid = ?");
            ps.setString(1, utilisateurEmail);
            ps.setInt(2, idFil);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                abonnement.setUtilisateurEmail(rs.getString("utilisateuremail"));
                abonnement.setIdFil(rs.getInt("filid"));
                abonnement.setDateAbonnement(rs.getDate("dateabonnement").toLocalDate());
            } else {
                System.out.println("Abonnement inexistant");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return abonnement;
    }

    public List<Abonnement> findAll() {
        List<Abonnement> abonnements = new ArrayList<>();
        try(Connection con = DS.instance.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM abonnement");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Abonnement abonnement = new Abonnement();
                abonnement.setUtilisateurEmail(rs.getString("utilisateuremail"));
                abonnement.setIdFil(rs.getInt("filid"));
                abonnement.setDateAbonnement(rs.getDate("dateabonnement").toLocalDate());
                abonnements.add(abonnement);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return abonnements;
    }

    public List<Abonnement> findAbonnements(String utilisateurEmail) {
        List<Abonnement> abonnements = new ArrayList<>();
        try(Connection con = DS.instance.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM abonnement WHERE utilisateuremail = ?");
            ps.setString(1, utilisateurEmail);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Abonnement abonnement = new Abonnement();
                abonnement.setUtilisateurEmail(rs.getString("utilisateuremail"));
                abonnement.setIdFil(rs.getInt("filid"));
                abonnement.setDateAbonnement(rs.getDate("dateabonnement").toLocalDate());
                abonnements.add(abonnement);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return abonnements;
    }

    public boolean isAbonne(String utilisateurEmail, int idFil) {
        try(Connection con = DS.instance.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM abonnement WHERE utilisateuremail = ? AND filid = ?");
            ps.setString(1, utilisateurEmail);
            ps.setInt(2, idFil);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void abonner(String utilisateurEmail, int idFil) {
        try(Connection con = DS.instance.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO abonnement (utilisateuremail, filid) VALUES (?, ?)");
            pstmt.setString(1, utilisateurEmail);
            pstmt.setInt(2, idFil);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void desabonner(String utilisateurEmail, int idFil) {
        try (Connection con = DS.instance.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM abonnement WHERE utilisateuremail = ? AND filid = ?");
            pstmt.setString(1, utilisateurEmail);
            pstmt.setInt(2, idFil);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
