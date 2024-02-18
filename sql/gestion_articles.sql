-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : dim. 18 fév. 2024 à 19:34
-- Version du serveur : 10.4.28-MariaDB
-- Version de PHP : 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `gestion_articles`
--

-- --------------------------------------------------------

--
-- Structure de la table `approvisionnement`
--

CREATE TABLE `approvisionnement` (
  `idAppro` int(11) NOT NULL,
  `articleAppro` varchar(255) DEFAULT NULL,
  `dateAppro` date DEFAULT NULL,
  `quantiteAppro` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `approvisionnement`
--

INSERT INTO `approvisionnement` (`idAppro`, `articleAppro`, `dateAppro`, `quantiteAppro`) VALUES
(10, 'Roman policier', '2024-01-31', 20),
(11, 'Livre', '2024-01-31', 11),
(12, 'Sac à dos', '2024-01-31', 2),
(13, 'Livre', '2024-01-31', 55),
(14, 'Console de jeu', '2024-01-31', 22),
(15, 'Ordinateur portable', '2024-02-01', 20),
(16, 'Jeans', '2024-02-01', 200),
(17, 'Chocolat', '2024-02-12', 20),
(18, 'Sac à dos', '2024-02-12', 12),
(19, 'Ordinateur portable', '2024-02-13', 12),
(20, 'Ordinateur portable', '2024-02-13', 90),
(21, 'Livre', '2024-02-13', 70),
(22, 'Montre', '2024-02-15', 2),
(23, 'Montre', '2024-02-15', 10),
(24, 'Chaussures de sport', '2024-02-15', -200),
(25, '', '2024-02-15', 20),
(26, 'Chaussures de sport', '2024-02-15', 200),
(27, 'Chaussures de sport', '2024-02-15', 20),
(28, 'Ordinateur portable', '2024-02-15', 20);

-- --------------------------------------------------------

--
-- Structure de la table `article`
--

CREATE TABLE `article` (
  `idArticle` int(11) NOT NULL,
  `libel` varchar(255) DEFAULT NULL,
  `prix` double DEFAULT NULL,
  `quantiteEnStock` int(11) DEFAULT NULL,
  `dateDeCrea` date DEFAULT NULL,
  `quantiteSeuil` int(11) DEFAULT NULL,
  `designationCat` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `article`
--

INSERT INTO `article` (`idArticle`, `libel`, `prix`, `quantiteEnStock`, `dateDeCrea`, `quantiteSeuil`, `designationCat`) VALUES
(31, 'Ordinateur portable', 899.99, 21, '2023-01-04', 2, 'Électronique'),
(34, 'Sac à dos', 29.99, 39, '2023-04-16', 8, 'Accessoires'),
(35, 'Montre', 79.99, 19, '2023-04-17', 5, 'Accessoires'),
(36, 'Smartphone', 699.99, 0, '2023-05-01', 5, 'Beauté'),
(37, 'Chaussures de sport', 49.99, 5, '2023-05-02', 10, 'Accessoires'),
(38, 'Enceinte Bluetooth', 79.99, 20, '2023-05-03', 5, 'Électronique'),
(39, 'Sac à main', 39.99, 30, '2023-05-04', 7, 'Accessoires'),
(40, 'Jeans', 34.99, 5, '2023-05-05', 8, 'Vêtements'),
(41, 'Laptop Gaming', 1299.99, 15, '2023-06-01', 2, 'Électronique'),
(42, 'Roman policier', 14.99, 55, '2023-06-02', 7, 'Littérature'),
(43, 'Robe de soirée', 129.99, 10, '2023-06-03', 3, 'Vêtements'),
(44, 'Aspirateur sans fil', 149.99, 18, '2023-06-04', 4, 'Électronique'),
(45, 'Tablette graphique', 199.99, 8, '2023-06-05', 2, 'Électronique'),
(46, 'Parfum', 59.99, 25, '2023-07-01', 5, 'Beauté'),
(47, 'Tapis de yoga', 19.99, 28, '2023-07-02', 5, 'Sport'),
(48, 'Caméra de surveillance', 79.99, 12, '2023-07-03', 3, 'Électronique'),
(49, 'Bouteille d\'eau réutilisable', 9.99, 50, '2023-07-04', 10, 'Accessoires'),
(50, 'Manteau d\'hiver', 99.99, 15, '2023-07-05', 3, 'Vêtements'),
(51, 'Console de jeu', 299.99, 30, '2023-08-01', 2, 'Électronique'),
(52, 'Bracelet connecté', 49.99, 20, '2023-08-02', 5, 'Accessoires'),
(53, 'Pantalon de jogging', 29.99, 25, '2023-08-03', 5, 'Vêtements'),
(54, 'Guitare acoustique', 179.99, 10, '2023-08-04', 2, 'Instruments de musique'),
(55, 'Lampe de bureau LED', 24.99, 20, '2023-08-05', 5, 'Maison et bureau'),
(56, 'Casquette', 9.99, 50, '2023-08-06', 10, 'Accessoires'),
(57, 'Téléviseur 4K', 699.99, 12, '2023-08-07', 2, 'Électronique'),
(58, 'Sweat à capuche', 34.99, 40, '2023-08-08', 7, 'Vêtements'),
(59, 'Enceinte portable', 29.99, 25, '2023-08-09', 5, 'Électronique'),
(60, 'Portefeuille', 19.99, 10, '2023-08-10', 5, 'Accessoires'),
(61, 'Sac de voyage', 49.99, 20, '2023-08-11', 5, 'Accessoires'),
(62, 'Tondeuse à cheveux', 39.99, 13, '2023-08-12', 3, 'Beauté'),
(63, 'Short de sport', 19.99, 35, '2023-08-13', 8, 'Vêtements'),
(64, 'Cafetière électrique', 59.99, 18, '2023-08-14', 4, 'Maison et cuisine'),
(65, 'Bijoux fantaisie', 14.99, 50, '2023-08-15', 10, 'Accessoires'),
(66, 'Chaise de bureau', 79.99, 10, '2023-08-16', 2, 'Maison et bureau'),
(67, 'Gants d\'hiver', 12.99, 30, '2023-08-17', 5, 'Accessoires'),
(68, 'Parapluie pliable', 9.99, 40, '2023-08-18', 7, 'Accessoires'),
(69, 'Livre de cuisine', 24.99, 25, '2023-08-19', 5, 'Littérature'),
(70, 'Appareil photo compact', 199.99, 8, '2023-08-20', 2, 'Électronique'),
(71, 'Souris sans fil', 19.99, 30, '2023-08-21', 5, 'Électronique'),
(72, 'Serviette de plage', 14.99, 40, '2023-08-22', 8, 'Accessoires'),
(73, 'Fer à repasser', 29.99, 20, '2023-08-23', 5, 'Maison et cuisine'),
(74, 'Sacoche pour ordinateur portable', 24.99, 25, '2023-08-24', 5, 'Accessoires'),
(75, 'Trottinette électrique', 299.99, 10, '2023-08-25', 2, 'Électronique'),
(76, 'Eau de Cologne', 49.99, 15, '2023-08-26', 3, 'Beauté'),
(77, 'Écharpe en laine', 9.99, 50, '2023-08-27', 10, 'Vêtements'),
(78, 'Haut-parleur Bluetooth', 59.99, 18, '2023-08-28', 4, 'Électronique'),
(79, 'Coussin décoratif', 12.99, 30, '2023-08-29', 5, 'Maison et déco'),
(80, 'Sac isotherme', 19.99, 25, '2023-08-30', 5, 'Accessoires'),
(81, 'Mug en céramique', 7.99, 20, '2023-08-31', 7, 'Maison et cuisine'),
(82, 'Tapis de bain', 14.99, 25, '2023-09-01', 5, 'Maison et déco'),
(83, 'Porte-monnaie', 8.99, 30, '2023-09-02', 5, 'Accessoires'),
(84, 'Ventilateur de bureau', 34.99, 15, '2023-09-03', 3, 'Maison et bureau'),
(85, 'Livre de science-fiction', 16.99, 35, '2023-09-04', 7, 'Littérature'),
(86, 'Bracelet en cuir', 29.99, 20, '2023-09-05', 5, 'Accessoires'),
(87, 'Stylo élégant', 9.99, 50, '2023-09-06', 10, 'Maison et bureau'),
(88, 'Couverture polaire', 19.99, 15, '2023-09-07', 3, 'Maison et déco'),
(89, 'Jeu de société', 24.99, 25, '2023-09-08', 5, 'Jeux et jouets'),
(90, 'Bougie parfumée', 12.99, 30, '2023-09-09', 5, 'Maison et déco'),
(91, 'kenza', 1000000000, 500, '2024-02-09', 20, 'kenza'),
(92, 'something', 20, 0, '2024-02-12', 2, 'Accessoires'),
(93, 'Stylo', 1.99, 0, '2024-02-13', 10, 'Fournitures scolaires'),
(95, 'Mèche (extension de cheveux)', 99, 0, '2024-02-15', 20, 'Beauté'),
(96, 'iPhone 13', 1999.99, 0, '2024-02-15', 20, 'Électronique'),
(97, 'Casque (Moto)', 200.99, 0, '2024-02-15', 10, 'Véhicules'),
(98, 'msi', 2000.99, 0, '2024-02-15', 20, 'java');

-- --------------------------------------------------------

--
-- Structure de la table `articlevendu`
--

CREATE TABLE `articlevendu` (
  `idArticle` int(11) NOT NULL,
  `libel` varchar(255) DEFAULT NULL,
  `prix` double DEFAULT NULL,
  `dateDeVente` date DEFAULT NULL,
  `designationCat` varchar(255) DEFAULT NULL,
  `quantiteVendu` int(11) DEFAULT NULL,
  `prixTotal` double DEFAULT NULL,
  `client` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `articlevendu`
--

INSERT INTO `articlevendu` (`idArticle`, `libel`, `prix`, `dateDeVente`, `designationCat`, `quantiteVendu`, `prixTotal`, `client`) VALUES
(32, 'Chocolat', 2.5, '2024-01-31', 'Alimentation', 2, 5, 'Kénza'),
(33, 'Ordinateur portable', 899.99, '2024-01-31', 'Électronique', 2, 1799.98, 'Fadilou'),
(34, 'Chocolat', 2.5, '2024-01-31', 'Alimentation', 20, 50, 'Romaric'),
(35, 'Jeans', 34.99, '2024-01-31', 'Vêtements', 20, 699.8000000000001, 'Péniel'),
(36, 'Lampe de bureau LED', 24.99, '2024-01-31', 'Maison et bureau', 10, 249.89999999999998, 'Sonia'),
(37, 'Chocolat', 2.5, '2024-02-01', 'Alimentation', 70, 175, 'Jordan'),
(38, 'Montre', 79.99, '2024-02-01', 'Accessoires', 3, 239.96999999999997, '1'),
(39, 'Casque audio', 49.99, '2024-02-01', 'Électronique', 2, 99.98, 'fd'),
(40, 'Casque audio', 49.99, '2024-02-01', 'Électronique', 2, 99.98, 'F'),
(41, 'Casque audio', 49.99, '2024-02-01', 'Électronique', 2, 99.98, 'F'),
(42, 'Casque audio', 49.99, '2024-02-01', 'Électronique', 2, 99.98, 'fd'),
(43, 'Casque audio', 49.99, '2024-02-01', 'Électronique', 2, 99.98, 'F'),
(44, 'Casque audio', 49.99, '2024-02-01', 'Électronique', 2, 99.98, 'f'),
(45, 'Casque audio', 49.99, '2024-02-01', 'Électronique', 2, 99.98, 'FSQD'),
(46, 'Casque audio', 49.99, '2024-02-01', 'Électronique', 1, 49.99, 'FDF'),
(47, 'Jeans', 34.99, '2024-02-01', 'Vêtements', 20, 699.8000000000001, 'Ce gars là'),
(48, 'Smartphone', 699.99, '2024-02-01', 'Électronique', 2, 1399.98, 'FDQSF'),
(49, 'Tapis de yoga', 19.99, '2024-02-01', 'Sport', 2, 39.98, 'Nazifa'),
(50, 'Livre', 12.99, '2024-02-01', 'Littérature', 20, 259.8, 'Fadile'),
(51, 'Ordinateur portable', 899.99, '2024-02-01', 'Électronique', 20, 17999.8, 'Kenza'),
(52, 'Tondeuse à cheveux', 39.99, '2024-02-01', 'Beauté', 2, 79.98, 'Pharel'),
(53, 'Portefeuille', 19.99, '2024-02-01', 'Accessoires', 20, 399.79999999999995, 'Romaric'),
(54, 'Chaussures de sport', 49.99, '2024-02-12', 'Vêtements', 20, 999.8000000000001, 'Achille'),
(55, 'Ordinateur portable', 899.99, '2024-02-13', 'Électronique', 4, 3599.96, 'Fad'),
(56, 'Montre', 79.99, '2024-02-13', 'Accessoires', 20, 1599.8, 'Irin'),
(57, 'Smartphone', 699.99, '2024-02-13', 'Électronique', 1, 699.99, 'Irin'),
(58, 'Livre', 13.99, '2024-02-13', 'Littérature', 90, 1259.1, 'morou fadile'),
(59, 'Chaussures de sport', 49.99, '2024-02-15', 'Vêtements', 25, 1249.75, 'Joyce'),
(60, 'Smartphone', 699.99, '2024-02-15', 'Électronique', 10, 6999.9, '````*;'),
(61, 'Mug en céramique', 7.99, '2024-02-15', 'Maison et cuisine', 20, 159.8, 'fdklfj'),
(62, 'Smartphone', 699.99, '2024-02-15', 'Électronique', 10, 6999.9, 'Sonia'),
(63, 'Ordinateur portable', 899.99, '2024-02-15', 'Électronique', 105, 94498.95, 'Fadilou'),
(64, 'Smartphone', 699.99, '2024-02-15', 'Électronique', 15, 10499.85, 'Kénza'),
(65, 'Jeans', 34.99, '2024-02-15', 'Vêtements', 195, 6823.05, 'Fifi'),
(66, 'Chaussures de sport', 49.99, '2024-02-15', 'Accessoires', 20, 999.8000000000001, 'Client'),
(67, 'Smartphone', 699.99, '2024-02-15', 'Beauté', 2, 1399.98, 'Fadilou');

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

CREATE TABLE `categorie` (
  `idCat` int(11) NOT NULL,
  `designation` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `categorie`
--

INSERT INTO `categorie` (`idCat`, `designation`) VALUES
(14, 'Alimentation'),
(15, 'Vêtements'),
(16, 'Électronique'),
(17, 'Littérature'),
(18, 'Accessoires'),
(19, 'Beauté'),
(20, 'Sport'),
(21, 'Maison et bureau'),
(22, 'Instruments de musique'),
(23, 'Maison et cuisine'),
(24, 'Maison et déco'),
(25, 'Jeux et jouets'),
(26, 'sonia'),
(27, 'Fournitures scolaires'),
(28, 'Véhicules'),
(29, 'java');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `username`, `password`) VALUES
(1, 'admin', 'admin'),
(2, 'admin', 'admin'),
(3, 'keni', 'keni'),
(4, 'sonia', 'sonia'),
(5, 'someone', 'someone'),
(6, 'adminn', 'adminn'),
(7, '        ', '   '),
(8, '        ', '   '),
(9, 'sonia', '123'),
(10, 'ken', 'ken');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `approvisionnement`
--
ALTER TABLE `approvisionnement`
  ADD PRIMARY KEY (`idAppro`);

--
-- Index pour la table `article`
--
ALTER TABLE `article`
  ADD PRIMARY KEY (`idArticle`);

--
-- Index pour la table `articlevendu`
--
ALTER TABLE `articlevendu`
  ADD PRIMARY KEY (`idArticle`);

--
-- Index pour la table `categorie`
--
ALTER TABLE `categorie`
  ADD PRIMARY KEY (`idCat`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `approvisionnement`
--
ALTER TABLE `approvisionnement`
  MODIFY `idAppro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT pour la table `article`
--
ALTER TABLE `article`
  MODIFY `idArticle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=99;

--
-- AUTO_INCREMENT pour la table `articlevendu`
--
ALTER TABLE `articlevendu`
  MODIFY `idArticle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=68;

--
-- AUTO_INCREMENT pour la table `categorie`
--
ALTER TABLE `categorie`
  MODIFY `idCat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
