DROP TABLE IF EXISTS Abonnement;
DROP TABLE IF EXISTS Message;
DROP TABLE IF EXISTS FilDeDiscussion;
DROP TABLE IF EXISTS Utilisateur;

CREATE TABLE Utilisateur (
    email VARCHAR(100) UNIQUE NOT NULL,
    nom VARCHAR(100) NOT NULL,
    motDePasse VARCHAR(255) NOT NULL,
    dateInscription TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    token VARCHAR(255),
    CONSTRAINT pk_utilisateur PRIMARY KEY (email)
);

CREATE TABLE FilDeDiscussion (
    id SERIAL,
    nom VARCHAR(100) NOT NULL,
    dateCreation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    createurEmail VARCHAR(100),
    description TEXT,
    logo VARCHAR(255) DEFAULT 'default.png',
    CONSTRAINT pk_fil PRIMARY KEY (id),
    CONSTRAINT fk_createur FOREIGN KEY (createurEmail) REFERENCES Utilisateur(email)
        ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE Message (
    id SERIAL,
    contenu TEXT NOT NULL,
    datePublication TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    filId INTEGER,
    auteurEmail VARCHAR(100),
    fileName VARCHAR(255),
    likeCount Boolean DEFAULT FALSE,
    CONSTRAINT pk_message PRIMARY KEY (id),
    CONSTRAINT fk_fil FOREIGN KEY (filId) REFERENCES FilDeDiscussion(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_auteur FOREIGN KEY (auteurEmail) REFERENCES Utilisateur(email)
        ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE Abonnement (
    utilisateurEmail VARCHAR(100),
    filId INTEGER,
    dateAbonnement TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_abonnement PRIMARY KEY (utilisateurEmail, filId),
    CONSTRAINT fk_utilisateur FOREIGN KEY (utilisateurEmail) REFERENCES Utilisateur(email)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_fil FOREIGN KEY (filId) REFERENCES FilDeDiscussion(id)
        ON DELETE CASCADE
);
