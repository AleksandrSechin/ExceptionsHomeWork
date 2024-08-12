package ExceptionsHomeWork;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final int REQUIRED_DATA_COUNT = 6;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in, "ibm866")) {
            System.out.println("Добрый день!\nВведите фамилию, имя, отчество, дату рождения, номер телефона и пол через пробел:");

            String input = scanner.nextLine();
            String[] data = input.split("\\s+");

            if (data.length < REQUIRED_DATA_COUNT) {
                System.err.println("\nОшибка! Введено меньше данных, чем требуется. Ожидалось " + REQUIRED_DATA_COUNT + " параметров");
                return;
            } else if (data.length > REQUIRED_DATA_COUNT) {
                System.err.println("\nОшибка! Введено больше данных, чем требуется. Ожидалось " + REQUIRED_DATA_COUNT + " параметров");
                return;
            }

            char gender;

            try {
                if (!validateData(data)) {
                    throw new Exception("Некорректный ввод данных");
                }

                String lastName = data[0];
                String firstName = data[1];
                String middleName = data[2];
                String birthDate = data[3];
                String phoneNumber = data[4];

                if (data[5].length() != 1) {
                    throw new Exception("\nПол должен быть указан одним символом: 'm' или 'f'!");
                }
                gender = data[5].charAt(0);

                File file = new File(lastName + ".txt");

                if (!file.exists()) {
                    file.createNewFile();
                    System.out.printf("\nФайл с фамилией %s создан!\n", lastName);
                }

                writeToFile(file, lastName, firstName, middleName, birthDate, phoneNumber, gender);
                System.out.printf("Данные введены корректно и добавлены в файл с фамилией %s.\n", lastName);

            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            } 
        }
    }

    private static boolean validateData(String[] data) {
        String lastName = data[0].trim();
        String firstName = data[1].trim();
        String middleName = data[2].trim();
        String birthDateString = data[3].trim();
    
        if (!birthDateString.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
            System.out.println("\nДата рождения должна быть формата dd.mm.yyyy!");
            return false;
        }
    
        String phoneNumber = data[4].trim();
        if (!phoneNumber.matches("\\d+")) {
            System.out.println("\nНомер телефона должен быть целым беззнаковым числом!");
            return false;
        }
    
        if (data[5].length() != 1 || (data[5].charAt(0) != 'm' && data[5].charAt(0) != 'f')) {
            System.out.println("\nПол должен быть указан одним символом 'm' или 'f'!");
            return false;
        }
    
        if (lastName.isEmpty() || firstName.isEmpty() || middleName.isEmpty()) {
            System.out.println("\nФамилия, имя или отчество не должны быть пустыми!");
            return false;
        }
    
        return true;
    }
    
    private static void writeToFile(File file, String lastName, String firstName,
                                     String middleName, String birthDate,
                                     String phoneNumber, char gender) throws Exception {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(lastName + " " + firstName + " "
                    + middleName + " " + birthDate + " "
                    + phoneNumber + " " + gender + "\n");
        } catch (IOException e) {
            System.err.println("\nОшибка при записи в файл: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
