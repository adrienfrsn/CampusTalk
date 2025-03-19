<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="modele.FilDeDiscussion" %>
<%@ page import="modele.FilDeDiscussionDAO" %>
<%@ page import="modele.Message" %>
<%@ page import="modele.MessageDao" %>
<%@ page import="modele.AbonnementDao" %>
<%@ page import="modele.Abonnement" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CampusTalk - Fil de Discussion</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="shortcut icon" href="<%= request.getContextPath() %>/images/logo.png">
    <style>
        .message-container {
            transition: background-color 0.3s ease;
        }
        .message-container:hover {
            background-color: #374151;
        }
        .message-image {
            max-width: 200px; 
            max-height: 200px; 
            object-fit: cover;
            border-radius: 8px;
        }
    </style>
</head>
<body class="bg-gray-900 text-white flex flex-col md:flex-row h-screen">
    <aside class="w-full md:w-64 bg-gray-800 text-white h-auto md:h-full">
        <div class="p-4 text-center">
            <img src="<%= request.getContextPath() %>/images/logo.png" alt="CampusTalk Logo" class="w-16 h-16 mx-auto">
            <h1 class="text-2xl font-bold mt-4">CampusTalk</h1>
        </div>
        <nav class="mt-4">
            <a href="<%= request.getContextPath() %>/accueil" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700">Accueil</a>
            <a href="<%= request.getContextPath() %>/listerFil" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700">Autres Fils de Discussion</a>
            <a href="<%= request.getContextPath() %>/creerFil" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700">Créer un Fil</a>
            <a href="<%= request.getContextPath() %>/parametre" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700">Paramètres</a>
            
            <h3 class="mt-4 px-4 text-lg font-semibold py-2.5">Vos abonnements</h3>
            <% 
                String email = (String) session.getAttribute("email");
                AbonnementDao adao = new AbonnementDao();
                List<Abonnement> abonnements = adao.findAbonnements(email);
                FilDeDiscussionDAO fddao = new FilDeDiscussionDAO();
                for (Abonnement abonnement : abonnements) {
                    FilDeDiscussion filAbonne = fddao.findById(abonnement.getIdFil());
            %>
            <a href="<%= request.getContextPath() %>/fil?id=<%= filAbonne.getId() %>" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700 flex items-center">
                <img src="<%= request.getContextPath() %>/images/<%= filAbonne.getLogo() %>" alt="Logo" class="w-8 h-8 mr-2">
                <span><%= filAbonne.getNom() %></span>
            </a>
            <% } %>
        </nav>
    </aside>

    <div class="flex-1 p-6 flex flex-col">

        <%
            int id = Integer.parseInt(request.getParameter("id"));
            session.setAttribute("filId", id);
            FilDeDiscussion fil = fddao.findById(id);
            MessageDao mdao = new MessageDao();
            List<Message> messages = mdao.findByFil(id);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy 'à' HH:mm");
        %>

        <% if (email.equals(fil.getCreateurEmail())) { %>
        <div class="flex justify-end">
            <form action="<%= request.getContextPath() %>/menuFil" method="post" enctype="multipart/form-data">
                <input type="hidden" name="id" value="<%= fil.getId() %>">
                <button type="submit" class="bg-yellow-500 text-white px-4 py-2 rounded-lg shadow-md hover:bg-yellow-600 transition-colors">Modifier le fil</button>
            </form>
        </div>
        <% } %>
                <h2 class="text-2xl font-bold mb-4 text-center"><%= fil.getNom() %></h2>
                <% if (email != null && adao.isAbonne(email, id)) { %>
                    <form action="<%= request.getContextPath() %>/desabonnerFil" method="post" class="mb-4">
                        <input type="hidden" name="idFil" value="<%= id %>">
                        <button type="submit" class="bg-red-500 text-white px-4 py-2 rounded-lg shadow-md hover:bg-red-600 transition-colors">
                            Se désabonner
                        </button>
                    </form>
                <% } %>
                <div class="flex-1 overflow-y-auto w-full" id="messages-box">
                    <% for (Message m : messages) {
                        LocalDateTime datePublication = m.getDatePublication();
                        String formattedDate = (datePublication != null) ? datePublication.format(formatter) : "Date non disponible";
                    %>
                    <div class="flex items-start mb-4 message-container">
                        <div class="w-10 h-10 rounded-full overflow-hidden">
                            <img src="https://www.gravatar.com/avatar/<%= m.getAuteurEmail().hashCode() %>?d=identicon&s=40" alt="Avatar">
                        </div>
                        <div class="ml-4 bg-gray-700 p-4 rounded-lg">
                            <h4 class="font-bold"><%= mdao.findUserName(m.getAuteurEmail()) %></h4>
                            <p class="text-sm text-gray-400"><%= formattedDate %></p>
                            <p class="mt-2"><%= m.getContenu() %></p>
                            <% if (m.getFileName() != null) { %>
                            <img src="<%= request.getContextPath() %>/uploads/<%= m.getFileName() %>" alt="Image" class="mt-2 message-image">
                            <% } %>
                            <div class="mt-2 flex items-center">
                                <form action="<%= request.getContextPath() %>/Like" method="post" class="mr-2">
                                    <input type="hidden" name="messageId" value="<%= m.getId() %>">
                                    <button type="submit" class="text-blue-500 hover:text-blue-700">
                                        <% if (m.getLikeCount()) { %>
                                        ❤️
                                        <% } else { %>
                                        🤍
                                        <% } %>
                                    </button>
                                </form>
                            </div>
                            <% if (session.getAttribute("email").equals(m.getAuteurEmail())) { %>
                            <form action="<%= request.getContextPath() %>/deleteMessage" method="post" class="mt-2" onsubmit="return confirm('Êtes-vous sûr de vouloir supprimer ce message ?');">
                                <input type="hidden" name="messageId" value="<%= m.getId() %>">
                                <button type="submit" class="text-red-500 hover:text-red-700">Supprimer</button>
                            </form>
                            <% } %>
                        </div>
                    </div>
                    <% } %>
                </div>

                <div class="mt-6 bg-gray-800 p-6 rounded-lg shadow-lg">
                    <form id="messageForm" action="envoyerMessage" method="post" enctype="multipart/form-data">
                        <div class="mb-4">
                            <label for="contenu" class="block text-gray-300 font-semibold">Votre message :</label>
                            <textarea class="w-full p-3 border border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400 bg-gray-700 text-white" id="contenu" name="contenu" rows="3" required></textarea>
                        </div>
                        <div class="mb-4">
                            <div class="flex items-center">
                                <input type="file" id="file" name="file" class="text-gray-300">
                            </div>
                        </div>
                        <input type="hidden" name="filId" value="<%= id %>">
                        <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded-lg shadow-md hover:bg-blue-600 transition-colors">Envoyer</button>
                    </form>
                </div>

        <script>
            window.onload = function () {
                let messagesBox = document.getElementById('messages-box');
                messagesBox.scrollTop = messagesBox.scrollHeight;
            };

            // setInterval(function() {
            //     location.reload();
            // }, 5000);

            document.getElementById('contenu').addEventListener('keypress', function (e) {
                if (e.key === 'Enter' && !e.shiftKey) {
                    e.preventDefault();
                    document.getElementById('messageForm').submit();
                }
            });
        </script>
    </div>
</body>
</html>