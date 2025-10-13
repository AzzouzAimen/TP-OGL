package com.example.service;

import com.example.entity.PlaceStatus;


import java.util.Date;

public interface IParkingPlaceManager {

    public boolean isAvailable(int idPlace, Date startTime, Date endTime);

    public void updateStatus(int idPlace, PlaceStatus placeStatus);
}
