# Hotel Reservation Application

## Overview
The Hotel Reservation Application is a console-based hotel management system developed as part of the Udacity Java Fundamentals course. This application provides a complete booking system for managing hotel rooms, customer accounts, and reservations through an interactive command-line interface.

## Problem Statement
Design and implement a hotel reservation system that allows administrators to manage rooms and customers, while providing customers with the ability to search for available rooms, create accounts, and make reservations. The system demonstrates core Java programming concepts including object-oriented design, data structures, and user interaction.

## Key Features

### Customer Features
- Create and manage customer accounts
- Search for available rooms by date range
- Make reservations
- View existing reservations
- Find alternative date suggestions if rooms unavailable

### Admin Features
- Add and manage rooms
- View all customers
- View all rooms
- View all reservations
- Manage both paid and free rooms

### Technical Features
- Email validation using regex patterns
- Date handling and validation
- Room availability checking
- Unique constraint enforcement (no duplicate customers/rooms)
- Singleton pattern for service management
- Clean separation of concerns (Model-View-Controller pattern)

## Technologies Used
- Java SE 11
- Collections Framework (HashMap, List)
- Date and Calendar API
- Regular Expressions
- Object-Oriented Programming

## Project Structure

```
hotel-reservation-application/
│
├── README.md
├── .gitignore
│
├── documentation/
│   ├── Hotel Reservation Application Project Documentation.docx
│   └── images/
│       ├── code-testing.png
│       ├── customer-class.png
│       ├── freeroom-class.png
│       ├── iroom-interface.png
│       ├── iroom-polymorphism.png
│       ├── model-package-structure.png
│       ├── output1.png
│       ├── output2.png
│       ├── output3.png
│       ├── output4.png
│       ├── output5.png
│       ├── output6.png
│       ├── output7.png
│       ├── output8.png
│       ├── reservation-class.png
│       ├── room-class.png
│       ├── roomtype-enum-class.png
│       ├── roomtype-enum-creation.png
│       └── roomtype-enum-implementation.png
│
└── src/
    ├── HotelApplication.java          # Main application entry point
    │
    ├── api/                            # API layer
    │   ├── AdminResource.java          # Admin functionality API
    │   └── HotelResource.java          # Customer functionality API
    │
    ├── model/                          # Data models
    │   ├── Customer.java               # Customer entity
    │   ├── FreeRoom.java               # Free room implementation
    │   ├── IRoom.java                  # Room interface
    │   ├── Reservation.java            # Reservation entity
    │   ├── Room.java                   # Room entity
    │   └── RoomType.java               # Room type enumeration
    │
    ├── menu/                           # User interface
    │   ├── AdminMenu.java              # Admin menu interface
    │   └── MainMenu.java               # Main menu interface
    │
    └── service/                        # Business logic
        ├── CustomerService.java        # Customer management service
        └── ReservationService.java     # Reservation management service
```

## Architecture

### Design Patterns
- **Singleton Pattern**: Service classes ensure single instance
- **Factory Pattern**: Room creation (Room vs FreeRoom)
- **MVC Pattern**: Separation of model, view, and controller logic

### Layers
1. **Model Layer**: Defines data entities (Customer, Room, Reservation)
2. **Service Layer**: Contains business logic and data management
3. **API Layer**: Provides interface for menu interactions
4. **Menu Layer**: Handles user input/output

## How to Run

### Prerequisites
- Java JDK 11 or higher
- Command line terminal

### Steps

1. Clone the repository
```bash
git clone https://github.com/Bhavs23/hotel-reservation-application.git
cd hotel-reservation-application
```

2. Compile the project
```bash
javac -d out src/**/*.java src/*.java
```

3. Run the application
```bash
java -cp out HotelApplication
```

### Usage

#### Main Menu Options
1. Find and reserve a room
2. See my reservations
3. Create an account
4. Admin
5. Exit

#### Admin Menu Options
1. See all customers
2. See all rooms
3. See all reservations
4. Add a room
5. Back to main menu

## Code Highlights

### Email Validation
Uses regex pattern to validate customer email format:
```java
^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$
```

### Room Management
- Supports both paid and free room types
- Room number uniqueness enforcement
- Type-safe room type enumeration (SINGLE, DOUBLE)

### Reservation System
- Date-based room availability checking
- Alternative date suggestion (7-day flexibility)
- Prevents double booking

## Learning Outcomes
- Applied object-oriented programming principles
- Implemented design patterns (Singleton, Factory, MVC)
- Used Java Collections Framework effectively
- Handled date/time operations
- Validated user input with regex
- Created interactive console applications
- Managed data persistence in memory

## Documentation
Detailed project documentation with implementation notes is available in:
- `documentation/Hotel Reservation Application Project Documentation.docx`

## Author
Bhavika Vunnam  

## Project Context
This project was completed as part of the Udacity Java for ATCI course, demonstrating mastery of:
- Java syntax and fundamentals
- Object-oriented design
- Data structure usage
- Application architecture
- Clean code practices
