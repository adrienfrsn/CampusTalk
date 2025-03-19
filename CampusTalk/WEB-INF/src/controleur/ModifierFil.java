package controleur;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import modele.FilDeDiscussion;
import modele.FilDeDiscussionDAO;
import org.apache.commons.text.StringEscapeUtils;

@WebServlet("/modifierFil")
@MultipartConfig(
    maxFileSize = 1024 * 1024 * 5,
    maxRequestSize = 1024 * 1024 * 10,
    fileSizeThreshold = 0
)
public class ModifierFil extends HttpServlet {
    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (!Auth.checkLog(req, res)) {
            return;
        }
        if (req.getSession().getAttribute("idFil") == null) {
            res.sendRedirect(req.getContextPath() + "/listerFil");
            return;
        }
        int id = Integer.parseInt(req.getSession().getAttribute("idFil").toString());
        String nom = StringEscapeUtils.escapeHtml4(req.getParameter("nom"));
        String description = StringEscapeUtils.escapeHtml4(req.getParameter("description"));

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

        FilDeDiscussionDAO fddao = new FilDeDiscussionDAO();
        FilDeDiscussion fil = fddao.findById(id);
        fil.setNom(nom);
        fil.setDescription(description);
        if (fileName != null) {
            fil.setLogo(fileName);
        }
        fddao.update(fil);

        res.sendRedirect(req.getContextPath() + "/fil?id=" + id);
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
