<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="modele.UtilisateurDao" %>
<%@ page import="modele.AbonnementDao" %>
<%@ page import="modele.FilDeDiscussion" %>
<%@ page import="modele.FilDeDiscussionDAO" %>
<%@ page import="modele.Abonnement" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CampusTalk - Accueil</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="shortcut icon" href="<%= request.getContextPath() %>/images/logo.png">
    <script>
        async function fetchWeather() {
            const response = await fetch('https://api.open-meteo.com/v1/forecast?latitude=50.6292&longitude=3.0573&current_weather=true');
            const data = await response.json();
            document.querySelector('#weather').innerHTML = `
                <h3 class="text-xl font-bold">Météo à Lille</h3>
                <p>Température: \${data.current_weather.temperature}°C</p>
                <p>Vent: \${data.current_weather.windspeed} km/h \${data.current_weather.winddirection} %</p>
            `;
        }

        window.onload = fetchWeather;
    </script>
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
        <% if (session.getAttribute("deleteFil") != null) { %>
            <div class="max-w-2xl mx-auto mt-4 p-4 bg-green-100 text-green-700 rounded-lg">
                <p class="font-bold"><%= session.getAttribute("deleteFil") %></p>
                <% session.removeAttribute("deleteFil"); %>
            </div>
        <% } %>

        <% 
            UtilisateurDao udao=new UtilisateurDao(); 
            String username=udao.findUtilisateur(email).getNom(); 
        %>
        <div class="max-w-2xl mx-auto mt-12 p-6 bg-gray-800 rounded-lg shadow-md">
            <h2 class="text-2xl font-bold mb-4">Bienvenue sur CampusTalk !</h2>
            <p class="mb-4">Bonjour, <%= username %> !</p>
            <p class="mb-4">Vous êtes connecté en tant que <%= email %></p>
        </div>

        <div id="weather" class="max-w-2xl mx-auto mt-12 p-6 bg-gray-800 rounded-lg shadow-md text-center">
            <h3 class="text-xl font-bold">Chargement de la météo...</h3>
        </div>
    </div>
</body>
</html>
