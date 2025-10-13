package com.example.service;

import com.example.dao.ReservationDao;
import com.example.entity.PlaceStatus;
import com.example.entity.Reservation;
import com.example.entity.ReservationStatus;
import java.util.Date;



public class ReservationManager {

    private ReservationDao reservationDao;

    private IParkingPlaceManager iPlaceManager ;



    public void setReservationDao(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }



    public void setiPlaceManager(IParkingPlaceManager iPlaceManager) {
        this.iPlaceManager = iPlaceManager;
    }


    public void createReservation(Reservation reservation) {

            int idPlace = reservation.getParkingPlace().getIdPlace();
            Date startDate = reservation.getStartTime() ;
            Date endTime = reservation.getEndTime();
            boolean isAvailable = iPlaceManager.isAvailable(idPlace,startDate,endTime);
            if(isAvailable) {
                reservationDao.insertReservation(reservation);
                iPlaceManager.updateStatus(idPlace, PlaceStatus.RESERVED);
            }

        }


    public void cancelReservation(int idReservation) {
        Reservation reservation = reservationDao.getReservationById(idReservation);
        if (reservation.getStatus()!=ReservationStatus.CONFIRMED) {
            reservationDao.updateReservationStatus( idReservation,ReservationStatus.CANCELLED);
        }

    }












}
