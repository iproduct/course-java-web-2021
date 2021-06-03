package streams;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamDemo {
    public static final Set<String> stopWords = Set.of("a", "about", "above", "after", "again", "against", "all", "am",
            "an", "and", "any", "are", "aren't", "as", "at", "be", "because",
            "been", "before", "being", "below", "between", "both", "but",
            "by", "can't", "cannot", "could", "couldn't", "did", "didn't",
            "do", "does", "doesn't", "doing", "don't", "down", "during", "each", "few", "for", "from", "further", "had", "hadn't", "has", "hasn't", "have", "haven't", "having", "he", "he'd", "he'll", "he's", "her", "here",
            "while", "who", "who's", "whom", "why", "why's", "with", "won't", "would", "wouldn't", "you", "you'd", "you'll", "you're", "you've", "your", "yours", "yourself", "yourselves"
    );

    public static void main(String[] args) throws IOException {
        // Creating streams
        Stream<String> streamEmpty = Stream.empty();//		streamEmpty.forEach(System.out::println);

        Collection<String> collection = Arrays.asList("a", "b", "c");
        Stream<String> streamOfCollection = collection.stream();//parallelStream().sequential();
//		streamOfCollection.forEach(System.out::println);

        Stream<String> streamOfElems = Stream.of("a", "b", "c", "d").limit(3);
//		streamOfElems.forEach(System.out::println);

        Stream<Integer> streamBuilder =
                Stream.<Integer>builder().add(1).add(2).add(3).build();
//		streamBuilder.forEach(System.out::println);

        IntStream intStream = IntStream.range(1, 10);
        LongStream longStream = LongStream.rangeClosed(1, 15).limit(5);
//		intStream.forEach(System.out::println);
        System.out.println();
//		longStream.forEach(System.out::println);

        Stream<Integer> streamIterated = Stream.iterate(40, n -> n + 5).limit(10);
//		streamIterated.forEach(System.out::println);

        Random rand = new Random();

        Stream<Integer> streamGenerated = Stream.generate(() -> rand.nextInt(100)).limit(10);
//		streamGenerated
//				.sorted(Comparator.reverseOrder())
//				.map(n -> "Element " + n)
//				.forEach(System.out::println);

        IntStream streamOfChars = "IntStream is used".chars();
        String result = streamOfChars.mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining(", "));
//		System.out.println(result);

        Stream<String> streamOfString = Pattern.compile(", ").splitAsStream(result);
//		System.out.println( streamOfString.collect(Collectors.joining()) );

        Path path = Paths.get("wikipedia.txt");
        List<String> words = Files.lines(path)
                .flatMap(line -> Pattern.compile("[\s,;!?-_/()'\".]+").splitAsStream(line))
                .filter(w -> w.length() > 2)
                .distinct()
                .collect(Collectors.toList());
//				.map(l -> new Tuple<String, Integer>(l, 0))
//				.reduce(new Tuple<>("", 1),
//					(acc, line) -> // accumulator
//						new Tuple<>(acc.getProp1() + acc.getProp2() + ": " + line + "\n", acc.getProp2() + 1),
//					(acc1, acc2) -> // combimner
//						new Tuple<>(acc1.getProp1() + "\n" + acc2.getProp2(), 0)
//				).getProp1();

        System.out.println(words);

        StringBuilder resBuilder = Files.lines(path, Charset.forName("UTF-8"))
//	   		.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
                .map(String::toUpperCase)
                .collect(StringBuilder::new,
                        (sb, line) -> sb.append(line).append(System.lineSeparator()),
                        (sb1, sb2) -> sb1.append(sb2));

//		StringBuilder sb = new StringBuilder();
//		for(int i = 0; i < lines.size(); i++) {
//			sb.append(i + 1).append(": ").append(lines.get(i)).append(System.lineSeparator());
//		}

//		System.out.println(resBuilder);

        List<String> listWithDuplicates = Arrays.asList("a", "bb", "ccccc", "d", "bb");
        Map<String, Integer> mapResult =
                listWithDuplicates.stream().collect(Collectors.toMap(Function.identity(), String::length,
                        (e1, e2) -> e2));
//		System.out.println(mapResult);

        IntSummaryStatistics stat = listWithDuplicates.stream().collect(
                Collectors.summarizingInt(s -> s.length()));
//		System.out.println(stat);


    }

}
