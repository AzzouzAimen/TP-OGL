package test;

import com.example.dao.DatabaseConnection;
import com.example.dao.ParkingDao;
import com.example.dao.ParkingPlaceDao;
import com.example.dao.ReservationDao;
import com.example.dao.UserDao;
import com.example.entity.Parking;
import com.example.entity.ParkingPlace;
import com.example.entity.PlaceStatus;
import com.example.entity.Reservation;
import com.example.entity.ReservationStatus;
import com.example.entity.User;
import com.example.service.ReservationManager;
import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simplified integration tests for learning purposes.
 *
 * This test uses DAO helper functions to interact with the database:
 * - We create an in-memory H2 database using DatabaseConnection helper.
 * - We insert reservations using DAO insertReservation method.
 * - We call the real `ReservationManager.cancelReservation` method (wired to a DAO using the same connection).
 * - We use DAO getReservationById to verify the resulting status.
 *
 * This is not production-grade test code, but it's easier to read for learning integration test basics.
 */
class CancelReservationIntegrationTest {

    private Connection conn;
    private DatabaseConnection db;
    private ReservationDao reservationDao;
    private UserDao userDao;
    private ParkingDao parkingDao;
    private ParkingPlaceDao parkingPlaceDao;
    private ReservationManager reservationManager;

    @BeforeEach
    void setUp() throws SQLException {
        // 1) Start an in-memory H2 database using DatabaseConnection helper
        db = new DatabaseConnection("sa", "", "org.h2.Driver", "jdbc:h2:mem:testdb");
        conn = db.connect();
        db.createDb(conn);

        // 2) Initialize all DAOs and wire the same connection
        reservationDao = new ReservationDao();
        reservationDao.setConn(conn);
        userDao = new UserDao();
        userDao.setConn(conn);
        parkingDao = new ParkingDao();
        parkingDao.setConn(conn);
        parkingPlaceDao = new ParkingPlaceDao();
        parkingPlaceDao.setConn(conn);

        // 3) Create test data: User, Parking, and ParkingPlace
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPhone("123456789");
        userDao.insertUser(user);

        Parking parking = new Parking();
        parking.setName("Test Parking");
        parking.setAddress("Test Address");
        parking.setCapacity(100);
        parkingDao.insertParking(parking);

        ParkingPlace parkingPlace = new ParkingPlace();
        parkingPlace.setPlaceName("A-01");
        parkingPlace.setPlaceStatus(PlaceStatus.AVAILABLE);
        parking.setParkingId(1); // Set the auto-generated ID
        parkingPlace.setParking(parking);
        parkingPlaceDao.insertParkingPlace(parkingPlace);

        // 4) Create and wire ReservationManager
        reservationManager = new ReservationManager();
        reservationManager.setReservationDao(reservationDao);
    }

    @AfterEach
    void tearDown() {
        // Close the connection to keep test isolation
        if (conn != null) {
            db.disconnect(conn);
        }
    }

    // Helper: insert a reservation using DAO helper
    private void insertReservationRow(ReservationStatus status) {
        // Build a Reservation object with required fields
        Reservation reservation = new Reservation();
        
        // Create User reference with the auto-generated ID
        User user = new User();
        user.setUserId(1);
        
        // Create ParkingPlace reference with the auto-generated ID
        ParkingPlace place = new ParkingPlace();
        place.setIdPlace(1);
        
        reservation.setParkingPlace(place);
        reservation.setUser(user);
        reservation.setStatus(status);
        Date now = new Date();
        reservation.setStartTime(now);
        reservation.setEndTime(now);

        // Use DAO helper to insert
        reservationDao.insertReservation(reservation);
    }

    // Helper: read status using DAO helper
    private String readStatus(int id) {
        Reservation reservation = reservationDao.getReservationById(id);
        return reservation != null ? reservation.getStatus().name() : null;
    }

    @Test
    void cancel_reservation_changes_status_when_not_confirmed() {
        // Arrange: insert a PENDING reservation using DAO helper
        insertReservationRow(ReservationStatus.PENDING);
        int id = 1; // First auto-generated ID

        // Act: call the method under test (uses ReservationDao wired to the same connection)
        reservationManager.cancelReservation(id);

        // Assert: the DB row's status should now be CANCELLED using DAO helper
        assertEquals(ReservationStatus.CANCELLED.name(), readStatus(id));
    }

    @Test
    void cancel_reservation_does_not_change_status_when_confirmed() {
        // Arrange: insert a CONFIRMED reservation using DAO helper
        insertReservationRow(ReservationStatus.CONFIRMED);
        int id = 1; // First auto-generated ID

        // Act
        reservationManager.cancelReservation(id);

        // Assert: status remains CONFIRMED using DAO helper
        assertEquals(ReservationStatus.CONFIRMED.name(), readStatus(id));
    }
}
