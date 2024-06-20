import java.util.*;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.function.Predicate;
import java.util.Collection;



public class CustomList<T> extends AbstractList<T>
{
    private Node<T> head;
    private Node<T> tail;
    int length = 0;

    private static class Node<T>
    {
        T value;
        Node<T> next;

        Node(T value)
        {
            this.value = value;
            this.next = null;
        }
    }

    public CustomList()
    {
        this.head = null;
        this.tail = null;
        this.length = 0;
    }

    public void addLast(T value)
    {
        Node<T> newNode = new Node<>(value);
        if (tail == null)
        {
            head = tail = newNode;
        }
        else
        {
            tail.next = newNode;
            tail = newNode;
        }
        length++;
    }

    public T getLast()
    {
        if (tail == null)
        {
           return null;
        }
        return tail.value;
    }

    public T getFirst()
    {
        if (head == null)
        {
            return null;
        }
        return head.value;
    }

    public void addFirst(T value)
    {
        Node<T> newNode = new Node<>(value);
        if (head == null)
        {
            head = tail = newNode;
        }
        else
        {
            newNode.next = head;
            head = newNode;
        }
        length++;
    }

    public T removeFirst()
    {
        if (head == null)
        {
            return null;
        }
        T value = head.value;
        head = head.next;
        if (head == null)
        {
            tail = null;
        }
        length--;
        return value;
    }

    public T removeLast()
    {
        if (tail == null)
        {
            return null;
        }
        if (head == tail)
        {
            T value = tail.value;
            head = tail = null;
            length--;
            return value;
        }

        Node<T> current = head;
        while (current.next != tail)
        {
            current = current.next;
        }
        T value = tail.value;
        tail = current;
        tail.next = null;
        length--;
        return value;
    }

    @Override
    public T get(int index)
    {
        if (index < 0 || index >= length)
        {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++)
        {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public int size()
    {
        return length;
    }

    @Override
    public boolean add(T t)
    {
        Node<T> newNode = new Node<>(t);
        if (tail == null)
        {
            head = tail = newNode;
        }
        else
        {
            tail.next = newNode;
            tail = newNode;
        }
        length++;

        return true;
    }

    @Override
    public Iterator<T> iterator()
    {
        return new Iterator<T>()
        {
            private Node<T> current = head;

            @Override
            public boolean hasNext()
            {
                return current != null;
            }

            @Override
            public T next()
            {
                if (!hasNext())
                {
                    throw new NoSuchElementException();
                }
                T value = current.value;
                current = current.next;
                return value;
            }
        };
    }
    public static <T> long countElementsInRange(CustomList<T> list, T lowerBound, T upperBound, Predicate<T> predicate)
    {
        long count = 0;
        for (T item : list)
        {
            if (predicate.test(item))
            {
                count++;
            }
        }
        return count;
    }
    @Override
    public Stream<T> stream()
    {
        Spliterator<T> spliterator = Spliterators.spliterator(iterator(), size(), 0);
        return StreamSupport.stream(spliterator, false);
    }

    public static <T> List<T> filterByClass(List<T> list, Class<T> clazz)
    {
        List<T> result = new ArrayList<>();
        for (T item : list)
        {
            if (clazz.isInstance(item))
            {
                result.add(item);
            }
        }
        return result;
    }

    public static <T extends Comparable<T>> Predicate<T> isInRange(T lowerBound, T upperBound) //5
    {
        return value -> value.compareTo(lowerBound) > 0 && value.compareTo(upperBound) < 0;
    }

    public static Comparator<CustomList<? extends Number>> compareBySum()
    {
        return Comparator.comparingDouble(customList -> customList.stream().mapToDouble(Number::doubleValue).sum());
    }
}