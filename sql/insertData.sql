INSERT INTO Article (libel, prix, quantiteEnStock, dateDeCrea, quantiteSeuil, designationCat)
VALUES
    ("Chocolat", 2.5, 100, "2023-01-01", 20, "Alimentation"),
    ("Débardeur", 15.2, 30, "2023-01-02", 5, "Vêtements"),
    ("Costard", 89.99, 20, "2023-01-03", 3, "Vêtements"),
    ("Ordinateur portable", 899.99, 10, "2023-01-04", 2, "Électronique"),
    ("Livre", 12.99, 50, "2023-01-05", 10, "Littérature"),

    ("Casque audio", 49.99, 15, "2023-04-15", 5, "Électronique"),
    ("Sac à dos", 29.99, 25, "2023-04-16", 8, "Accessoires"),
    ("Montre", 79.99, 30, "2023-04-17", 5, "Accessoires"),
    
    -- Ajoutez d"autres lignes avec des valeurs réalistes ici
    ("Smartphone", 699.99, 40, "2023-05-01", 5, "Électronique"),
    ("Chaussures de sport", 49.99, 50, "2023-05-02", 10, "Vêtements"),
    ("Enceinte Bluetooth", 79.99, 20, "2023-05-03", 5, "Électronique"),
    ("Sac à main", 39.99, 30, "2023-05-04", 7, "Accessoires"),
    ("Jeans", 34.99, 40, "2023-05-05", 8, "Vêtements"),
    
    ("Laptop Gaming", 1299.99, 15, "2023-06-01", 2, "Électronique"),
    ("Roman policier", 14.99, 35, "2023-06-02", 7, "Littérature"),
    ("Robe de soirée", 129.99, 10, "2023-06-03", 3, "Vêtements"),
    ("Aspirateur sans fil", 149.99, 18, "2023-06-04", 4, "Électronique"),
    ("Tablette graphique", 199.99, 8, "2023-06-05", 2, "Électronique"),

    ("Parfum", 59.99, 25, "2023-07-01", 5, "Beauté"),
    ("Tapis de yoga", 19.99, 30, "2023-07-02", 5, "Sport"),
    ("Caméra de surveillance", 79.99, 12, "2023-07-03", 3, "Électronique"),
    ("Bouteille d'eau réutilisable", 9.99, 50, "2023-07-04", 10, "Accessoires"),
    ("Manteau d'hiver", 99.99, 15, "2023-07-05", 3, "Vêtements"),

    ("Console de jeu", 299.99, 8, "2023-08-01", 2, "Électronique"),
    ("Bracelet connecté", 49.99, 20, "2023-08-02", 5, "Accessoires"),
    ("Pantalon de jogging", 29.99, 25, "2023-08-03", 5, "Vêtements"),
    ("Guitare acoustique", 179.99, 10, "2023-08-04", 2, "Instruments de musique"),
    ("Lampe de bureau LED", 24.99, 30, "2023-08-05", 5, "Maison et bureau");
    -- Continuez d"ajouter d"autres lignes avec des valeurs réalistes ici

INSERT INTO categorie (designation) VALUES
    ('Alimentation'),
    ('Vêtements'),
    ('Électronique'),
    ('Littérature'),
    ('Accessoires'),
    ('Beauté'),
    ('Sport'),
    ('Maison et bureau'),
    ('Instruments de musique');