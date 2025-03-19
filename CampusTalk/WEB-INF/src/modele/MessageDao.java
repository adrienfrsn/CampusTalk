package modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class MessageDao {
    
    public Message findMessage(int id) {
        Message message = new Message();
        try(Connection con = DS.instance.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM message WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                message.setId(rs.getInt("id"));
                message.setContenu(rs.getString("contenu"));
                message.setDatePublication(rs.getTimestamp("datePublication").toLocalDateTime());
                message.setFilId(rs.getInt("filId"));
                message.setAuteurEmail(rs.getString("auteurEmail"));
                message.setFileName(rs.getString("fileName"));
                message.setLikeCount(rs.getBoolean("likeCount"));
            } else {
                System.out.println("Message inexistant");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return message;
    }

    public List<Message> findAll() {
        List<Message> messages = new ArrayList<>();
        try(Connection con = DS.instance.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM message ORDER BY datePublication, id");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getInt("id"));
                message.setContenu(rs.getString("contenu"));
                message.setDatePublication(rs.getTimestamp("datePublication").toLocalDateTime());
                message.setFilId(rs.getInt("filId"));
                message.setAuteurEmail(rs.getString("auteurEmail"));
                message.setFileName(rs.getString("fileName"));
                message.setLikeCount(rs.getBoolean("likeCount"));
                messages.add(message);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public List<Message> findByFil(int idFil) {
        List<Message> messages = new ArrayList<>();
        try(Connection con = DS.instance.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM message WHERE filId = ? ORDER BY datePublication, id");
            ps.setInt(1, idFil);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getInt("id"));
                message.setContenu(rs.getString("contenu"));
                message.setDatePublication(rs.getTimestamp("datePublication").toLocalDateTime());
                message.setFilId(rs.getInt("filId"));
                message.setAuteurEmail(rs.getString("auteurEmail"));
                message.setFileName(rs.getString("fileName"));
                message.setLikeCount(rs.getBoolean("likeCount"));
                messages.add(message);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public String findUserName(String email) {
        String userName = "";
        try(Connection con = DS.instance.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT nom FROM utilisateur WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userName = rs.getString("nom");
            } else {
                System.out.println("Utilisateur inexistant");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return userName;
    }

    public void delete(int id) {
        try(Connection con = DS.instance.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM message WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void create(Message message) {
        try(Connection con = DS.instance.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO message (contenu, filid, auteuremail, fileName) VALUES (?, ?, ?, ?)");
            ps.setString(1, message.getContenu());
            ps.setInt(2, message.getfilId());
            ps.setString(3, message.getAuteurEmail());
            ps.setString(4, message.getFileName());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    //cette methode permet de mettre a jour le nombre de like d'un message en fonction de l'action de l'utilisateur (like ou unlike)
    public boolean updateLike(int id) {
        try(Connection con = DS.instance.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT likeCount FROM message WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                boolean currentLikeStatus = rs.getBoolean("likeCount");
                boolean newLikeStatus = !currentLikeStatus;
                ps = con.prepareStatement("UPDATE message SET likeCount = ? WHERE id = ?");
                ps.setBoolean(1, newLikeStatus);
                ps.setInt(2, id);
                ps.executeUpdate();
                return true;
            } else {
                System.out.println("Message inexistant");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
