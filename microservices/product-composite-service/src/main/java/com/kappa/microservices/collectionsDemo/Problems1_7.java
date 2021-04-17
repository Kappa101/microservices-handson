package com.kappa.microservices.collectionsDemo;


import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Problems1_7 {

    /*1. sort an Array*/
    private void sortArray01() {
//        java provides built-in sorting capabilities for arrays consisting of primitives as well as objects
        int[] ints = new int[]{2, 5, 3, 6, 7};
        Arrays.sort(ints);

        //descending order
        Arrays.stream(ints)
                .boxed()
                .sorted(Collections.reverseOrder())
                .mapToInt(Integer::intValue)
                .toArray();

//        Arrays.sort(ints, Collections.reverseOrder());

        // *** bubble sort best: O(n) worst: O(n^2) ***
        int l = ints.length;
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l - i - 1; j++) {

                if (ints[j] > ints[j + 1]) {
                    int temp = ints[j];
                    ints[j] = ints[j + 1];
                    ints[j + 1] = temp;
                }
            }
        }

        // note: there is also a version of bubble sort with while loop which has performance benefit of 2 secs for 100_000 integers
        // to use bubble sort on an Object , you need to implement a comparator on it


//        **** Insertion sort [starts from second element and compares with previous one ]  best and worst case : O(n)
        for (int i = 1; i < l; i++) {
            int key = ints[i];
            int j = i - 1;

            while (j >= 0 && ints[j] > key) {
                ints[j + 1] = ints[j];
                j = j - 1;
            }

            ints[j + 1] = key;
        }

        // This can be fast for small and mostly sorted arrays. Also, it performs well when adding new elements to an array. It is also very memory-efficient since a single element is moved around


//        **** counting sort
//        The counting sort flow starts by calculating the minimum and the maximum element in the array. Based on the computed minimum and maximum, the algorithm defines a new array that will be used to count the unsorted elements by using the element as the index. Furthermore, this new array is modified in such a way that each element at each index stores the sum of previous counts. Finally, the sorted array is obtained from this new array.
//        best case & worst case : O(n+k)   | k is the number of possible values in the range. n is the number of elements to be sorted


//        **** Heap sort

    }

    //  ******   for objects
    class Person {

        private final String name;
        private final Integer height;

        Person(String name, Integer height) {
            this.name = name;
            this.height = height;
        }

        // getters,setters setc...
        @Override
        public String toString() {
            return name + "(" + height + "cms)";
        }

        public String getName() {
            return name;
        }

        public Integer getHeight() {
            return height;
        }
    }

    void sortDemo02() {
        Person[] sortDemos = {
                new Person("kappa", 171),
                new Person("chappa", 162),
                new Person("tapppa", 170),
                new Person("lappa", 171)
        };

        // 1
        Arrays.sort(sortDemos, Comparator.comparingInt(Person::getHeight));
        // 2
        Arrays.sort(sortDemos, (person1, person2) ->
                Integer.compare(person1.height, person1.height));
    }


    /*2. Finding an element in an array:*/
    void findElement() {
        int[] numbers = {4, 5, 1, 3, 7, 4, 1};
        int find = 3;
        for (int elem : numbers) {
            if (elem == find) {
                System.out.println("Found");
            }
        }

        // using BinarySearch
        Arrays.sort(numbers); // clone the array if you do not want to sort the array
        String flag = Arrays.binarySearch(numbers, find) >= 0 ? "true" : "false";


        //using streams
        Arrays.stream(numbers)
                .anyMatch(isPresent -> isPresent == find);


        // Search In objects [use Person class above]
        Person[] persons = {
                new Person("kappa", 171),
                new Person("chappa", 162),
                new Person("tapppa", 170),
                new Person("lappa", 171)
        };

        Person findPerson = new Person("kappa", 171);
        Arrays.asList(persons)
                .contains(findPerson);


        // you can use binarySearch also
        Comparator<Person> c = Comparator.comparing(Person::getHeight);

        Arrays.binarySearch(persons, findPerson, c);

        //*** check the first index of an element in an array
        IntStream.range(0, numbers.length)
                .filter(i -> find == numbers[i])
                .findFirst()
                .orElse(-1);

        // for objects_1
        Arrays.asList(persons)
                .indexOf(findPerson);

        // _2 you canuse comparator


    }


    /*3. Checking whether two arrays are equal or mismatches*/
    void checkArrayEquals() {
        //Two arrays of primitives are equal if they contain the same number of elements, and all corresponding pairs of elements in the two arrays are equal.
        int[] integers1 = {3, 4, 5, 6, 1, 5};
        int[] integers2 = {3, 4, 5, 6, 1, 5};
        int[] integers3 = {3, 4, 5, 6, 1, 3};

        boolean i12 = Arrays.equals(integers1, integers2); // true
        boolean i13 = Arrays.equals(integers1, integers3); // false


        // using objects
        Person[] person1 = {new Person("kappa", 171)};
        Person[] person2 = {new Person("kappa", 171)};
        Person[] person3 = {new Person("kappa", 101)};


        Arrays.equals(person1, person2); // true
        Arrays.equals(person1, person3); // true


    }

    /*4. Comparing two arrays lexicographically */
    // JDK 9 onwards only


    /*5. Creating a stream from an array */
    void createStream() {
        String[] arr = {"One", "Two", "Three", "Four", "Five"};
        Stream<String> stream = Arrays.stream(arr);

        // if stream only sub-array
        Stream<String> streamSubArray = Arrays.stream(arr, 0, 2);

        Stream.of(arr);


        int[] ints = {1, 2, 3, 4};
        IntStream stream1 = Arrays.stream(ints); // primitives Intstream

        // also has DoubleStream ; LongStream etc...

    }


    /*6. Minimum, maximum, and average of an array*/
    void findMaxMinAvg() {
        // max using basic for loop
        int[] ints = {1, 2, 3, 4};

        int max = ints[0];

        for (int elem : ints) {
            if (elem > max) {
                max = elem;
            }
        }

        //java 8
        Arrays.stream(ints)
                .max()
                .getAsInt();


        // objects
        Person[] persons = {
                new Person("kappa", 171),
                new Person("chappa", 162),
                new Person("tapppa", 170),
                new Person("lappa", 171)
        };

        Comparator<Person> getMax = Comparator.comparing(Person::getHeight);

        Arrays.stream(persons)
                .max(getMax)
                .orElseThrow(() -> new RuntimeException());


        // avg
//        Compute the sum of the elements from the array.Divide this sum by the length of the array.

        double avg = Arrays.stream(ints).average().getAsDouble(); // u can also use 3rd party libraries like apache commons or guava


    }

    /*7. Reversing an array */
    void reverseArr() {
        // for loop
        int[] arr = {1, 3, 5, 7, 8, 4, 2};
        for (int leftHead = 0, rightHead = arr.length - 1;
             leftHead < rightHead; leftHead++, rightHead--) {

            int elem = arr[leftHead];
            arr[leftHead] = arr[rightHead];
            arr[rightHead] = elem;
        }

        // java 8
        int[] reversed = IntStream.rangeClosed(1, arr.length)
                .map(i -> arr[arr.length - i]).toArray();


        // for objects
        Person[] persons = {
                new Person("kappa", 171),
                new Person("chappa", 162),
                new Person("tapppa", 170),
                new Person("lappa", 171)
        };
        Collections.reverse(Arrays.asList(persons));

        //not mutating
        IntStream.rangeClosed(1, persons.length)
                .mapToObj(i -> persons[persons.length - i])
                .toArray(Person[]::new);


    }

    public static void main(String[] args) {


    }

}
