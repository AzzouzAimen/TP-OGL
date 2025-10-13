package com.example.dao;

public class Scripts {

    static final String sql = "CREATE TABLE users (\n" +
            "    user_id INT PRIMARY KEY AUTO_INCREMENT,\n" +
            "    name VARCHAR(255) NOT NULL,\n" +
            "    email VARCHAR(255) NOT NULL,\n" +
            "    phone VARCHAR(20) NOT NULL\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE parkings (\n" +
            "    parking_id INT PRIMARY KEY AUTO_INCREMENT,\n" +
            "    name VARCHAR(255) NOT NULL,\n" +
            "    address VARCHAR(255) NOT NULL,\n" +
            "    capacity INT NOT NULL\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE parking_places (\n" +
            "    place_id INT PRIMARY KEY AUTO_INCREMENT,\n" +
            "    place_name VARCHAR(20) NOT NULL,\n" +
            "    place_status VARCHAR(20) NOT NULL,\n" +
            "    parking_id INT NOT NULL,\n" +
            "    FOREIGN KEY (parking_id) REFERENCES parkings(parking_id)\n" +
            ");\n" +
            "\n" +
            "    CREATE TABLE reservations (\n" +
            "    id_reservation INT PRIMARY KEY AUTO_INCREMENT,\n" +
            "    place_id INT NOT NULL,\n" +
            "    user_id INT NOT NULL,\n" +
            "    status VARCHAR(255) NOT NULL,\n" +
            "    start_time TIMESTAMP  NOT NULL,\n" +
            "    end_time TIMESTAMP NOT NULL,\n" +
            "    FOREIGN KEY (place_id) REFERENCES parking_places(place_id),\n" +
            "    FOREIGN KEY (user_id) REFERENCES users(user_id)\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE payments (\n" +
            "    payment_id INT PRIMARY KEY AUTO_INCREMENT,\n" +
            "    amount DECIMAL(10,2) NOT NULL,\n" +
            "    id_reservation INT NOT NULL,\n" +
            "    FOREIGN KEY (id_reservation) REFERENCES reservations(id_reservation));\n";



}
