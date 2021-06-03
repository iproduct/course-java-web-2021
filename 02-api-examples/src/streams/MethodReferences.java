package streams;

import java.util.function.BiFunction;
public class MethodReferences {

    public static <T> T mergeThings(T a, T b, BiFunction<T, T, T> merger) {
        return merger.apply(a, b);
    }

    public static String appendStrings(String a, String b) {
        return a + b;
    }

    public String appendStrings2(String a, String b) {
        return a + b;
    }

    public static void main(String[] args) {

        MethodReferences myApp = new MethodReferences();

        // Calling the method mergeThings with a lambda expression
        System.out.println(MethodReferences.
                mergeThings("Hello ", "World!", (a, b) -> a + b));

        // Reference to a static method
        System.out.println(MethodReferences.
                mergeThings("Hello ", "World!", MethodReferences::appendStrings));

        // Reference to an instance method of a particular object
        System.out.println(MethodReferences.
                mergeThings("Hello ", "World!", myApp::appendStrings2));

        // Reference to an instance method of an arbitrary object of a
        // particular type
        System.out.println(MethodReferences.
                mergeThings("Hello ", "World!", String::concat));
    }
}
