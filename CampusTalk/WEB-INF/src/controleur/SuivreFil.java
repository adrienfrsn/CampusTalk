package controleur;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.AbonnementDao;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/suivreFil")
public class SuivreFil extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        if (!Auth.checkLog(req, res)) {
            return;
        }
        int id = Integer.parseInt(req.getParameter("id"));
        String email = (String) req.getSession().getAttribute("email");
        AbonnementDao adao = new AbonnementDao();
        adao.abonner(email, id);
        req.getRequestDispatcher("/WEB-INF/vue/listerFil.jsp").forward(req, res);
    }
}