import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Ticket {
    // Variables to store ticket information
    private char row;
    private int seat;
    private double price;
    private Person person;

    // Constructor to initialize Ticket object with provided row, seat, price and person
    public Ticket(char row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    // Getter and Setter methods
    public char getRow() {
        return row;
    }
    public void setRow(char row) {
        this.row = row;
    }
    public int getSeat() {
        return seat;
    }
    public void setSeat(int seat) {
        this.seat = seat;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }

    // Method to print ticket information
    public void printInfo() {
        System.out.println("Row: " + row);
        System.out.println("Seat: " + seat);
        System.out.println("Price: £" + price);
        System.out.println("Person Info: ");
        person.printInfo();
    }

    // Method to save ticket information to a text file
    public void save() {
        String filename = row + Integer.toString(seat) + ".txt";
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Ticket Information: \n");
            writer.write("Row: " + row + "\n");
            writer.write("Seat: " + seat + "\n");
            writer.write("Price: " + price + "\n");
            writer.write("Person Information: \n");
            writer.write("Name: " + person.getName() + "\n");
            writer.write("Surname: " + person.getSurname() + "\n");
            writer.write("Email: " + person.getEmail() + "\n");
        } catch (IOException e) {
            System.out.println("Error occurred while saving ticket information to file.");
        }
    }

    // Method to delete the associated text file
    public void delete() {
        String filename = row + Integer.toString(seat) + ".txt";
        File deleteFile = new File(filename);
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
    }
}
