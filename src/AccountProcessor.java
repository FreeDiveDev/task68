import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AccountProcessor {

    public static void main(String[] args) {
        // Path to your CSV file
        String filePath = "src/resources/accounts.csv";

        // Create a map where the key is the first two digits, and the value is the list of account numbers
        Map<String, List<String>> accountMap = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;

            while ((line = reader.readNext()) != null) {
                if (line.length > 0) {
                    String accountNumber = line[0];

                    // Check if the account number is numeric and at least 2 digits long
                    if (accountNumber.matches("\\d+") && accountNumber.length() >= 2) {
                        // Extract the first two digits
                        String key = accountNumber.substring(0, 2);

                        // Add the account number to the appropriate list in the map
                        accountMap.computeIfAbsent(key, k -> new ArrayList<>()).add(accountNumber);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        // Sort the keys numerically and print the arrays
        accountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparingInt(Integer::parseInt))) // Sort by numeric value of key
                .forEach(entry -> {
                    String key = entry.getKey();
                    List<String> accounts = entry.getValue();
                    // Sort the list of account numbers
                    Collections.sort(accounts);
                    // Print the arrays
                    System.out.println("Array for accounts starting with " + key + ": " + accounts);
                });
    }
}
