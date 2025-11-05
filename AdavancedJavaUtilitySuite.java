import java.io.IOException;
import java.lang.annotation.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Advanced Java Utility Suite
 * A demonstration of modern Java features: reflection, concurrency, CompletableFuture,
 * sealed classes, records, annotations, and NIO I/O.
 */
public class AdavancedJavaUtilitySuite {
    public static void main(String[] args) throws Exception {
        System.out.println("=== Advanced Java Utility Suite Demo ===\n");

        // StringUtils demo
        System.out.println("Title case: " + StringUtils.titleCase("java UTILITY suite demo"));
        System.out.println("Levenshtein('kitten','sitting') = " + StringUtils.levenshtein("kitten", "sitting"));

        // Reflection demo
        Optional<Person> person = ReflectionUtils.instantiate(Person.class);
        System.out.println("Reflection create Person: present? " + person.isPresent());

        // Concurrency demo
        Function<Integer, BigInteger> fib = ConcurrencyUtils.memoize(Utils::bigFib);
        ExecutorService es = ConcurrencyUtils.newNamedFixedPool("worker", 4);
        try {
            List<Callable<BigInteger>> tasks = List.of(
                    () -> fib.apply(30),
                    () -> fib.apply(31),
                    () -> fib.apply(32));
            List<BigInteger> results = ConcurrencyUtils.invokeAllTimed(es, tasks, 5000);
            System.out.println("Fibonacci (memoized): " + results);
        } finally {
            es.shutdown();
        }

        // CompletableFuture demo
        ExecutorService single = Executors.newSingleThreadExecutor();
        CompletableFuture<String> pipeline = ConcurrencyUtils.supplyAsyncWithFallback(() -> {
            if (new Random().nextBoolean()) throw new RuntimeException("random failure");
            return "payload";
        }, s -> "mapped:" + s.toUpperCase(), single, ex -> "fallback:" + ex.getMessage());
        System.out.println("Pipeline result: " + pipeline.get());
        single.shutdown();

        // IO demo
        Path file = Paths.get("./demo-output.txt");
        IOUtils.writeStringToFile(file, "Hello from AdvancedJavaUtilitySuite!");
        System.out.println("File content: " + IOUtils.readStringFromFile(file));

        // Functional demo
        Map<Boolean, Long> counts = FunctionalUtils.partitionCount(List.of(1,2,3,4,5,6), i -> i % 2 == 0);
        System.out.println("Partition counts (even/odd): " + counts);

        // Annotation scan demo
        AnnotationsDemo.scanAndPrintImportant(Person.class, AdavancedJavaUtilitySuite.class);

        // Sealed types demo
        Shape s1 = new Circle(2.5);
        Shape s2 = new Rectangle(3, 4);
        System.out.println("Shapes: " + describeShape(s1) + ", " + describeShape(s2));

        System.out.println("\n=== Demo Complete ===");
    }

    @Important("Main demo method")
   private static String describeShape(Shape s) {
    if (s instanceof Circle c) {
        return "Circle(radius=" + c.radius() + ")";
    } else if (s instanceof Rectangle r) {
        return "Rectangle(" + r.width() + "x" + r.height() + ")";
    } else {
        return "Unknown shape";
    }
}


/* ==================== Utility Classes ==================== */

final class StringUtils {
    public static String titleCase(String input) {
        if (input == null || input.isBlank()) return input;
        return Arrays.stream(input.split("\\s+"))
                .map(w -> Character.toUpperCase(w.charAt(0)) + w.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
    public static int levenshtein(String a, String b) {
        if (a == null) a = ""; if (b == null) b = "";
        int m = a.length(), n = b.length();
        int[] prev = new int[n + 1];
        for (int j = 0; j <= n; j++) prev[j] = j;
        for (int i = 1; i <= m; i++) {
            int[] cur = new int[n + 1];
            cur[0] = i;
            for (int j = 1; j <= n; j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                cur[j] = Math.min(Math.min(cur[j - 1] + 1, prev[j] + 1), prev[j - 1] + cost);
            }
            prev = cur;
        }
        return prev[n];
    }
}

final class ReflectionUtils {
    public static <T> Optional<T> instantiate(Class<T> cls) {
        try {
            var ctor = cls.getDeclaredConstructor();
            ctor.setAccessible(true);
            return Optional.of(ctor.newInstance());
        } catch (Exception e) { return Optional.empty(); }
    }
}

final class ConcurrencyUtils {
    public static ExecutorService newNamedFixedPool(String prefix, int threads) {
        return Executors.newFixedThreadPool(threads, r -> {
            Thread t = new Thread(r);
            t.setName(prefix + "-" + UUID.randomUUID().toString().substring(0, 6));
            return t;
        });
    }
    public static <T> List<T> invokeAllTimed(ExecutorService es, Collection<Callable<T>> tasks, long timeoutMs) throws InterruptedException {
        var futures = es.invokeAll(tasks, timeoutMs, TimeUnit.MILLISECONDS);
        List<T> results = new ArrayList<>();
        for (Future<T> f : futures) {
            try { if (f.isDone() && !f.isCancelled()) results.add(f.get()); } catch (Exception ignored) {}
        }
        return results;
    }
    public static <K, V> Function<K, V> memoize(Function<K, V> fn) {
        var cache = new ConcurrentHashMap<K, V>();
        return k -> cache.computeIfAbsent(k, fn);
    }
    public static <T, R> CompletableFuture<R> supplyAsyncWithFallback(Supplier<T> supplier, Function<T, R> mapper,
                                                                      Executor executor, Function<Throwable, R> fallback) {
        return CompletableFuture.supplyAsync(supplier, executor)
                .thenApply(mapper)
                .exceptionally(fallback);
    }
}

final class IOUtils {
    public static void writeStringToFile(Path path, String content) throws IOException {
        Files.writeString(path, content, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
    public static String readStringFromFile(Path path) throws IOException {
        return Files.readString(path, StandardCharsets.UTF_8);
    }
}

final class FunctionalUtils {
    public static <T> Map<Boolean, Long> partitionCount(Collection<T> items, Predicate<T> predicate) {
        return items.stream().collect(Collectors.partitioningBy(predicate, Collectors.counting()));
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@interface Important { String value() default ""; }

@Important("Data Model")
record Person(String name, int age) {}

sealed interface Shape permits Circle, Rectangle {}
record Circle(double radius) implements Shape {}
record Rectangle(double width, double height) implements Shape {}

final class AnnotationsDemo {
    public static void scanAndPrintImportant(Class<?>... types) {
        for (Class<?> t : types) {
            if (t.isAnnotationPresent(Important.class)) {
                Important imp = t.getAnnotation(Important.class);
                System.out.println("[Important] " + t.getSimpleName() + " - " + imp.value());
            }
        }
    }
}

final class Utils {
    public static BigInteger bigFib(int n) {
        BigInteger a = BigInteger.ZERO, b = BigInteger.ONE;
        for (int i = 0; i < n; i++) {
            BigInteger t = a.add(b);
            a = b; b = t;
        }
        return a;
    }
}
}
