# JavaDeveloper_Internship
Internship Task 1 : Project implementing advanced core Java utilities using multi-threading, reflection, and functional patterns.
)
# Project Overview

The Advanced Java Utility Suite is a comprehensive demonstration of modern Java programming techniques.
This project integrates multiple core and advanced Java concepts—from Reflection and Annotations to Concurrency, Functional Programming, and File I/O—into one structured application.

The goal of this task is to explore how powerful, maintainable, and modular code can be built using Java 17 features.



# Objectives

Understand and apply Reflection for dynamic object manipulation.

Implement Concurrency and Thread Management using ExecutorService.

Explore CompletableFuture for asynchronous programming.

Use Java Streams & Functional Interfaces for cleaner logic.

Work with Annotations, Records, and Sealed Classes.

Perform file handling using Java NIO (non-blocking I/O).

# Modules and Their Theoretical Concepts
🧱 1. StringUtils — String Manipulation

Concepts: String operations, loops, functional streams.
Theory:

Demonstrates how to process strings efficiently.

titleCase() converts any text to proper capitalization using Streams.

levenshtein() implements the edit distance algorithm to compare two strings.

 2. ReflectionUtils — Reflection API

Concepts: Reflection, dynamic class loading, object creation.
Theory:

Uses Class.getDeclaredConstructor() and newInstance() to create objects at runtime.

Reflection allows flexibility in frameworks, testing tools, and libraries like Spring.

 3. ConcurrencyUtils — Multi-threading & Async Tasks

Concepts: ExecutorService, Callable, Future, Memoization.
Theory:

Demonstrates thread pools and parallel computation.

Implements memoization to optimize recursive calculations like Fibonacci.

Uses CompletableFuture for async pipelines with fallback logic.

 4. IOUtils — File Handling with NIO

Concepts: File I/O, Path, Files, NIO API.
Theory:

Writes and reads files using UTF-8 encoding.

Demonstrates safe, modern file operations without legacy FileInputStream.

 5. FunctionalUtils — Functional Programming

Concepts: Streams, Lambdas, Collectors, Predicates.
Theory:

Demonstrates functional programming with partitioningBy() collector.

Simplifies data grouping and counting in one line.

 6. AnnotationsDemo — Custom Annotations

Concepts: Meta-annotations (@Retention, @Target), Reflection.
Theory:

Scans for classes or methods marked with a custom @Important annotation.

Mimics behavior of modern frameworks like Spring or JUnit.

 7. Shape (Sealed Interfaces) — Modern OOP

Concepts: Records, Sealed Interfaces, Pattern Matching.
# Theory:

sealed interface Shape permits Circle, Rectangle restricts inheritance for better design control.

record Circle(double radius) provides a concise, immutable data model.

Demonstrates clean OOP principles with immutability and polymorphism.

 # Key Learnings

How modern Java 17 features simplify and enhance programming.

The use of functional programming for cleaner and more efficient logic.

Managing concurrency and asynchronous tasks safely.

Building modular, reusable utilities with clear separation of concerns.

Understanding the role of Reflection and Annotations in dynamic frameworks.

# Tools & Technologies
Category	Technology
Language	Java 17
IDE	VS Code / IntelliJ IDEA
Concurrency	ExecutorService, CompletableFuture
OOP	Sealed Interfaces, Records
Functional	Streams, Lambdas
File Handling	Java NIO

# How to Run
Step 1️⃣ — Compile
javac src/AdavancedJavaUtilitySuite.java

Step 2️⃣ — Run
java src.AdavancedJavaUtilitySuite


💡 If you removed the package declaration, use:

javac AdavancedJavaUtilitySuite.java
java AdavancedJavaUtilitySuite

🧠 Output Example
=== Advanced Java Utility Suite Demo ===

Title case: Java Utility Suite Demo
Levenshtein('kitten','sitting') = 3
Reflection create Person: present? true
Fibonacci (memoized): [832040, 1346269, 2178309]
Pipeline result: mapped:PAYLOAD
File content: Hello from AdvancedJavaUtilitySuite!
Partition counts (even/odd): {false=3, true=3}
Shapes: Circle(radius=2.5), Rectangle(3.0x4.0)

=== Demo Complete ===

# Learning Outcome

This task strengthened my understanding of:

Advanced Java programming paradigms

Thread management and async operations

Object-oriented design using new Java constructs

Building scalable, modular codebases

