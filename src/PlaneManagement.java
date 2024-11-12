import java.util.InputMismatchException;
import java.util.Scanner;

public class PlaneManagement {
    public static Ticket[] soldTickets = new Ticket[52]; // Array to store sold tickets
    public static int ticketCount; // Counter to keep track of the sold tickets

    public static void main(String[] args){
        System.out.println("Welcome to Plane Management System!");

        // Initialize seating plan
        char [][] seats = new char[4][];
        seats[0] = new char[14];
        seats[1] = new char[12];
        seats[2] = new char[12];
        seats[3] = new char[14];

        // Set 'O's to indicate available seats
        for (char i = 0; i < seats.length; i++){
            for (char j = 0; j < seats[i].length; j++){
                seats[i][j] = 'O';
            }
        }

        Scanner scanner = new Scanner(System.in);

        // Display the menu
        while (true){
            System.out.println("*********************");
            System.out.println("*        Menu       *");
            System.out.println("1. Buy a seat");
            System.out.println("2. Cancel a seat");
            System.out.println("3. Find first available seat");
            System.out.println("4. Show seating plan");
            System.out.println("5. Print ticket information and total sales");
            System.out.println("6. Search ticket");
            System.out.println("0. Quit");
            System.out.println("*********************");

            System.out.print("Please select an option: "); // Get input from the user
            int option;
            try{
                option = scanner.nextInt(); // Read user input
            }catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear input
                continue;
            }

            // Do actions, based on the input given by the user
            switch (option){
                case 1:
                    buy_seat(scanner,seats);
                    break;
                case 2:
                    cancel_seat(scanner,seats);
                    break;
                case 3:
                    find_first_available(seats);
                    break;
                case 4:
                    show_seating_plan(seats);
                    break;
                case 5:
                    print_ticket_info();
                    break;
                case 6:
                    search_ticket(scanner, seats);
                    break;
                case 0:
                    return; // Exit the program
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    // Method to buy a seat
    public static void buy_seat(Scanner scanner, char[][] seats){
        try{
            System.out.println("Enter row letter: ");
            char row = scanner.next().toUpperCase().charAt(0);
            System.out.println("Enter seat number: ");
            int seatNumber = scanner.nextInt();

            // Check if the selected seat is valid and available
            if (validSeat(row, seatNumber, seats)){
                if (seats[row - 'A'][seatNumber - 1] == 'O'){
                    // Prompt user to get personal information
                    System.out.println("Enter name: ");
                    String name = scanner.next();
                    System.out.println("Enter surname: ");
                    String surname = scanner.next();
                    System.out.println("Enter email: ");
                    String email = scanner.next();

                    Person person = new Person(name, surname, email); // Create a new person object
                    double price = getPrice(row, seatNumber);
                    Ticket ticket = new Ticket (row, seatNumber, price, person); // Create a ticket object
                    ticket.save(); // Save the ticket information

                    //Update the soldTickets array and seating plan
                    soldTickets[ticketCount] = ticket;
                    ticketCount++;

                    seats[row - 'A'][seatNumber - 1] = 'X';
                    System.out.println("Seat booked successfully!");
                }else {
                    System.out.println("Seat is already booked. Please try again.");
                }
            }else{
                System.out.println("Invalid row or seat number. Please try again.");
            }
        }catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a correct row letter and seat number.");
            scanner.nextLine();
        }
    }

    // Method to cancel a seat
    public static void cancel_seat(Scanner scanner, char[][] seats){
        try {
            System.out.println("Enter row letter: ");
            char row = scanner.next().toUpperCase().charAt(0);
            System.out.println("Enter seat number: ");
            int seatNumber = scanner.nextInt();

            if (validSeat(row, seatNumber, seats)) {
                if (seats[row - 'A'][seatNumber - 1] == 'X') {
                    seats[row - 'A'][seatNumber - 1] = 'O';

                    // Find and remove the ticket from soldTickets array
                    for (int i = 0; i < ticketCount; i++) {
                        if (soldTickets[i].getRow() == row && soldTickets[i].getSeat() == seatNumber) {
                            removeTicket(i);
                            System.out.println("Seat cancelled successfully!");
                            return;
                        }
                    }
                    System.out.println("Ticket not found.");
                } else {
                    System.out.println("Seat is not booked");
                }
            } else {
                System.out.println("Invalid row or seat number. Please try again.");
            }
        }catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a correct row letter and seat number.");
            scanner.nextLine();
        }
    }

    // Method to find the first available seat
    public static void find_first_available(char[][] seats){
        for (char row = 'A'; row <= 'D'; row++){
            for (int seatNumber = 1; seatNumber <= seats[row - 'A'].length; seatNumber++){
                if (seats[row - 'A'][seatNumber - 1] =='O'){
                    System.out.println("First available seat: "+ row+seatNumber);
                    return;
                }
            }
        }
        System.out.println("No available seat found.");
    }
    // Method to show the seating plan
    public static void show_seating_plan(char[][] seats){
        for (char row = 'A'; row <= 'D'; row++){
            for (int seatNumber = 1; seatNumber <= seats[row - 'A'].length; seatNumber++){
                if (seats[row - 'A'][seatNumber - 1] == 'O'){
                    System.out.print("O  ");
                }else{
                    System.out.print("X  ");
                }
            }
            System.out.println();
        }
    }

    //Method to print ticket information
    public static void print_ticket_info(){
        double totalSales = 0;
        System.out.println("Tickets Information: ");
        for (int i = 0; i < ticketCount; i++){
            Ticket ticket = soldTickets[i];
            if (ticket != null){
                System.out.println("Ticket "+(i+1)+":");
                ticket.printInfo();
                System.out.println();
                totalSales += ticket.getPrice();
            }
        }
        System.out.println("Total Sales: £" +totalSales);
    }
    // Method to search a specific ticket
    public static void search_ticket(Scanner scanner, char[][] seats){
        try{
            System.out.println("Enter row letter: ");
            char row = scanner.next().toUpperCase().charAt(0);
            System.out.println("Enter seat number: ");
            int seatNumber = scanner.nextInt();

            if (validSeat(row, seatNumber, seats)) {
                if (seats[row - 'A'][seatNumber - 1] == 'X') {
                    for (Ticket ticket: soldTickets){
                        if (ticket != null && ticket.getRow() == row && ticket.getSeat() == seatNumber){
                            System.out.println("Seat is already booked!\nTicket Information: ");
                            ticket.printInfo();
                            return;
                        }
                    }
                } else {
                    System.out.println("This seat is available.");
                }
            }else {
                System.out.println("Invalid row or seat number. Please enter a correct row letter and seat number.");
            }
        }catch(InputMismatchException e){
            System.out.println("Invalid row or seat number. Please enter a correct row letter and seat number.");
            scanner.nextLine();
        }
    }
    // Method to find the seat is valid or not
    public static boolean validSeat(char row, int seatNumber, char[][] seats){
        if (row < 'A' || row > 'D' || seatNumber < 1 || seatNumber > seats[row - 'A'].length){
            return false;
        }
        return true;
    }
    // Method to remove a ticket from soldTickets array
    public static void removeTicket(int index){
        soldTickets[index].delete();// Delete the associated text file

        for (int i = index; i< ticketCount - 1; i++){
            soldTickets[i] = soldTickets [i + 1];
        }
        ticketCount--;
    }
    // Method to get the seat prices
    public static int getPrice(char row, int seatNumber){
        if (seatNumber >= 1 && seatNumber <= 5){
            return 200;
        } else if (seatNumber >= 6 && seatNumber <= 9) {
            return 150;
        } else if ((row == 'A' || row == 'D') && seatNumber >= 10 && seatNumber <= 14) {
            return 180;
        } else if ((row == 'B' || row == 'C') && seatNumber >= 10 && seatNumber <= 12) {
            return 180;
        } else {
            return 0;
        }
    }
}
