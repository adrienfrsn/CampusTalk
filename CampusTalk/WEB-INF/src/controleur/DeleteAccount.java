package controleur;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;
import java.io.IOException;
import modele.UtilisateurDao;

@WebServlet("/deleteAccount")
public class DeleteAccount extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (!Auth.checkLog(req, res)) {
            return;
        }

        String email = (String) req.getSession().getAttribute("email");
        UtilisateurDao udao = new UtilisateurDao();
        udao.delete(email);

        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        res.sendRedirect(req.getContextPath() + "/login");
    }
}
