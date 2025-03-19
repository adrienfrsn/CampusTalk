package controleur;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.AbonnementDao;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/fil")
public class Fil extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        if (!Auth.checkLog(req, res)) {
            return;
        }
        int id = Integer.parseInt(req.getParameter("id"));
        String email = (String) req.getSession().getAttribute("email");
        AbonnementDao abonnementDao = new AbonnementDao();
        if (!abonnementDao.isAbonne(email, id)) {
            res.sendRedirect(req.getContextPath() + "/listerFil");
            return;
        }
        req.setAttribute("id", id);
        req.getSession().setAttribute("idFil", id);
        req.getRequestDispatcher("/WEB-INF/vue/fil.jsp").forward(req, res);
    }
}
