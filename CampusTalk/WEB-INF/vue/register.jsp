<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <title>CampusTalk - Inscription</title>
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
        </nav>
    </aside>
    <div class="flex-1 p-6">
        <div class="max-w-md mx-auto mt-12 p-6 bg-gray-800 rounded-lg shadow-md">
            <h2 class="text-2xl font-bold mb-4 text-center">Inscription</h2>
            <% if (request.getAttribute("error") != null) { %>
                <div class="bg-red-100 text-red-700 p-4 rounded-lg mb-4">
                    <%= request.getAttribute("error") %>
                </div>
                <% request.removeAttribute("error"); } %>
            <form action="register" method="post">
                <div class="mb-4">
                    <label for="nom" class="block text-gray-300 font-semibold">Nom</label>
                    <input type="text" class="w-full p-3 border border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400 bg-gray-700 text-white" name="nom" id="nom" placeholder="Entrez votre nom" required>
                </div>
                <div class="mb-4">
                    <label for="email" class="block text-gray-300 font-semibold">Adresse e-mail</label>
                    <input type="email" class="w-full p-3 border border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400 bg-gray-700 text-white" name="email" id="email" placeholder="Entrez votre e-mail" required>
                </div>
                <div class="mb-4">
                    <label for="motdepasse" class="block text-gray-300 font-semibold">Mot de passe</label>
                    <input type="password" class="w-full p-3 border border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400 bg-gray-700 text-white" name="motdepasse" id="motdepasse" placeholder="Choisissez un mot de passe" required>
                </div>
                <div class="mb-4">
                    <input type="checkbox" name="remember" id="remember" class="mr-2">
                    <label for="remember" class="text-gray-300">Se souvenir de moi</label>
                </div>
                <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded-lg shadow-md hover:bg-blue-600 transition-colors w-full">S'inscrire</button>
            </form>
            <div class="text-center mt-3">
                <p>Vous avez déjà un compte ? <a href="<%= request.getContextPath() %>/login" class="text-blue-500 hover:underline">Se connecter</a></p>
            </div>
        </div>
    </div>
</body>
</html>