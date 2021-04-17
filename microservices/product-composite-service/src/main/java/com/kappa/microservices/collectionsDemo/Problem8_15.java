package com.kappa.microservices.collectionsDemo;

import io.swagger.models.auth.In;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Problem8_15 {
    /*8. Filling and setting an array:*/
    /*9. Next Greater Element (NGE)*/
    /*10. Changing array size */
    /*11. Creating unmodifiable/immutable collections */
    /*12. Mapping a default value */
    /*13. Computing whether absent/present in a Map */
    /*14. Removal from a Map */
    /*15. Replacing entries from a Map*/


    static Character findFirstNonRepeatedChar() {    /*finds first occurrence of Non-repeated character*/
        String input = "aabbbcddef";
        char[] chars = input.toCharArray();
        Map<Character, Integer> map = new LinkedHashMap<>(); // insertion order is preserved

        for (int i = 0; i < input.length(); i++) {
            if (map.containsKey(chars[i])) {
                map.put(chars[i], map.get(chars[i]) + 1);
            } else {
                map.put(chars[i], 1);
            }
        }

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                System.out.println("key :  " + entry.getKey());
                return entry.getKey();
            }
        }

//        if (Objects.isNull(input)){
//            return Character.MIN_VALUE;
//        }

        for (int i = 0; i < input.length(); i++) {

            int count = 0;
            char ch = input.charAt(i);
            for (int j = 0; j < input.length(); j++) {
                if (ch == input.charAt(j) && i != j) {
                    count++;
                    break;
                }

                if (count == 0) {
                    System.out.println("count is 0 : " + ch);
                    return ch;
                }
            }
        }


        input.chars()                                   // chars creates a INtStream
                .mapToObj(codepoint -> (char) codepoint)       // change this into codepoints [ characters of extended Ascii]
                .collect(Collectors.groupingBy(         // grouping by
                        Function.identity(),            // Function.identity()
                                                        // is similar to `c -> c` but i have read somewhere that Function.identity() has some memory advantage ove c -> c ;
                                                        //  but you can go for which is more readable ;some places it gives compile errors , like mapToInt (this doesnt have identity() )
                        LinkedHashMap::new,             // linkedhashmaps - insertion order is preserved
                        Collectors.counting()           // count how many times the key is repeated
                ));

        // the decision is made at runtime.
        // The compiler inserts an invokedynamic instruction which gets linked on its first execution by executing a so-called bootstrap method, which in the case of lambda expressions is located in the LambdaMetafactory.
        // This implementation decides to return a handle to a constructor, a factory method, or code always returning the same object.
        // It may also decide to return a link to an already existing handle (which currently doesn’t happen)

        return Character.MIN_VALUE;
    }


    static void findDuplicates() {
        String input = "rajarani";
        Map<Character, Long> collect =
                input.chars()                           // creates an IntStream
                        .mapToObj(c -> (char) c)        // transform an InStream into character [integer -> character ]
                        .collect(Collectors.groupingBy(character -> character, Collectors.counting())); // grouping characters according with their count
        System.out.println(collect);

    }


    public static void main(String[] args) {
//        findDuplicates();
        findFirstNonRepeatedChar();

    }
}
