package com.example.dao;

import com.example.entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParkingDao {

    private Connection conn;


    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }



    public void insertParking(Parking parking) {

        PreparedStatement pstmt = null;
        try {

            String sql = "INSERT INTO parkings(name,address,capacity) VALUES (?,?,?); " ;
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, parking.getName());
            pstmt.setString(2, parking.getAddress());
            pstmt.setInt(3, parking.getCapacity());
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }  finally {
            try {
                if (pstmt != null) pstmt.close();

            } catch (SQLException se2) {

            }
        }

    }




}
