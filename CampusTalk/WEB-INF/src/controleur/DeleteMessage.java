package controleur;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.Message;
import modele.MessageDao;

@WebServlet("/deleteMessage")
public class DeleteMessage extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        if (!Auth.checkLog(req, res)) {
            return;
        }
        req.getRequestDispatcher("/WEB-INF/vue/listeFil.jsp").forward(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        if (!Auth.checkLog(req, res)) {
            return;
        }
        String email = (String) req.getSession().getAttribute("email");
        int messageId = Integer.parseInt(req.getParameter("messageId"));
        MessageDao messageDao = new MessageDao();
        Message message = messageDao.findMessage(messageId);
        if (message.getAuteurEmail().equals(email)) {
            messageDao.delete(messageId);
        }
        res.sendRedirect(req.getHeader("Referer"));
    }
}