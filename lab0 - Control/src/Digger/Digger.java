package Digger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Digger {
    public void dig(String className) {
        try {
            Class c = Class.forName(className);

            System.out.println("Поля класса:");
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                System.out.println("  " + field.getType().getSimpleName() + " " + field.getName());
            }

            System.out.println("\nМетоды класса:");
            Method[] methods = c.getDeclaredMethods();
            for (Method method : methods) {
                System.out.println("  " + method.getReturnType().getSimpleName() + " " +
                                   method.getName() + "(" +
                                   Arrays.toString(method.getParameterTypes()) + ")");
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
