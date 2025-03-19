package controleur;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import modele.UtilisateurDao;
import jakarta.servlet.ServletException;
import java.io.IOException;
import org.apache.commons.text.StringEscapeUtils;
import modele.GenerateToken;

@WebServlet("/login")
public class Login extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.getRequestDispatcher("/WEB-INF/vue/login.jsp").forward(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String email = StringEscapeUtils.escapeHtml4(req.getParameter("email"));
        String motdepasse = StringEscapeUtils.escapeHtml4(req.getParameter("motdepasse"));
        String token = GenerateToken.generateToken(email, motdepasse);
        UtilisateurDao udao = new UtilisateurDao();
        if (udao.check(token)) {
            System.out.println("Connexion réussie");
            req.getSession().setAttribute("email", email);

            if ("on".equals(req.getParameter("remember"))) {
                Cookie cookie = new Cookie("token", token);
                cookie.setMaxAge(60 * 60 * 24 * 30); // 30 jours
                res.addCookie(cookie);
            }
            res.sendRedirect(req.getContextPath() + "/accueil");
        } else {
            System.out.println("Connexion échouée");
            req.setAttribute("error", "Email ou mot de passe incorrect.");
            req.getRequestDispatcher("/WEB-INF/vue/login.jsp").forward(req, res);
            res.sendRedirect(req.getContextPath() + "/login");
        }
    }
}