package ThreadController;

import java.util.Scanner;
import java.util.concurrent.locks.*;

public class ThreadController {
    private final Thread thread;
    private boolean isExit = false;
    private boolean isRunning = false;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public ThreadController() {
        thread = new Thread(() -> {
            while (!isExit) {
                lock.lock();
                try {
                    while (!isRunning && !isExit) {
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                } finally {
                    lock.unlock();
                }

                if (isRunning) {
                    System.out.println("Поток работает");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        });

        thread.start();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Команды: start, stop, exit");

        while (!isExit) {
            String cmd = scanner.nextLine().trim().toLowerCase();

            switch (cmd) {
                case "start" -> startThread();
                case "stop" -> stopThread();
                case "exit" -> exitThread();
                default -> System.out.println("Неизвестная команда");
            }
        }

        scanner.close();
    }

    private void startThread() {
        lock.lock();
        try {
            if (isRunning) {
                System.out.println("Поток уже запущен");
                return;
            }
            isRunning = true;
            condition.signal();
            System.out.println("Поток запущен");
        } finally {
            lock.unlock();
        }
    }

    private void stopThread() {
        lock.lock();
        try {
            isRunning = false;
            System.out.println("Поток остановлен");
        } finally {
            lock.unlock();
        }
    }

    private void exitThread() {
        lock.lock();
        try {
            isRunning = false;
            isExit = true;
            condition.signal();
            System.out.println("Поток завершен");
        } finally {
            lock.unlock();
        }

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}