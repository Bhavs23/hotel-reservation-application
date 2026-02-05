package service;

import model.*;

import java.util.*;

public class ReservationService {

    private static final ReservationService instance = new ReservationService();

    private final Set<IRoom> rooms = new HashSet<>();
    private final Set<Reservation> reservations = new HashSet<>();

    private ReservationService() {}

    public static ReservationService getInstance() {
        return instance;
    }

    public void addRoom(IRoom room) {
        rooms.add(room);
    }

    public IRoom getARoom(String roomId) {
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomId)) {
                return room;
            }
        }
        return null;
    }

    // Prevent double booking
    public Reservation reserveARoom(Customer customer, IRoom room,
                                    Date checkInDate, Date checkOutDate) {

        if (customer == null || room == null) return null;

        for (Reservation res : reservations) {
            if (res.getRoom().equals(room) &&
                    !(checkOutDate.before(res.getCheckInDate()) ||
                            checkInDate.after(res.getCheckOutDate()))) {

                System.out.println("This room is not available for the selected dates.");
                return null;
            }
        }

        Reservation reservation =
                new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRooms = new ArrayList<>();

        for (IRoom room : rooms) {
            boolean available = true;

            for (Reservation res : reservations) {
                if (res.getRoom().equals(room) &&
                        !(checkOutDate.before(res.getCheckInDate()) ||
                                checkInDate.after(res.getCheckOutDate()))) {
                    available = false;
                    break;
                }
            }

            if (available) availableRooms.add(room);
        }
        return availableRooms;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        List<Reservation> result = new ArrayList<>();
        if (customer == null) return result;

        for (Reservation res : reservations) {
            if (res.getCustomer().equals(customer)) {
                result.add(res);
            }
        }
        return result;
    }

    // Admin must see ALL rooms
    public Collection<IRoom> getAllRooms() {
        return rooms;
    }

    public void printAllReservation() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }
        reservations.forEach(System.out::println);
    }
}
