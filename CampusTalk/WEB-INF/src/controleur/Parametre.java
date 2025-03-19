package controleur;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.GenerateToken;
import modele.Utilisateur;
import modele.UtilisateurDao;
import org.apache.commons.text.StringEscapeUtils;

@WebServlet("/parametre")
public class Parametre extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (!Auth.checkLog(req, res)) {
            return;
        }
        req.getRequestDispatcher("/WEB-INF/vue/parametre.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (!Auth.checkLog(req, res)) {
            return;
        }

        String email = (String) req.getSession().getAttribute("email");
        String nom = StringEscapeUtils.escapeHtml4(req.getParameter("nom"));
        String newEmail = StringEscapeUtils.escapeHtml4(req.getParameter("email"));
        String motdepasse = StringEscapeUtils.escapeHtml4(req.getParameter("motdepasse"));

        UtilisateurDao udao = new UtilisateurDao();
        if (!email.equals(newEmail) && udao.emailExists(newEmail)) {
            req.setAttribute("error", "Cet email est déjà utilisé.");
            req.getRequestDispatcher("/WEB-INF/vue/parametre.jsp").forward(req, res);
            return;
        }

        Utilisateur utilisateur = udao.findUtilisateur(email);
        utilisateur.setNom(nom);
        utilisateur.setEmail(newEmail);
        if (motdepasse != null && !motdepasse.isEmpty()) {
            utilisateur.setMotDePasse(motdepasse);
        }

        udao.update(utilisateur, email);
        req.getSession().setAttribute("email", newEmail);
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                cookie.setValue(GenerateToken.generateToken(newEmail, motdepasse));
                res.addCookie(cookie);
                break;
            }
        }
        res.sendRedirect(req.getContextPath() + "/accueil");
    }
}
