package controleur;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.text.StringEscapeUtils;

@WebServlet("/accueil")
public class Accueil extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!Auth.checkLog(request, response)) {
            return;
        }

        HttpSession session = request.getSession();
        String email = StringEscapeUtils.escapeHtml4((String) session.getAttribute("email"));
        request.setAttribute("email", email);
        request.getRequestDispatcher("/WEB-INF/vue/accueil.jsp").forward(request, response);
    }
}
