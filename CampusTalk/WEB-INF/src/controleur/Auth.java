package controleur;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import modele.UtilisateurDao;
import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Auth {
    public static boolean checkLog(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("token".equals(cookie.getName())) {
                        String token = cookie.getValue();
                        UtilisateurDao udao = new UtilisateurDao();
                        if (udao.check(token)) {
                            String email = udao.findEmailByToken(token);
                            session = request.getSession(true);
                            session.setAttribute("token", token);
                            session.setAttribute("email", email);
                            return true;
                        }
                    }
                }
            }
        }

        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        return true;
    }
}
