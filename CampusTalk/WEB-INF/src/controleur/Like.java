package controleur;


import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.MessageDao;

@WebServlet("/Like")
public class Like extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        if (!Auth.checkLog(req, res)) {
            return;
        }

        int messageId = Integer.parseInt(req.getParameter("messageId"));
        MessageDao messageDao = new MessageDao();

        if (messageDao.updateLike(messageId)) {
            res.sendRedirect(req.getHeader("Referer"));
        } else {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la mise à jour du like.");
        }
    }
    
}
