package org.vinted.homeworktask.utils;

import org.vinted.homeworktask.domain.ShippingProvider;
import org.vinted.homeworktask.domain.Transaction;

import java.math.BigDecimal;
import java.util.List;

import static org.vinted.homeworktask.config.Constants.SMALL_PACKAGE_SIZE;

public class ShippingProviderUtils {

    /**
     * Returns the smallest price of shipping providers for package size S
     * @param shippingProviders list of shipping providers
     * @return smallest price for package size S
     */
    public static BigDecimal getSmallestPriceOfPackageSizeS(List<ShippingProvider> shippingProviders) {
        return shippingProviders
                .stream()
                .filter(s -> s.packageSizeCode().equals(SMALL_PACKAGE_SIZE))
                .map(ShippingProvider::price)
                .min(BigDecimal::compareTo)
                .get();
    }

    /**
     * Gets the shipping price from the list of shipping providers based on the member transaction.
     *
     * @param shippingProviders the list of shipping providers
     * @param memberTransaction the member transaction
     * @return the shipping price
     */
    public static BigDecimal getShippingPrice(List<ShippingProvider> shippingProviders, Transaction memberTransaction) {
        return shippingProviders
                .stream()
                .filter(s -> s.packageSizeCode().equals(memberTransaction.getPackageSizeCode()) && s.providerCode().equals(memberTransaction.getProviderCode()))
                .map(ShippingProvider::price)
                .findFirst()
                .get();
    }
}
