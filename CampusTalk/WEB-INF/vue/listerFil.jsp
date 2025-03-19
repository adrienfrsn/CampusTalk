<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="modele.FilDeDiscussion" %>
<%@ page import="modele.FilDeDiscussionDAO" %>
<%@ page import="modele.AbonnementDao" %>
<%@ page import="modele.Abonnement" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CampusTalk - Fils de Discussion</title>
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

        <div class="max-w-2xl mx-auto mt-8 p-6 bg-gray-800 rounded-lg shadow-md">

            <h2 class="text-2xl font-bold mb-4 text-center">Fils de Discussion</h2>
            <p class="text-gray-400 mb-4 text-center">(auxquels vous n'êtes pas abonné)</p>
            <% 
                Set<Integer> abonnementsIds = new HashSet<>();
                for (Abonnement abonnement : abonnements) {
                    abonnementsIds.add(abonnement.getIdFil());
                }

                for (FilDeDiscussion f : fddao.findAll()) {
                    if (!abonnementsIds.contains(f.getId())) {
            %>
            <div class="mb-4 p-4 bg-gray-700 rounded-lg shadow-md">
                <img src="<%= request.getContextPath() %>/images/<%= f.getLogo() %>" alt="Logo" class="w-16 h-16 mx-auto">
                <h4 class="font-bold text-lg"><%= f.getNom() %></h4>
                <% if (f.getDescription() != null) { %>
                    <p class="text-gray-400"><%= f.getDescription() %></p>
                <% } %>
                <a href="<%= request.getContextPath() %>/suivreFil?id=<%= f.getId() %>" class="bg-blue-500 text-white px-4 py-2 rounded mt-2 inline-block">Suivre</a>
            </div>
            <% 
                    }
                }
            %>
        </div>
    </div>
</body>
</html>