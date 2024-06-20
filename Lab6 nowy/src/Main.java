import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Main
{
    public static void main(String[] args)
    {
        Integer lowerBound = 0;
        Integer upperBound = 10;

        CustomList<Integer> list = new CustomList<>();
        list.addLast(3);
        list.addLast(7);
        list.addLast(-5);
        list.add(10);

        System.out.println("idx[2]: " + list.get(2));

        System.out.println("\nIterator:");
        Iterator<Integer> iter = list.iterator();
        while (iter.hasNext())
        {
            System.out.println(iter.next());
        }
        System.out.println("\nStrumień (elementy + 5):");
        Stream<Integer> stream = list.stream();
        stream.map(element -> element + 5).forEach(System.out::println);

        Predicate<Integer> inRangePredicate = CustomList.isInRange(lowerBound, upperBound);
        long count = CustomList.countElementsInRange(list, lowerBound, upperBound, inRangePredicate);
        System.out.println("\nLiczba elementów w przedziale (" + lowerBound + ", " + upperBound + "): " + count);
    }
}