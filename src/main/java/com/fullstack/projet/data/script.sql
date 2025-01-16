CREATE DATABASE HOPE;
USE HOPE;

CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'TEACHER', 'STUDENT') NOT NULL
);

CREATE TABLE tool (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    domain VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    link VARCHAR(255),
    added_by_id BIGINT,
    FOREIGN KEY (added_by_id) REFERENCES user(id) ON DELETE SET NULL
);

CREATE TABLE feedback (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    comment TEXT NOT NULL,
    tool_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (tool_id) REFERENCES tool(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

INSERT INTO user (first_name, last_name, email, password, role) VALUES
('Admin', 'User', 'admin@hope.com', 'password', 'ADMIN'),
('John', 'Doe', 'john.doe@example.com', 'password', 'TEACHER'),
('Jane', 'Smith', 'jane.smith@example.com', 'password', 'STUDENT');

INSERT INTO tool (name, domain, description, link, added_by_id) VALUES
('GitHub Global Campus (GitHub Education)', 'Bouquet de services', 'Sélection d\'outils et de services pour booster votre productivité en tant qu\'élève-ingénieur du numérique. Le GitHub Student Developer Pack vous donne un accès gratuit à un impressionnant catalogue de services et d\'outils. Ex: GitHub pro, GitHub Copilot, DigitalOcean, Outils JetBrains (IntelliJ IDEA Ultimate, PyCharm,...), Azure, Heroku, etc...', 'https://education.github.com/discount_requests/application', 1),
('Coding Rooms', 'Codage / Développement', 'Exercices pratiques de programmation, en ligne', 'https://www.codingrooms.com/', 1),
('Nowledgeable', 'Codage / Développement', 'Exercices pratiques de programmation, en ligne', 'https://nowledgeable.com/', 1),
('Jupyter notebook', 'Codage / Développement', 'Jupyter Notebook est une application web qui permet de créer et partager des documents qui contiennent à la fois du code et du texte', 'https://jupyter.org/', 1),
('repl.it', 'Codage / Développement', 'Exercices pratiques de programmation (notamment de programmation fonctionnelle) en ligne ou via une app', 'https://replit.com/', 1),
('Google Colaboratory (Colab)', 'Codage / Développement', 'Colab est une plateforme très pratique pour exécuter du code Python sans avoir besoin d\'installer Python localement.', 'https://colab.research.google.com/?hl=fr', 1),
('Environnements virtuels', 'Hyperviseurs', 'Machines virtuelles', '???', 1),
('Environnements virtuels', 'Containers', 'Containers Docker', 'https://www.docker.com/', 1),
('TryHackme', 'Cyber sécurité', 'TryHackMe est une excellente ressource pour se former à la cybersécurité de manière pratique.', 'https://tryhackme.com/', 1),
('Hack The Box', 'Cyber sécurité', 'Hack The Box est une plateforme de cybersécurité réputée pour ses challenges de type "capture de drapeaux" (CTF).', 'https://www.hackthebox.com/', 1),
('AWS', 'Cloud provider', 'Fournisseur Cloud', 'https://signin.aws.amazon.com/signup?request_type=register', 1),
('AWS Academy', 'Formations en ligne', 'Plateforme d\'AWS de formations en ligne', 'https://aws.amazon.com/fr/training/awsacademy/', 1),
('Azure', 'Cloud provider', 'Fournisseur Cloud', 'https://azure.microsoft.com/en-us', 1),
('Integral Calculator', 'Mathématiques', 'Calcul d\'intégrales en ligne', 'https://www.integral-calculator.com/', 1),
('eMathHelp', 'Mathématiques', 'Résolution de problèmes mathématiques, étape par étape', 'https://www.emathhelp.net/en/', 1),
('MultisimLive', 'Electronique', 'Simulation de programmation de cartes électroniques', 'https://www.multisim.com/', 1),
('MATLAB & Simulink (MathWorks)', 'Mathématiques', 'Calcul numérique / Analyse de données', 'https://fr.mathworks.com/products/matlab/student.html', 1),
('Patchwork3D & AccelVR (Lumiscaphe)', 'Réalité virtuelle / augmentée', 'Modélisation 3D / Rendu RV', 'https://resources.lumiscaphe.com/Software_Suite/2023/en/accel-vr.html', 1),
('Kaggle', 'Data Science', 'Kaggle est une plateforme web qui fournit des outils et des ressources puissants pour aider à progresser en Data Science.', 'https://www.kaggle.com/', 1),
('Notion', 'Gestion de projets et collaboration', 'Application/plateforme de prise de notes, de gestion de projet et de collaboration.', 'https://www.notion.so/fr-fr', 1),
('Trello', 'Gestion de projets et collaboration', 'Trello est une plateforme de gestion de projet très visuelle et intuitive.', 'https://trello.com/home', 1),
('Code Ocean', 'Data Science', 'Code Ocean une plateforme centralisée pour la création, le partage, la publication, la préservation et la réutilisation de code et de données.', 'https://codeocean.com/', 1),
('Marp', 'Génération de documents', 'Création de slides à partir de documents Markdown', 'https://marp.app/', 1),
('LaTeX', 'Génération de documents', 'LaTeX est un langage et un système de composition de documents', 'https://www.latex-project.org/', 1),
('Material for mkdocs', 'Génération de documents', 'Création de documentation (de code)', 'https://squidfunk.github.io/mkdocs-material/', 1),
('Looping', 'Bases de données', 'Modélisation conceptuelle de données', 'https://www.looping-mcd.fr/', 1),
('MongoDB Compass', 'Bases de données', 'GUI pour MongoDB', 'https://www.mongodb.com/products/tools/compass', 1),
('Wireshark', 'Réseaux', 'Wireshark est un analyseur de paquets libre et gratuit.', 'https://www.wireshark.org/', 1);

INSERT INTO feedback (comment, tool_id, user_id) VALUES
('Très bon outil pour développer des compétences en codage', 1, 2),
('Idéal pour des exercices en ligne', 2, 3),
('Plateforme très utile pour la programmation', 3, 2),
('Outil très complet pour l\'enseignement de la data science', 4, 3),
('Parfait pour tester des codes en ligne', 5, 2),
('Colab est parfait pour apprendre le machine learning', 6, 3),
('Les environnements virtuels sont pratiques pour les tests', 7, 2),
('Docker facilite le déploiement d\'applications', 8, 2),
('TryHackMe rend l\'apprentissage de la cybersécurité ludique', 9, 3),
('Hack The Box est un défi amusant pour les hackers éthiques', 10, 3),
('AWS est idéal pour les projets cloud', 11, 2),
('Azure offre des ressources complètes pour les développeurs', 12, 3),
('Integral Calculator est très utile pour les études de maths', 13, 3),
('eMathHelp explique bien les étapes', 14, 2),
('MultisimLive aide pour les simulations électroniques', 15, 3),
('MATLAB est parfait pour l\'analyse de données', 16, 2),
('Patchwork3D produit des rendus réalistes', 17, 3),
('Kaggle est idéal pour les data scientists', 18, 2),
('Notion organise tout en un seul endroit', 19, 3),
('Trello est très visuel et facile à utiliser', 20, 3),
('Code Ocean simplifie le partage de projets', 21, 3),
('Marp rend les présentations simples', 22, 2),
('LaTeX est essentiel pour les documents académiques', 23, 3),
('Material for mkdocs est excellent pour la documentation', 24, 2),
('Looping aide à modéliser des données complexes', 25, 3),
('MongoDB Compass simplifie les interactions avec MongoDB', 26, 2),
('Wireshark est incontournable pour l\'analyse réseau', 27, 3);
