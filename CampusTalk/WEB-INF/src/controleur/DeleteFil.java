package controleur;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.FilDeDiscussionDAO;

@WebServlet("/deleteFil")
public class DeleteFil extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        if (!Auth.checkLog(req, res)) {
            return;
        }
        int id = Integer.parseInt(req.getParameter("id"));
        FilDeDiscussionDAO filDao = new FilDeDiscussionDAO();
        if (filDao.delete(id)) {
            req.getSession().setAttribute("deleteFil", "Le fil de discussion a bien été supprimé.");
        } else {
            req.getSession().setAttribute("deleteFil", "Erreur lors de la suppression du fil de discussion.");
        }
        res.sendRedirect(req.getContextPath() + "/accueil");
    }
}
