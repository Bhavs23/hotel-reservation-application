package api;

import model.*;
import service.*;

import java.util.Collection;

public class AdminResource {

    private static final AdminResource instance = new AdminResource();

    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();

    private AdminResource() {}

    public static AdminResource getInstance() {
        return instance;
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public void addRoom(Collection<IRoom> rooms) {
        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }
    }

    public void displayAllReservations() {
        reservationService.printAllReservation();
    }
}
