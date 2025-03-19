package controleur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.Abonnement;
import modele.AbonnementDao;
import modele.FilDeDiscussion;
import modele.FilDeDiscussionDAO;
import modele.Message;
import modele.MessageDao;

// http GET http://localhost:8080/CampusTalk/getFils Cookie:"JSESSIONID=?" --> F12>Storage>JSESSIONID
// curl -X GET http://localhost:8080/CampusTalk/getFils --cookie "JSESSIONID=?"

@WebServlet("/getFils")
public class GetFils extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        if (!Auth.checkLog(req, res)) {
            return;
        }

        String email = (String) req.getSession().getAttribute("email");
        AbonnementDao abonnementDao = new AbonnementDao();
        FilDeDiscussionDAO filDao = new FilDeDiscussionDAO();
        MessageDao messageDao = new MessageDao();

        try {
            List<Abonnement> abonnements = abonnementDao.findAbonnements(email);
            List<FilDeDiscussion> fils = new ArrayList<>();
            Map<Integer, List<Message>> messagesMap = new HashMap<>();

            for (Abonnement abonnement : abonnements) {
                FilDeDiscussion fil = filDao.findById(abonnement.getIdFil());
                fils.add(fil);
                List<Message> messages = messageDao.findByFil(fil.getId());
                messagesMap.put(fil.getId(), messages);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("fils", fils);
            jsonResponse.put("messages", messagesMap);

            res.setContentType("application/json");
            res.getWriter().write(objectMapper.writeValueAsString(jsonResponse));
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.getWriter().write("{\"error\":\"Une erreur est survenue lors de la récupération des données.\"}");
        }
    }
}