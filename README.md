# Inventory Management System

An inventory management system built using Java Swing and connected to a MySQL database.

## Table of Contents

- [Description](#description)
- [Features](#features)
- [Screenshots](#screenshots)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Database Setup](#database-setup)



## Description

This inventory management system allows users to perform various operations related to managing stock, categories, and sales. It is built using Java Swing for the GUI and connected to a MySQL database for data storage.

## Features

- **Save an Article:** Record an article, setting its stock quantity to zero.
- **Display List of Articles in Stock:** Showcase the list of articles in stock along with their respective categories.
- **Display Article Categories:** Present the categories of articles.
- **Modify or Delete an Article:** Edit or remove an article from the stock (deletion is subject to prior control).
- **Search for an Article in Stock:** Find an article within the stock.
- **Display Total Number of Articles in Stock:** Show the overall count of articles in stock.
- **Display List of Articles Below Restocking Threshold:** View the list of articles below the restocking threshold.
- **Restock an Article:** Replenish the stock of an article.
- **Sell an Article Available in Stock:** Conduct the sale of an article present in the stock.
- **Display List of Sold Articles:** Present the list of articles that have been sold.
- **Display List of Article Restockings:** Show the history of article restockings.
- **Provide Receipt after Each Article Sale:** Generate a receipt after each article sale.
- **Authentication:** Users must authenticate before gaining access to the application.


## Screenshots
### Dashboard
![image](https://github.com/Fadilix/articles-management/assets/121851593/49ec14d2-5c33-4fb2-8f5b-94608906b198)

### Navbar
![image](https://github.com/Fadilix/articles-management/assets/121851593/c1b95a3a-4c22-441d-8734-b07e540007f7)

### Articles below the restocking threshold (colored in red)
![image](https://github.com/Fadilix/articles-management/assets/121851593/ea4d153f-43ec-4145-8ba6-b2c7309f747a)

### Add a article
![image](https://github.com/Fadilix/articles-management/assets/121851593/561df954-2801-47af-a8d9-b619558b0da0)

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) installed
- MySQL database server installed
- Code editor (e.g., Visual Studio Code, NetBeans, IntelliJ IDEA, Eclipse)
- Git installed (for cloning the repository)

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/Fadilix/articles-management.git
   ```
## Usage
- Go in the main folder and run Main.java


## Database setup
- Create a MySQL database named `gestion_articles`.
- Import the provided SQL script to set up the necessary tables:
  * Go the in the `sql` folder
  * Use the `gestion_articles.sql` file
