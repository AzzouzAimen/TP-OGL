package test;

import com.example.dao.*;
import com.example.entity.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.*;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class ReservationDaoTest {
    static Connection conn;
    static DatabaseConnection db;
    static ReservationDao dao;
    static UserDao userDao;
    static ParkingDao parkingDao;
    static ParkingPlaceDao parkingPlaceDao;

    @BeforeEach
    void setUp() throws SQLException {
        db = new DatabaseConnection("sa", "", "org.h2.Driver", "jdbc:h2:mem:test");
        conn = db.connect();
        db.createDb(conn);
        
        // Initialize DAOs
        dao = new ReservationDao();
        dao.setConn(conn);
        userDao = new UserDao();
        userDao.setConn(conn);
        parkingDao = new ParkingDao();
        parkingDao.setConn(conn);
        parkingPlaceDao = new ParkingPlaceDao();
        parkingPlaceDao.setConn(conn);
    }

    @AfterEach
    void cleanup() {
        if (conn != null) {
            db.disconnect(conn);
        }
    }

    @Test
    void testInsertAndGetReservation() throws SQLException {

        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPhone("123456789");
        userDao.insertUser(user);
        user.setUserId(1);

        Parking parking = new Parking();
        parking.setName("Test Parking");
        parking.setAddress("Test Address");
        parking.setCapacity(100);
        parkingDao.insertParking(parking);
        parking.setParkingId(1); // Set the auto-generated ID

        ParkingPlace parkingPlace = new ParkingPlace();
        parkingPlace.setPlaceName("A-01");
        parkingPlace.setPlaceStatus(PlaceStatus.AVAILABLE);
        parkingPlace.setParking(parking);
        parkingPlaceDao.insertParkingPlace(parkingPlace);
        parkingPlace.setIdPlace(1); // Set the auto-generated ID
        
        //create the reservation
        Reservation res = new Reservation();
        res.setUser(user);
        res.setParkingPlace(parkingPlace);
        res.setStatus(ReservationStatus.CONFIRMED);
        Date startTime = new Date();
        Date endTime = new Date(System.currentTimeMillis() + 3600000); // 1 hour later
        res.setStartTime(startTime);
        res.setEndTime(endTime);

        dao.insertReservation(res);
        Reservation retrievedReservation = dao.getReservationById(1);

        // assert
        assertNotNull(retrievedReservation, "Retrieved reservation should not be null");
        assertEquals(1, retrievedReservation.getReservationId(), 
                    "Reservation ID should be auto-generated as 1");
        assertEquals(1, retrievedReservation.getUser().getUserId(), 
                    "User ID should match");
        assertEquals(1, retrievedReservation.getParkingPlace().getIdPlace(), 
                    "Parking place ID should match");
        assertEquals(ReservationStatus.CONFIRMED, retrievedReservation.getStatus(), 
                    "Reservation status should match");
    }


}