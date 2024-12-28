package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PersonData pData = null;
        while (true) {
            System.out.println("Input your data (Surname Name MiddleName DateOfBirth PhoneNumber Gender):");
            String s = scanner.nextLine();
            try {
                pData = new PersonData();
                TryParse(s, pData);
                break;
            } catch (Exception e) {
                System.out.println(e+ "\n\n[LET'S TRY AGAIN]");
            }
        }
        try {
            WriteFile(pData);
        } catch (Exception e) {
            System.out.println("[ERROR] while writing the file");
            e.printStackTrace();
        }
        System.out.println("[GOODBYE]");
        scanner.close();
    }

    private static void TryParse(String s, PersonData personData) throws Exception {
        String[] split = null;
        try {
            split = s.split(" ");
        } catch (Exception e) {
            System.out.println("[ERROR] String split failed");
        }
        if (split != null && split.length != 6) {
            throw new IllegalArgumentException("[ERROR] Invalid input: 6 members required");
        }
        boolean genderFound = false;
        boolean dateFound = false;
        boolean numberFound = false;
        int repeatsLeft = 6;
        while (repeatsLeft > 0) {
            for (int i = 0; i < split.length; i++) {
                if (split[i] == null) continue;
                if (!genderFound) {
                    genderFound = TryParseAs_Gender(split[i], personData);
                    if (genderFound)
                    {
                        split[i] = null;
                        break;
                    }
                }
                else if (!dateFound) {
                    dateFound = TryParseAs_Date(split[i], personData);
                    if (dateFound) {
                        split[i] = null;
                        break;
                    }
                }
                else if (!numberFound) {
                    numberFound = TryParseAs_PhoneNumber(split[i], personData);
                    if (numberFound) {
                        split[i] = null;
                        break;
                    }
                }
                else {
                    if (personData.surname == null) {
                        personData.surname = split[i];
                        split[i] = null;
                        continue;
                    }
                    if (personData.name == null) {
                        personData.name = split[i];
                        split[i] = null;
                        continue;
                    }
                    if (personData.middlename == null) {
                        personData.middlename = split[i];
                        split[i] = null;
                    }
                }
            }
            repeatsLeft--;
        }
        if (!genderFound) throw new IllegalArgumentException("[ERROR] gender not found");
        if (!dateFound) throw new IllegalArgumentException("[ERROR] date not found");
        if (!numberFound) throw new IllegalArgumentException("[ERROR] number not found");
    }
    private static boolean TryParseAs_Gender(String s, PersonData personData)
    {
        if (s.length() != 1) {
            return false;
        }
        if (s.equalsIgnoreCase("f")) {
            personData.gender = 'f';
            return true;
        }
        else if (s.equalsIgnoreCase("m")) {
            personData.gender = 'm';
            return true;
        }
        return false;
    }
    private static boolean TryParseAs_Date(String s, PersonData personData)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            personData.dateOfBirth = LocalDate.parse(s, dtf).toString();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    private static boolean TryParseAs_PhoneNumber(String s, PersonData personData)
    {
        try {
            personData.number = Long.parseLong(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    private static void WriteFile(PersonData personData) throws IOException {
        String fileName = personData.surname + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(personData.toString());
            writer.newLine();
        } catch (IOException e) {
            throw e;
        }
    }
}
//surname name 1234523 12.12.1212 m middlenameae