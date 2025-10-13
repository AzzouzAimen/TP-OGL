package test;

import com.example.dao.ReservationDao;
import com.example.entity.*;
import com.example.service.IParkingPlaceManager;
import com.example.service.ReservationManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Date;

import static org.mockito.Mockito.*;

class ReservationManagerTest {
    private ReservationManager reservationManager;

    @Mock
    private ReservationDao reservationDao;
    @Mock
    private IParkingPlaceManager parkingPlaceManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        // Create instance of ReservationManager
        reservationManager = new ReservationManager();
        // Inject the mocked dependencies
        reservationManager.setReservationDao(reservationDao);
        reservationManager.setiPlaceManager(parkingPlaceManager);
    }

    private Reservation createTestReservation() {
        Reservation reservation = new Reservation();
        
        // Create a parking place
        ParkingPlace parkingPlace = new ParkingPlace();
        parkingPlace.setIdPlace(1);
        parkingPlace.setPlaceName("A-101");
        parkingPlace.setPlaceStatus(PlaceStatus.AVAILABLE);
        
        // Create a user
        User user = new User();
        user.setUserId(1);
        user.setName("Test User");
        user.setEmail("test@example.com");
        
        // Set up the reservation
        reservation.setParkingPlace(parkingPlace);
        reservation.setUser(user);
        reservation.setStartTime(new Date());
        reservation.setEndTime(new Date(System.currentTimeMillis() + 3600000)); // 1 hour later
        reservation.setStatus(ReservationStatus.PENDING);
        
        return reservation;
    }

    // Tests for createReservation
    @Test
    void testCreateReservation_WhenPlaceIsAvailable_ShouldCreateReservation() {
        Reservation reservation = createTestReservation();
        int placeId = 1;
        Date startDate = reservation.getStartTime();
        Date endDate = reservation.getEndTime();
        // parking place available
        when(parkingPlaceManager.isAvailable(placeId, startDate, endDate)).thenReturn(true);
        reservationManager.createReservation(reservation);
        // Assert
        verify(parkingPlaceManager).isAvailable(placeId, startDate, endDate);
        verify(reservationDao).insertReservation(reservation);
        verify(parkingPlaceManager).updateStatus(placeId, PlaceStatus.RESERVED);
    }

    @Test
    void testCreateReservation_WhenPlaceIsNotAvailable_ShouldNotCreateReservation() {
        Reservation reservation = createTestReservation();
        int placeId = 1;
        Date startDate = reservation.getStartTime();
        Date endDate = reservation.getEndTime();
        // parking place NOT available
        when(parkingPlaceManager.isAvailable(placeId, startDate, endDate)).thenReturn(false);
        reservationManager.createReservation(reservation);
        // Assert
        verify(parkingPlaceManager).isAvailable(placeId, startDate, endDate);
        verify(reservationDao, never()).insertReservation(any());
        verify(parkingPlaceManager, never()).updateStatus(anyInt(), any());
    }

    // Tests for cancelReservation

    @Test
    void testCancelReservation_WhenStatusIsPending_ShouldCancelReservation() {
        // Arrange
        int reservationId = 100;
        Reservation reservation = createTestReservation();
        reservation.setReservationId(reservationId);
        reservation.setStatus(ReservationStatus.PENDING);
        // Mock the DAO to return this reservation
        when(reservationDao.getReservationById(reservationId)).thenReturn(reservation);

        reservationManager.cancelReservation(reservationId);

        // Assert
        verify(reservationDao).getReservationById(reservationId);
        verify(reservationDao).updateReservationStatus(reservationId, ReservationStatus.CANCELLED);
    }

    @Test
    void testCancelReservation_WhenStatusIsConfirmed_ShouldNotCancelReservation() {
        // Arrange
        int reservationId = 100;
        Reservation reservation = createTestReservation();
        reservation.setReservationId(reservationId);
        reservation.setStatus(ReservationStatus.CONFIRMED);

        // Mock the DAO to return a confirmed reservation
        when(reservationDao.getReservationById(reservationId)).thenReturn(reservation);

        // Act
        reservationManager.cancelReservation(reservationId);

        // Assert
        verify(reservationDao).getReservationById(reservationId);
        verify(reservationDao, never()).updateReservationStatus(anyInt(), any());
    }

    @Test
    void testCancelReservation_WhenStatusIsCancelled_ShouldNotCancelAgain() {
        // Arrange
        int reservationId = 100;
        Reservation reservation = createTestReservation();
        reservation.setReservationId(reservationId);
        reservation.setStatus(ReservationStatus.CANCELLED);

        // Mock the DAO to return an already cancelled reservation
        when(reservationDao.getReservationById(reservationId)).thenReturn(reservation);

        // Act
        reservationManager.cancelReservation(reservationId);

        // Assert
        verify(reservationDao).getReservationById(reservationId);
        verify(reservationDao, never()).updateReservationStatus(anyInt(), any());
    }

    // ========== Helper method to create test data ==========

    
}