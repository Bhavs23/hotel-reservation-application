package menu;

import api.HotelResource;
import model.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {

    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void showMainMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\nMain Menu");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");

            switch (scanner.nextLine()) {
                case "1": findAndReserve(); break;
                case "2": seeReservations(); break;
                case "3": createAccount(); break;
                case "4": AdminMenu.showAdminMenu(); break;
                case "5": running = false; break;
                default: System.out.println("Invalid input.");
            }
        }
    }

    // ================= CREATE ACCOUNT =================
    private static void createAccount() {
        try {
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            if (hotelResource.getCustomer(email) != null) {
                System.out.println("An account with this email already exists.");
                return;
            }

            System.out.print("First name: ");
            String first = scanner.nextLine().trim();

            System.out.print("Last name: ");
            String last = scanner.nextLine().trim();

            hotelResource.createACustomer(email, first, last);
            System.out.println("Account created successfully.");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // ================= FIND & RESERVE =================
    private static void findAndReserve() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            formatter.setLenient(false);

            System.out.print("Check-in (dd/MM/yyyy): ");
            Date checkIn = formatter.parse(scanner.nextLine());

            System.out.print("Check-out (dd/MM/yyyy): ");
            Date checkOut = formatter.parse(scanner.nextLine());

            Date today = new Date();

            if (checkIn.before(today)) {
                System.out.println("Check-in date cannot be in the past.");
                return;
            }

            if (!checkOut.after(checkIn)) {
                System.out.println("Check-out date must be after check-in date.");
                return;
            }

            long diff = checkOut.getTime() - checkIn.getTime();
            if (diff < 24 * 60 * 60 * 1000) {
                System.out.println("Reservation must be at least one night.");
                return;
            }

            Collection<IRoom> rooms = hotelResource.findARoom(checkIn, checkOut);

            // 🔁 NO ROOMS → RECOMMEND 7 DAYS LATER
            if (rooms.isEmpty()) {
                System.out.println("No rooms available for selected dates.");
                System.out.print("Would you like to search 7 days later? (y/n): ");

                if (!scanner.nextLine().equalsIgnoreCase("y")) {
                    return;
                }

                Calendar cal = Calendar.getInstance();
                cal.setTime(checkIn);
                cal.add(Calendar.DATE, 7);
                Date newCheckIn = cal.getTime();

                cal.setTime(checkOut);
                cal.add(Calendar.DATE, 7);
                Date newCheckOut = cal.getTime();

                rooms = hotelResource.findARoom(newCheckIn, newCheckOut);

                if (rooms.isEmpty()) {
                    System.out.println("No rooms available even after 7 days.");
                    return;
                }

                System.out.println("Available Rooms (7 days later):");
                rooms.forEach(System.out::println);

                handleBooking(newCheckIn, newCheckOut);
                return;
            }

            // NORMAL FLOW
            System.out.println("Available Rooms:");
            rooms.forEach(System.out::println);
            handleBooking(checkIn, checkOut);

        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    // ================= BOOKING HANDLER =================
    private static void handleBooking(Date checkIn, Date checkOut) {

        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter room number to reserve: ");
        String roomNumber = scanner.nextLine().trim();

        IRoom room = hotelResource.getRoom(roomNumber);
        if (room == null) {
            System.out.println("Invalid room number.");
            return;
        }

        Customer customer = hotelResource.getCustomer(email);

        if (customer == null) {
            System.out.println("Cannot make reservation. Please create an account.");
            System.out.print("Do you want to create an account now? (y/n): ");

            if (!scanner.nextLine().equalsIgnoreCase("y")) {
                return;
            }

            System.out.print("First name: ");
            String first = scanner.nextLine().trim();

            System.out.print("Last name: ");
            String last = scanner.nextLine().trim();

            hotelResource.createACustomer(email, first, last);
            System.out.println("Account created successfully.");

            System.out.print("Do you want to reserve the same room now? (y/n): ");
            if (!scanner.nextLine().equalsIgnoreCase("y")) {
                return;
            }
        }

        Reservation reservation =
                hotelResource.bookARoom(email, room, checkIn, checkOut);

        if (reservation != null) {
            System.out.println("Reservation successful!");
        }
    }

    // ================= SEE RESERVATIONS =================
    private static void seeReservations() {
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        Collection<Reservation> reservations =
                hotelResource.getCustomersReservations(email);

        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        reservations.forEach(System.out::println);
    }
}
