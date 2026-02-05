package menu;

import api.AdminResource;
import model.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class AdminMenu {

    private static final AdminResource adminResource = AdminResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void showAdminMenu() {
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back to Main Menu");

            switch (scanner.nextLine()) {
                case "1":
                    showCustomers();
                    break;
                case "2":
                    showRooms();
                    break;
                case "3":
                    adminResource.displayAllReservations();
                    break;
                case "4":
                    addRoomFlow();
                    break;
                case "5":
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // ================= CUSTOMERS =================
    private static void showCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        customers.forEach(System.out::println);
    }

    // ================= ROOMS =================
    private static void showRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }
        rooms.forEach(System.out::println);
    }

    // ================= ADD ROOM =================
    private static void addRoomFlow() {
        boolean addMore = true;

        while (addMore) {
            try {
                System.out.print("Enter room number: ");
                String roomNumber = scanner.nextLine().trim();

                if (roomNumber.isEmpty()) {
                    System.out.println("Room number cannot be empty.");
                    continue;
                }

                for (IRoom room : adminResource.getAllRooms()) {
                    if (room.getRoomNumber().equalsIgnoreCase(roomNumber)) {
                        System.out.println("Room already exists.");
                        roomNumber = null;
                        break;
                    }
                }
                if (roomNumber == null) continue;

                System.out.print("Enter price: ");
                double price = Double.parseDouble(scanner.nextLine());
                if (price < 0) {
                    System.out.println("Price cannot be negative.");
                    continue;
                }

                System.out.print("Room type (1 = Single, 2 = Double): ");
                String type = scanner.nextLine();

                RoomType roomType;
                if ("1".equals(type)) {
                    roomType = RoomType.SINGLE;
                } else if ("2".equals(type)) {
                    roomType = RoomType.DOUBLE;
                } else {
                    System.out.println("Invalid room type.");
                    continue;
                }

                IRoom room = price == 0
                        ? new FreeRoom(roomNumber, roomType)
                        : new Room(roomNumber, price, roomType);

                adminResource.addRoom(Collections.singletonList(room));
                System.out.println("Room added successfully.");

                System.out.print("Add another room? (y/n): ");
                addMore = scanner.nextLine().equalsIgnoreCase("y");

            } catch (Exception e) {
                System.out.println("Invalid input. Try again.");
            }
        }
    }
}
