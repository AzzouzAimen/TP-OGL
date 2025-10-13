package com.example.dao;

import com.example.entity.ParkingPlace;
import com.example.entity.Reservation;
import com.example.entity.ReservationStatus;
import com.example.entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ReservationDao {

    private Connection conn;


    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }




        public void insertReservation(Reservation reservation) {

            PreparedStatement pstmt = null;

            try {
                String sql = "INSERT INTO reservations (place_id,user_id,status,start_time,end_time) VALUES (?,?,?,?,?); " ;
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, reservation.getParkingPlace().getIdPlace());
                pstmt.setInt(2, reservation.getUser().getUserId());
                pstmt.setString(3, reservation.getStatus().name());
                java.sql.Date startTime = new java.sql.Date(reservation.getStartTime().getTime());
                java.sql.Date endTime = new java.sql.Date(reservation.getEndTime().getTime());
                pstmt.setDate(4, startTime);
                pstmt.setDate(5,endTime);
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


        public Reservation getReservationById(int idReservation) {
            PreparedStatement pstmt = null;
            Reservation reservation = null;
            try {
                String sql = "SELECT * FROM reservations WHERE id_reservation = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,idReservation);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    reservation = new Reservation();
                    reservation.setReservationId(rs.getInt("id_reservation"));
                    ParkingPlace parkingPlace = new ParkingPlace();
                    parkingPlace.setIdPlace(rs.getInt("place_id"));
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    reservation.setParkingPlace(parkingPlace);
                    reservation.setUser(user);
                    reservation.setStatus(ReservationStatus.valueOf(rs.getString("status")));
                    reservation.setStartTime(rs.getDate("start_time"));
                    reservation.setEndTime(rs.getDate("end_time"));
                }


            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pstmt != null) pstmt.close();
                } catch (SQLException se2) {
                }
            }
            return reservation;
        }


    public void updateReservationStatus(int idReservation, ReservationStatus reservationStatus) {
        PreparedStatement pstmt = null;
        try {
            String sql = "UPDATE reservations SET status=? WHERE id_reservation=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,reservationStatus.name());
            pstmt.setInt(2,idReservation);
            pstmt.executeUpdate();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException se2) {
            }
        }

    }



}

