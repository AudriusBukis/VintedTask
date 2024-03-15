package org.vinted.homeworktask.utils;

import org.junit.jupiter.api.Test;
import org.vinted.homeworktask.domain.ShippingProvider;
import org.vinted.homeworktask.domain.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.vinted.homeworktask.config.Constants.*;

public class ShippingProviderUtilsTest {
    public static final List<ShippingProvider> shippingProviders = new ArrayList<>() {{
        add(new ShippingProvider(LA_POSTE_PROVIDER_CODE, SMALL_PACKAGE_SIZE, new BigDecimal("1.5")));
        add(new ShippingProvider(LA_POSTE_PROVIDER_CODE, MEDIUM_PACKAGE_SIZE, new BigDecimal("4.90")));
        add(new ShippingProvider(LA_POSTE_PROVIDER_CODE, LARGE_PACKAGE_SIZE, new BigDecimal("6.90")));
        add(new ShippingProvider(MONDIAL_RELAY_PROVIDER_CODE, SMALL_PACKAGE_SIZE, new BigDecimal("2")));
        add(new ShippingProvider(MONDIAL_RELAY_PROVIDER_CODE, MEDIUM_PACKAGE_SIZE, new BigDecimal("3")));
        add(new ShippingProvider(MONDIAL_RELAY_PROVIDER_CODE, LARGE_PACKAGE_SIZE, new BigDecimal("4")));
    }};

    @Test
    public void testIfShippingProvidersUtilsReturnsCorrectShippingPrice() {
        Transaction transaction = new Transaction();
        transaction.setPackageSizeCode(SMALL_PACKAGE_SIZE);
        transaction.setProviderCode(MONDIAL_RELAY_PROVIDER_CODE);
        transaction.setPackageDate(new Date());

        BigDecimal shippingPrice = ShippingProviderUtils.getShippingPrice(shippingProviders, transaction);

        assertEquals(new BigDecimal("2"), shippingPrice);
    }

    @Test
    public void testIfShippingProvidersUtilsReturnsSmallestShippingPriceOfSmallPackage() {
        BigDecimal smallestShippingPrice = ShippingProviderUtils.getSmallestPriceOfPackageSizeS(shippingProviders);

        assertEquals(new BigDecimal("1.5"), smallestShippingPrice);
    }


}
