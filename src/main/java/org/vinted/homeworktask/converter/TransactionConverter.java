package org.vinted.homeworktask.converter;

import org.vinted.homeworktask.domain.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TransactionConverter {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Converts a formatted input string to a MembersTransaction object.
     *
     * @param input The input string in the format "packageDate packageSizeCode carrierCode"
     * @return MembersTransaction object with the parsed values, or null if input is invalid
     */
    public Transaction convertStringToTransaction(String input) {
        String[] parts = input.split(" ");
        Transaction transaction = new Transaction();
        if (parts.length >= 3) {
            try {
                transaction.setPackageDate(dateFormat.parse(parts[0]));
                transaction.setPackageSizeCode(parts[1]);
                transaction.setProviderCode(parts[2]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return transaction;
        } else {
            return null;
        }
    }
}
