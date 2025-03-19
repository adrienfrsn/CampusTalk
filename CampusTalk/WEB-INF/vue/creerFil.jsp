<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="modele.AbonnementDao" %>
<%@ page import="modele.FilDeDiscussion" %>
<%@ page import="modele.FilDeDiscussionDAO" %>
<%@ page import="modele.Abonnement" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CampusTalk - Créer un Fil de Discussion</title>
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
                FilDeDiscussionDAO fddao = new FilDeDiscussionDAO();
                for (Abonnement abonnement : abonnements) {
                    FilDeDiscussion fil = fddao.findById(abonnement.getIdFil());
            %>
            <a href="<%= request.getContextPath() %>/fil?id=<%= fil.getId() %>" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700 flex items-center">
                <img src="<%= request.getContextPath() %>/images/<%= fil.getLogo() %>" alt="Logo" class="w-8 h-8 mr-2">
                <span><%= fil.getNom() %></span>
            </a>
            <% } %>
        </nav>

    </aside>
    <div class="flex-1 p-6">

        <% if (request.getAttribute("error") != null) { %>
            <div class="max-w-2xl mx-auto mt-4 p-4 bg-red-100 text-red-700 rounded-lg">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <div class="max-w-2xl mx-auto mt-8 p-6 bg-gray-800 rounded-lg shadow-md">
            <h1 class="text-2xl font-bold mb-4 text-center">Créer un nouveau fil de discussion</h1>
            <form action="<%= request.getContextPath() %>/creerFil" method="post" enctype="multipart/form-data" class="space-y-4">
                <div class="mb-4">
                    <label for="nom" class="block text-gray-300 font-semibold">Nom du fil:</label>
                    <input type="text" class="w-full p-3 border border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400 bg-gray-700 text-white" id="nom" name="nom" required>
                </div>
                <div class="mb-4">
                    <label for="description" class="block text-gray-300 font-semibold">Description:</label>
                    <textarea class="w-full p-3 border border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400 bg-gray-700 text-white" id="description" name="description" rows="3" required></textarea>
                </div>
                <div class="mb-4">
                    <label for="logo" class="block text-gray-300 font-semibold">Logo du fil:</label>
                    <div class="flex items-center">
                        <input type="file" id="file" name="file" class="text-gray-300">
                    </div>
                </div>
                <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded-lg shadow-md hover:bg-blue-600 transition-colors w-full">Créer</button>
            </form>
        </div>
    </div>
</body>
</html>