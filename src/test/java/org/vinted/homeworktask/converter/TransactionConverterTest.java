package org.vinted.homeworktask.converter;

import org.junit.jupiter.api.Test;
import org.vinted.homeworktask.domain.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TransactionConverterTest {
    private final TransactionConverter transactionConverter = new TransactionConverter();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void testTransactionConverterReturnsTransactionIfInputIsValid() throws ParseException {
        Transaction transaction = transactionConverter.convertStringToTransaction("2022-01-01 LP LA");

        Date expected = dateFormat.parse("2022-01-01");

        assertEquals(expected, transaction.getPackageDate());
        assertEquals("LA", transaction.getProviderCode());
        assertEquals("LP", transaction.getPackageSizeCode());
    }

    @Test
    public void testTransactionConverterReturnsNullIfInputIsValid() {
        Transaction transaction = transactionConverter.convertStringToTransaction("2022-01-01 LPLA");

        assertNull(transaction);
    }
}
