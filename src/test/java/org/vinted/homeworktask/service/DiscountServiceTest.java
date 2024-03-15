package org.vinted.homeworktask.service;

import org.junit.jupiter.api.Test;
import org.vinted.homeworktask.domain.ShippingProvider;
import org.vinted.homeworktask.domain.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.vinted.homeworktask.config.Constants.*;

public class DiscountServiceTest {
    DiscountService discountService = new DiscountService();
    public static final List<ShippingProvider> shippingProviders = new ArrayList<>() {{
        add(new ShippingProvider(LA_POSTE_PROVIDER_CODE, SMALL_PACKAGE_SIZE, new BigDecimal("1.5")));
        add(new ShippingProvider(LA_POSTE_PROVIDER_CODE, MEDIUM_PACKAGE_SIZE, new BigDecimal("4.90")));
        add(new ShippingProvider(LA_POSTE_PROVIDER_CODE, LARGE_PACKAGE_SIZE, new BigDecimal("6.90")));
        add(new ShippingProvider(MONDIAL_RELAY_PROVIDER_CODE, SMALL_PACKAGE_SIZE, new BigDecimal("2")));
        add(new ShippingProvider(MONDIAL_RELAY_PROVIDER_CODE, MEDIUM_PACKAGE_SIZE, new BigDecimal("3")));
        add(new ShippingProvider(MONDIAL_RELAY_PROVIDER_CODE, LARGE_PACKAGE_SIZE, new BigDecimal("4")));
    }};

    @Test
    void testGetDiscountForShippingPackageSizeSmallReturnsCorrectDiscount() {
        Transaction transaction = new Transaction();
        transaction.setPackageSizeCode(SMALL_PACKAGE_SIZE);
        transaction.setProviderCode(MONDIAL_RELAY_PROVIDER_CODE);
        transaction.setPackageDate(new Date());

        BigDecimal calculatedDiscount = discountService.calculateDiscount(transaction, shippingProviders, new BigDecimal("10.00"), 0);

        assertEquals(new BigDecimal("0.5"), calculatedDiscount);
    }

    @Test
    void testGetDiscountForShippingPackageSizeSmallReturnsNoDiscount() {
        Transaction transaction = new Transaction();
        transaction.setPackageSizeCode(SMALL_PACKAGE_SIZE);
        transaction.setProviderCode(LA_POSTE_PROVIDER_CODE);
        transaction.setPackageDate(new Date());

        BigDecimal calculatedDiscount = discountService.calculateDiscount(transaction, shippingProviders, new BigDecimal("10.00"), 0);

        assertEquals(BigDecimal.ZERO, calculatedDiscount);
    }

    @Test
    void testGetDiscountForShippingPackageSizeSmallReturnsLessDiscountThenMonthlyIsAlmostEmpty() {
        Transaction transaction = new Transaction();
        transaction.setPackageSizeCode(SMALL_PACKAGE_SIZE);
        transaction.setProviderCode(MONDIAL_RELAY_PROVIDER_CODE);
        transaction.setPackageDate(new Date());

        BigDecimal calculatedDiscount = discountService.calculateDiscount(transaction, shippingProviders, new BigDecimal("0.35"), 0);

        assertEquals(new BigDecimal("0.35"), calculatedDiscount);
    }

    @Test
    void testGetDiscountForShippingPackageSizeMediumReturnsNoDiscount() {
        Transaction transaction = new Transaction();
        transaction.setPackageSizeCode(MEDIUM_PACKAGE_SIZE);
        transaction.setProviderCode(MONDIAL_RELAY_PROVIDER_CODE);
        transaction.setPackageDate(new Date());

        BigDecimal calculatedDiscount = discountService.calculateDiscount(transaction, shippingProviders, new BigDecimal("10.00"), 0);

        assertEquals(BigDecimal.ZERO, calculatedDiscount);
    }

    @Test
    void testGetDiscountForShippingPackageSizeLargeMRReturnsNoDiscount() {
        Transaction transaction = new Transaction();
        transaction.setPackageSizeCode(LARGE_PACKAGE_SIZE);
        transaction.setProviderCode(MONDIAL_RELAY_PROVIDER_CODE);
        transaction.setPackageDate(new Date());

        BigDecimal calculatedDiscount = discountService.calculateDiscount(transaction, shippingProviders, new BigDecimal("10.00"), 0);

        assertEquals(BigDecimal.ZERO, calculatedDiscount);
    }

    @Test
    void testGetDiscountForShippingFirstPackageSizeLargeLPReturnsCorrectDiscount() {
        Transaction transaction = new Transaction();
        transaction.setPackageSizeCode(LARGE_PACKAGE_SIZE);
        transaction.setProviderCode(LA_POSTE_PROVIDER_CODE);
        transaction.setPackageDate(new Date());
        int packageSizeLNumberPerMonth = 1;

        BigDecimal calculatedDiscount = discountService.calculateDiscount(transaction, shippingProviders, new BigDecimal("10.00"), packageSizeLNumberPerMonth);

        assertEquals(BigDecimal.ZERO, calculatedDiscount);
    }

    @Test
    void testGetDiscountForShippingThirdPackageSizeLargeLPReturnsCorrectDiscount() {
        Transaction transaction = new Transaction();
        transaction.setPackageSizeCode(LARGE_PACKAGE_SIZE);
        transaction.setProviderCode(LA_POSTE_PROVIDER_CODE);
        transaction.setPackageDate(new Date());
        int packageSizeLNumberPerMonth = 3;

        BigDecimal calculatedDiscount = discountService.calculateDiscount(transaction, shippingProviders, new BigDecimal("10.00"), packageSizeLNumberPerMonth);

        assertEquals(new BigDecimal("6.90") , calculatedDiscount);
    }

    @Test
    void testGetDiscountForShippingThirdPackageSizeLargeLPReturnsLessDiscountThenMonthlyIsAlmostEmpty() {
        Transaction transaction = new Transaction();
        transaction.setPackageSizeCode(LARGE_PACKAGE_SIZE);
        transaction.setProviderCode(LA_POSTE_PROVIDER_CODE);
        transaction.setPackageDate(new Date());
        int packageSizeLNumberPerMonth = 3;

        BigDecimal calculatedDiscount = discountService.calculateDiscount(transaction, shippingProviders, new BigDecimal("5.00"), packageSizeLNumberPerMonth);

        assertEquals(new BigDecimal("5.00") , calculatedDiscount);
    }
}
