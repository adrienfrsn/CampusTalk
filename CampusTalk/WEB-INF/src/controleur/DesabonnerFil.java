package controleur;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import modele.AbonnementDao;

@WebServlet("/desabonnerFil")
public class DesabonnerFil extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (!Auth.checkLog(req, res)) {
            return;
        }
        int idFil = Integer.parseInt(req.getParameter("idFil"));
        String email = (String) req.getSession().getAttribute("email");
        AbonnementDao adao = new AbonnementDao();
        adao.desabonner(email, idFil);
        req.getRequestDispatcher("/WEB-INF/vue/accueil.jsp").forward(req, res);
    }
}
