package controleur;

import java.time.LocalDateTime;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import modele.FilDeDiscussion;
import modele.FilDeDiscussionDAO;
import modele.UtilisateurDao;
import jakarta.servlet.ServletException;

import java.io.File;
import java.io.IOException;
import org.apache.commons.text.StringEscapeUtils;

@WebServlet("/creerFil")
@MultipartConfig(
    maxFileSize = 1024 * 1024 * 5,
    maxRequestSize = 1024 * 1024 * 10,
    fileSizeThreshold = 0
)
public class CreerFil extends HttpServlet {
    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (!Auth.checkLog(req, res)) {
            return;
        }
        req.getRequestDispatcher("/WEB-INF/vue/creerFil.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        if (!Auth.checkLog(req, res)) {
            return;
        }
        FilDeDiscussionDAO f = new FilDeDiscussionDAO();
        String nom = StringEscapeUtils.escapeHtml4(req.getParameter("nom"));
        String createuremail = req.getSession().getAttribute("email").toString();
        String description = StringEscapeUtils.escapeHtml4(req.getParameter("description"));
        UtilisateurDao udao = new UtilisateurDao();
        if (!udao.emailExists(createuremail)) {
            req.setAttribute("error", "L'email n'existe pas.");
            req.getRequestDispatcher("/WEB-INF/vue/creerFil.jsp").forward(req, res);
            return;
        }

        Part filePart = req.getPart("file");
        String fileName = null;
        if (filePart != null && filePart.getSize() > 0) {
            fileName = getFileName(filePart);
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            filePart.write(uploadPath + File.separator + fileName);
        } else {
            fileName = "default.png";
        }

        f.create(new FilDeDiscussion(
            -1,
            nom,
            LocalDateTime.now(),
            createuremail,
            description,
            fileName
        ));
        res.sendRedirect(req.getContextPath() + "/listerFil");
    }

    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String cd : contentDisposition.split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
