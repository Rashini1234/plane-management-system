public class Person {
    // Variables to store person's name, surname and email
    private String name;
    private String surname;
    private String email;
    // Constructor to initialize Person object with provided name, surname and email
    public Person(String name, String surname, String email){
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
    // Getter and Setter methods
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getSurname(){
        return surname;
    }
    public void setSurname(String surname){
        this.surname=surname;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    // Method to print person's information
    public void printInfo(){
        System.out.println("Name: "+ name);
        System.out.println("Surname: "+ surname);
        System.out.println("Email: "+ email);
    }

}
