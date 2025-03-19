package controleur;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/menuFil")
public class MenuFil extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        if (!Auth.checkLog(req, res)) {
            return;
        }
        req.getRequestDispatcher("/WEB-INF/vue/menuFil.jsp").forward(req, res);
    }
}
