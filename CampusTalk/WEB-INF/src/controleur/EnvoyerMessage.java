package controleur;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import modele.Message;
import modele.MessageDao;
import jakarta.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.commons.text.StringEscapeUtils;

@WebServlet("/envoyerMessage")
@MultipartConfig(
    maxFileSize = 1024 * 1024 * 5,
    maxRequestSize = 1024 * 1024 * 10,
    fileSizeThreshold = 0 // comme ça tous les fichiers qui sont normalement des images sont enregistrés sur le serveur
)
public class EnvoyerMessage extends HttpServlet {
    private static final String UPLOAD_DIR = "uploads";

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        if (!Auth.checkLog(req, res)) {
            return;
        }
        req.getRequestDispatcher("/WEB-INF/vue/listeFil.jsp").forward(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        if (!Auth.checkLog(req, res)) {
            return;
        }
        String email = (String) req.getSession().getAttribute("email"); // je pense que ça ne sert a rien d'utiliser StringEscapeUtils.escapeHtml4 sur l'email qui est en session et qui est censé être safe vu qu'on verifie au moment ou on le met en session
        String message = StringEscapeUtils.escapeHtml4(req.getParameter("contenu"));
        int filId = (int) req.getSession().getAttribute("filId");
        if (message == null || message.isEmpty()) {
            res.sendRedirect(req.getContextPath() + "/fil?id=" + filId);
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
        }

        Message m = new Message(-1, message, LocalDateTime.now(), filId, email, fileName);
        MessageDao messageDao = new MessageDao();
        messageDao.create(m);
        res.sendRedirect(req.getContextPath() + "/fil?id=" + filId);
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