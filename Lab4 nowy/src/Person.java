import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Person
{
    String name;
    LocalDate birthDate;
    LocalDate deathDate;
    private List<Person> parents = new ArrayList<>();

    public Person(String name, LocalDate birthDate, LocalDate deathDate)
    {
        this.name = name;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }

    public static void fromCsvLine(String line, List<Person> people)
    {
        try
        {
            String[] parts = line.split(",");
            int numOfParts = parts.length;

            String fullName = null;
            LocalDate birthDate = null;
            LocalDate deathDate = null;
            String fatherName = null;
            String motherName = null;

            if (numOfParts > 0)
            {
                fullName = parts[0].trim();
            }
            if (numOfParts > 1)
            {
                String dob = parts[1].trim();
                birthDate = LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            }
            if (numOfParts > 2 && !parts[2].trim().isEmpty())
            {
                String dod = parts[2].trim();
                deathDate = LocalDate.parse(dod, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            }
            if (numOfParts > 3 && !parts[3].trim().isEmpty())
            {
                fatherName = parts[3].trim();
            }
            if (numOfParts > 4 && !parts[4].trim().isEmpty())
            {
                motherName = parts[4].trim();
            }

            if (deathDate != null && birthDate != null && deathDate.isBefore(birthDate))
            {
                throw new NegativeLifespanException(fullName);
            }

            if (findPersonByName(people, fullName) != null)
            {
                throw new AmbiguousPersonException(fullName);
            }

            Person person = new Person(fullName, birthDate, deathDate);
            people.add(person);



            if (fatherName != null)
            {
                Person father = findPersonByName(people, fatherName);
                if (father != null)
                {
                    checkParentingAge(father, birthDate);
                    person.parents.add(father);
                }
                else
                {
                    person.parents.add(new Person(fatherName, null, null));
                }
            }
            else
            {
                person.parents.add(null);
            }

            if (motherName != null)
            {
                Person mother = findPersonByName(people, motherName);
                if (mother != null)
                {
                    checkParentingAge(mother, birthDate);
                    person.parents.add(mother);
                }
                else
                {
                    person.parents.add(new Person(motherName, null, null));
                }
            }
            else
            {
                person.parents.add(null);
            }
            people.add(person);

        } catch (ParentingAgeException e)
        {
            System.out.println(e.getMessage() + " Potwierdź lub odrzuć (Y/N): ");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine();
            if (!response.equalsIgnoreCase("Y"))
            {
                return;
            }
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    private static void checkParentingAge(Person parent, LocalDate childBirthDate) throws ParentingAgeException
    {
        if (parent.birthDate != null)
        {
            if (parent.birthDate.isAfter(childBirthDate.minusYears(15)))
            {
                throw new ParentingAgeException("Rodzic " + parent.name + " miał mniej niż 15 lat gdy dziecko sie urodziło.");
            }
            if (parent.deathDate != null && parent.deathDate.isBefore(childBirthDate))
            {
                throw new ParentingAgeException("Rodzic " + parent.name + " umarł przed narodzinami dziecka.");
            }
        }
    }

    private static Person findPersonByName(List<Person> people, String name)
    {
        for (Person person : people)
        {
            if (person.name.equals(name))
            {
                return person;
            }
            else return null;
        }
        return null;
    }

    public static List<Person> fromCsv(String filePath)
    {
        List<Person> people = new ArrayList<>();

        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null)
            {
                fromCsvLine(line, people);
            }

            for (Person person : people)
            {
                if (person.parents.get(0) != null && person.parents.get(0).birthDate == null)
                {
                    Person actualFather = findPersonByName(people, person.parents.get(0).name);
                    if (actualFather != null)
                    {
                        person.parents.set(0, actualFather);
                    }
                }
                if (person.parents.get(1) != null && person.parents.get(1).birthDate == null)
                {
                    Person actualMother = findPersonByName(people, person.parents.get(1).name);
                    if (actualMother != null)
                    {
                        person.parents.set(1, actualMother);
                    }
                }
            }
        } catch (IOException e)
        {
            System.out.println("Problem z odczytem: " + e.getMessage());
        }

        return people;
    }

    @Override
    public String toString()
    {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", deathDate=" + deathDate +
                ", mother=" + (parents.get(1) != null ? parents.get(1).name : "null") +
                ", father=" + (parents.get(0) != null ? parents.get(0).name : "null") +
                '}';
    }

    public static void toBinaryFile(String filePath, List<Person> people) throws IOException
    {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
        oos.writeObject(people);
        oos.close();
    }

    public static List<Person> fromBinaryFile(String filePath) throws IOException, ClassNotFoundException
    {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
        List<Person> people = (List<Person>) ois.readObject();
        ois.close();

        return people;
    }
}
