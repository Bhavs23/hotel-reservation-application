package api;

import model.*;
import service.*;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

    private static final HotelResource instance = new HotelResource();

    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();

    private HotelResource() {}

    public static HotelResource getInstance() {
        return instance;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    // ✅ Blocks booking without account
    public Reservation bookARoom(String customerEmail, IRoom room,
                                 Date checkInDate, Date checkOutDate) {

        Customer customer = customerService.getCustomer(customerEmail);
        if (customer == null) {
            System.out.println(
                    "No account found for this email. Please create an account first."
            );
            return null;
        }

        return reservationService.reserveARoom(
                customer, room, checkInDate, checkOutDate
        );
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = customerService.getCustomer(customerEmail);
        return reservationService.getCustomersReservation(customer);
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }
}
