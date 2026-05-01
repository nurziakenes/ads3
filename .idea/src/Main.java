import java.util.Random;

public class Main {

    public static void main(String[] args) {


        System.out.println("=== Part 1: MyHashTable ===\n");

        MyHashTable<MyTestingClass, Student> table = new MyHashTable<>(100);

        Random rnd = new Random(42);
        String[] firstNames = {"Alice", "Bob", "Carol", "Dave", "Eve",
                "Frank", "Grace", "Hank", "Iris", "Jack"};
        String[] lastNames  = {"Smith", "Jones", "Brown", "Davis", "Wilson",
                "Moore", "Taylor", "Anderson", "Thomas", "Jackson"};

        for (int i = 0; i < 10_000; i++) {
            String fn  = firstNames[rnd.nextInt(firstNames.length)];
            String ln  = lastNames [rnd.nextInt(lastNames.length)];
            int    age = 17 + rnd.nextInt(30);
            MyTestingClass key   = new MyTestingClass(i, fn + i);
            Student        value = new Student(fn, ln, age);
            table.put(key, value);
        }

        System.out.println("Total elements inserted: " + table.getSize());
        System.out.println("\nElements per bucket:");
        table.printBucketSizes();

        System.out.println("\n=== Part 2: BST ===\n");

        BST<Integer, String> tree = new BST<>();
        tree.put(5, "five");
        tree.put(3, "three");
        tree.put(7, "seven");
        tree.put(1, "one");
        tree.put(4, "four");
        tree.put(6, "six");
        tree.put(9, "nine");

        System.out.println("Size: " + tree.size());

        System.out.println("\nIn-order traversal (key -> value):");
        for (var elem : tree) {
            System.out.println("key is " + elem.getKey() + " and value is " + elem.getValue());
        }

        System.out.println("\nget(7): " + tree.get(7));
        System.out.println("get(99): " + tree.get(99));

        tree.delete(3);
        System.out.println("\nAfter deleting key 3, size: " + tree.size());
        System.out.println("In-order after delete:");
        for (var elem : tree) {
            System.out.println("key is " + elem.getKey() + " and value is " + elem.getValue());
        }
    }
}