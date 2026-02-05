package model;

import java.util.Date;

public class Reservation {

    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        return customer +
                " | Room: " + room.getRoomNumber() +
                " | From: " + checkInDate +
                " | To: " + checkOutDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Reservation)) return false;
        Reservation other = (Reservation) obj;
        return room.getRoomNumber().equals(other.room.getRoomNumber()) &&
                checkInDate.equals(other.checkInDate) &&
                checkOutDate.equals(other.checkOutDate);
    }

    @Override
    public int hashCode() {
        return room.getRoomNumber().hashCode();
    }
}

