package org.vinted.homeworktask.service;

import org.vinted.homeworktask.converter.TransactionConverter;
import org.vinted.homeworktask.domain.Transaction;
import org.vinted.homeworktask.domain.ShippingProvider;
import org.vinted.homeworktask.utils.ShippingProviderUtils;
import org.vinted.homeworktask.validation.ShippingProviderValidator;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static org.vinted.homeworktask.config.Constants.*;

public class ShippingService {
    private final FileService fileService = new FileService();
    private final DiscountService discountService = new DiscountService();
    private final ShippingProviderValidator shippingProviderValidator = new ShippingProviderValidator();
    private final TransactionConverter transactionConverter = new TransactionConverter();
    private static final BigDecimal MAX_DISCOUNT_PER_MONTH = new BigDecimal("10.0");
    private static final DecimalFormat df = new DecimalFormat("#,##0.00");
    public static final List<ShippingProvider> shippingProviders = new ArrayList<>() {{
        add(new ShippingProvider(LA_POSTE_PROVIDER_CODE, SMALL_PACKAGE_SIZE, new BigDecimal("1.5")));
        add(new ShippingProvider(LA_POSTE_PROVIDER_CODE, MEDIUM_PACKAGE_SIZE, new BigDecimal("4.90")));
        add(new ShippingProvider(LA_POSTE_PROVIDER_CODE, LARGE_PACKAGE_SIZE, new BigDecimal("6.90")));
        add(new ShippingProvider(MONDIAL_RELAY_PROVIDER_CODE, SMALL_PACKAGE_SIZE, new BigDecimal("2")));
        add(new ShippingProvider(MONDIAL_RELAY_PROVIDER_CODE, MEDIUM_PACKAGE_SIZE, new BigDecimal("3")));
        add(new ShippingProvider(MONDIAL_RELAY_PROVIDER_CODE, LARGE_PACKAGE_SIZE, new BigDecimal("4")));
    }};


    /**
     * Calculate the shipping price for each transaction based on the data read from the input file.
     * The calculated shipping prices are then written to an output file.
     */
    public void calculateShippingPrice() {
        // Read data from input file
        List<String> shippingList = new ArrayList<>();
        try {
            shippingList = fileService.readDataFromFile("input.txt");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        List<String> listWithShippingPrice = new ArrayList<>();
        // Process each month
        for (int i = 1; i <= 12; i++) {
            String monthNumber = String.format("%02d", i);
            // Filter transactions for the current month
            List<String> FilteredTransactionsByMonth = shippingList.stream().filter(s -> s.contains(HYPHEN + monthNumber + HYPHEN)).toList();

            // Initialize discount and package size variables
            BigDecimal discountPerMonth =  MAX_DISCOUNT_PER_MONTH;
            int packageSizeLNumberPerMonth = 0;

            // Process each transaction for the current month
            for (String transaction : FilteredTransactionsByMonth) {
                Transaction memberTransaction = transactionConverter.convertStringToTransaction(transaction);

                if (memberTransaction == null || !shippingProviderValidator.isValid(memberTransaction)) {
                    listWithShippingPrice.add(transaction + SPACER + IGNORED);
                }else {
                    if (memberTransaction.getPackageSizeCode().equals(LARGE_PACKAGE_SIZE) && memberTransaction.getProviderCode().equals(LA_POSTE_PROVIDER_CODE)) {
                        packageSizeLNumberPerMonth++;
                    }
                    if (discountPerMonth.compareTo(BigDecimal.ZERO) > 0) {
                        BigDecimal calculateDiscount = discountService.calculateDiscount(memberTransaction, shippingProviders, discountPerMonth, packageSizeLNumberPerMonth);
                        discountPerMonth = discountPerMonth.subtract(calculateDiscount);
                        listWithShippingPrice.add(transaction +
                                SPACER +
                                df.format(ShippingProviderUtils.getShippingPrice(shippingProviders, memberTransaction).subtract(calculateDiscount)) +
                                (calculateDiscount.compareTo(BigDecimal.ZERO) > 0 ? SPACER + df.format(calculateDiscount) : NO_DISCOUNT ));
                    }else {
                        listWithShippingPrice.add(transaction + SPACER + df.format(ShippingProviderUtils.getShippingPrice(shippingProviders, memberTransaction)) + NO_DISCOUNT );
                    }

                }
            }
        }
        // print data to console
        listWithShippingPrice.forEach(System.out::println);
        // Write data to output file
        fileService.writeDataToFile(listWithShippingPrice, "output.txt");
    }
}
