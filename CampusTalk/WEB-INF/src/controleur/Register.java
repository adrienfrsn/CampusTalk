package controleur;

import java.time.LocalDateTime;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.ServletException;
import java.io.IOException;
import modele.Utilisateur;
import modele.UtilisateurDao;
import org.apache.commons.text.StringEscapeUtils;
import modele.GenerateToken;

@WebServlet("/register")
public class Register extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/vue/register.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        UtilisateurDao udao = new UtilisateurDao();
        String email = StringEscapeUtils.escapeHtml4(req.getParameter("email"));
        String motdepasse = StringEscapeUtils.escapeHtml4(req.getParameter("motdepasse"));
        String nom = StringEscapeUtils.escapeHtml4(req.getParameter("nom"));
        String token = GenerateToken.generateToken(email, motdepasse);
        System.out.println(token);
        if (udao.emailExists(email)) {
            req.setAttribute("error", "L'email est déjà utilisé.");
            req.getRequestDispatcher("/WEB-INF/vue/register.jsp").forward(req, res);
        } else {
            udao.insert(new Utilisateur(
            nom,
            email,
            motdepasse,
            LocalDateTime.now(),
            token
            ));
            req.getSession().setAttribute("email", email);

            if ("on".equals(req.getParameter("remember"))) {
                Cookie cookie = new Cookie("token", token);
                cookie.setMaxAge(60 * 60 * 24 * 30);
                res.addCookie(cookie);
            }

            res.sendRedirect(req.getContextPath() + "/accueil");
        }
    }
}
