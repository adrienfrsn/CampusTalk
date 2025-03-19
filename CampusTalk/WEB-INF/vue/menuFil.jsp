<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="modele.FilDeDiscussion" %>
<%@ page import="modele.FilDeDiscussionDAO" %>
<%@ page import="modele.AbonnementDao" %>
<%@ page import="modele.Abonnement" %>
<%@ page import="java.util.List" %>

<%
    int id = Integer.parseInt(session.getAttribute("filId").toString());
    FilDeDiscussionDAO fddao = new FilDeDiscussionDAO();
    FilDeDiscussion fil = fddao.findById(id);
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifier Fil de Discussion</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="shortcut icon" href="<%= request.getContextPath() %>/images/logo.png">
</head>
<body class="bg-gray-900 text-white flex flex-col md:flex-row">
    <aside class="w-full md:w-64 bg-gray-800 text-white h-auto md:h-screen">
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
    <div class="flex-1 p-6">
        <div class="max-w-2xl mx-auto mt-8 p-6 bg-gray-800 rounded-lg shadow-lg">
            <h2 class="text-2xl font-bold mb-4 text-center">Modifier le Fil de Discussion</h2>
            <form action="<%= request.getContextPath() %>/modifierFil" method="post" enctype="multipart/form-data">
                <input type="hidden" name="id" value="<%= fil.getId() %>">
                <div class="mb-4">
                    <label for="nom" class="block text-gray-300 font-semibold">Nom :</label>
                    <input type="text" id="nom" name="nom" value="<%= fil.getNom() %>" class="w-full p-3 border border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400 bg-gray-700 text-white" required>
                </div>
                <div class="mb-4">
                    <label for="description" class="block text-gray-300 font-semibold">Description :</label>
                    <textarea id="description" name="description" class="w-full p-3 border border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400 bg-gray-700 text-white" rows="3" required><%= fil.getDescription() %></textarea>
                </div>
                <div class="mb-4">
                    <label for="file" class="block text-gray-300 font-semibold">Logo :</label>
                    <input type="file" id="file" name="file" class="w-full p-3 border border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400 bg-gray-700 text-white">
                    <% if (fil.getLogo() != null) { %>
                    <img src="<%= request.getContextPath() %>/uploads/<%= fil.getLogo() %>" alt="Logo" class="mt-2 message-image">
                    <% } %>
                </div>
                <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded-lg shadow-md hover:bg-blue-600 transition-colors w-full">Sauvegarder</button>
            </form>
            <form action="<%= request.getContextPath() %>/deleteFil" method="post" class="mt-4">
                <input type="hidden" name="id" value="<%= fil.getId() %>">
                <button type="submit" class="bg-red-500 text-white px-4 py-2 rounded-lg shadow-md hover:bg-red-600 transition-colors w-full">Supprimer</button>
            </form>
        </div>
    </div>
</body>
</html>