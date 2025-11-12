package aston.sorting;

import java.util.Scanner;

public final class App {
    private final Scanner scanner;

    private App() {
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        System.out.println("=== Программа сортировки студентов ===");
        boolean working = true;
        while (working) {
            // Программа должна выполняться в цикле
        }
        System.out.println("Программа завершена.");
    }
}
