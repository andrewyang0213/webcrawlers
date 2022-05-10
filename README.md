# Web crawler/scraper Project

This repository details the progress and journey of creating a web scrapper program that crawls on a user-given webpage and downloads images from the webpage. 
The final iteration of this project combines the web scrapper program (using multithreading) and corresponding MySQL database with a Java Springboot framework to create a local webpage that displays the contents of the MySQL database that stores the information regarding the images that were downloaded and the download status. The webpage also includes an user authentication feature that is incorporated using Java Web Tokens.

Start mysql server in terminal: mysql.server.start 

1. Run download-final to compile mysql database with images to be downloaded
2. Run Spring-boot-JWT-Final to launch springboot framework in order to visually access databases generated
3. Go to localhost:8080 to access springboot website

localhost login details:
  username: Admin
  password: 123456
