package modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FilDeDiscussionDAO {

    public FilDeDiscussion findById(int id) {
        FilDeDiscussion f = new FilDeDiscussion();
        try (Connection con = DS.instance.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM filDeDiscussion WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                f.setId(rs.getInt("id"));
                f.setNom(rs.getString("nom"));
                f.setDateCreation(rs.getTimestamp("datecreation").toLocalDateTime());
                f.setCreateurEmail(rs.getString("createuremail"));
                f.setDescription(rs.getString("description"));
                f.setLogo(rs.getString("logo"));
            } else {
                System.out.println("Fil de discussion inexistant");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return f;
    }

    public void create(FilDeDiscussion filDeDiscussion) {
        try (Connection con = DS.instance.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO filDeDiscussion (nom, createuremail, description, logo) VALUES (?, ?, ?, ?)");
            pstmt.setString(1, filDeDiscussion.getNom());
            pstmt.setString(2, filDeDiscussion.getCreateurEmail());
            pstmt.setString(3, filDeDiscussion.getDescription());
            pstmt.setString(4, filDeDiscussion.getLogo());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());    
        }
    }

    public List<FilDeDiscussion> findAll() {
        List<FilDeDiscussion> filsDeDiscussion = new ArrayList<>();
        try (Connection con = DS.instance.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM filDeDiscussion");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FilDeDiscussion f = new FilDeDiscussion();
                f.setId(rs.getInt("id"));
                f.setNom(rs.getString("nom"));
                f.setDateCreation(rs.getTimestamp("datecreation").toLocalDateTime());
                f.setCreateurEmail(rs.getString("createuremail"));
                f.setDescription(rs.getString("description"));
                f.setLogo(rs.getString("logo"));
                filsDeDiscussion.add(f);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return filsDeDiscussion;
    }

    public boolean delete(int id) {
        try (Connection con = DS.instance.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM filDeDiscussion WHERE id = ?");
            pstmt.setInt(1, id);
            int modif =  pstmt.executeUpdate();
            return modif > 0;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public void update(FilDeDiscussion filDeDiscussion) {
        try (Connection con = DS.instance.getConnection()) {
            PreparedStatement pstmt = con
                    .prepareStatement("UPDATE filDeDiscussion SET nom = ?, description = ?, logo = ? WHERE id = ?");
            pstmt.setString(1, filDeDiscussion.getNom());
            pstmt.setString(2, filDeDiscussion.getDescription());
            pstmt.setString(3, filDeDiscussion.getLogo());
            pstmt.setInt(4, filDeDiscussion.getId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
