package com.kappa.microservices.collectionsDemo;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Problems31_37 {

    /*31. Test higher order function*/
    void hoc() {
        // Testing a HOC

    }

    /*32. Testing methods that use Lambdas */
    public static final Function<String, String> firstAndLastChar =
            (s) ->
                    String.valueOf(s.charAt(0)) + String.valueOf(s.charAt(s.length() - 1));

//    you can always test like below for lambdas
  /*  @Test
    public void testFirstAndLastChar() throws Exception {

        String text = "Lambda";
        String result = firstAndLastChar.apply(text);
        assertEquals("La", result);
    }*/


    /*33. debug a lambda expression */
    static void debugLambdas() {
        // note that there is null below which can cause NPE
        List<String> names = Arrays.asList("anna", "bob", null, "mary");

        // the null above can cause NPE
//        names.stream()
//                .map(String::toUpperCase)
//                .collect(Collectors.toList());

//    Exception in thread "main" java.lang.NullPointerException
//	at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:193)
//	at java.util.Spliterators$ArraySpliterator.forEachRemaining(Spliterators.java:948)
//	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:481)
//	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:471)
//	at java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:708)
//	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
//	at java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:499)
//	at com.kappa.microservices.collectionsDemo.Problems31_37.debugLambdas(Problems31_37.java:39)
//	at com.kappa.microservices.collectionsDemo.Problems31_37.main(Problems31_37.java:43)

//        above stack trace shows it caused NPE but it is not intuitive to know where


//        Alternatively , you can use logging ; but this will be a terminal operation


        // you can use Peek() int the method as it's intermediate operation
        List<String> namesz = Arrays.asList("anna", "bob",
                "christian", null, "carmen", "rick", "carla");

        names.stream()
                .peek(p -> System.out.println("\tstream(): " + p))
                .filter(s -> s.startsWith("c"))
                .peek(p -> System.out.println("\tfilter(): " + p))
                .map(String::toUpperCase)
                .peek(p -> System.out.println("\tmap(): " + p))
                .sorted()
                .peek(p -> System.out.println("\tsorted(): " + p))
                .collect(Collectors.toList());

        // now here you can exactly see where it occur as peek() is useful



        /*33. Filter a non-zero elements in a Stream */
        List<Integer> ints = Arrays.asList(1, 2, -4, 0, 2, 0, -1, 14, 0, -1);
        List<Integer> result = ints.stream()
                .filter(i -> i != 0)
                .collect(Collectors.toList());


        List<Integer> results = ints.stream()
                .filter(i -> i != 0)
                .distinct()    // duplicates are removed
                .skip(1)      // skips first element
                .limit(2)     // only 2 elements are handled
                .sorted()    //sorts in natural order
                .collect(Collectors.toList()); // collect into a list

    }

    /*34. long filters into static method*/
    void longFilterConds() {
        List<Integer> ints = Arrays.asList(1, 2, -4, 0, 2, 0, -1, 14, 0, -1);
        List<Integer> result = ints.stream()
                .filter(this::evenBetweenZeroAndTen) // this goes into static method
                .collect(Collectors.toList());
    }

    private boolean evenBetweenZeroAndTen(Integer value) {
        return value > 0 && value < 10 && value % 2 == 0;
    }






    public static void main(String[] args) {
        debugLambdas();
    }


}
