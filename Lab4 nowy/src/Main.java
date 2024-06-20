import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Zakłada, że plik jest w katalogu resources i podany jest z odpowiednią ścieżką
        List<Person> people = Person.fromCsv("C:\\Users\\Oleki\\OneDrive\\Object Oriented Programming II semestr\\Lab4 nowy\\src\\family.csv");

        for (Person person : people)
        {
            System.out.println(person.toString());
        }
    }
}
