package com.example.dao;

import com.example.entity.Parking;
import com.example.entity.ParkingPlace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParkingPlaceDao {

    private Connection conn;


    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }



    public void insertParkingPlace(ParkingPlace parkingPlace) {

        PreparedStatement pstmt = null;

        try {
            String sql = "INSERT INTO parking_places(place_name,place_status,parking_id) VALUES (?,?,?); " ;
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, parkingPlace.getPlaceName());
            pstmt.setString(2, parkingPlace.getPlaceStatus().name());
            pstmt.setInt(3, parkingPlace.getParking().getParkingId());
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
