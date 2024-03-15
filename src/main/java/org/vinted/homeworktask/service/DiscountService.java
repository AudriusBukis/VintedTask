package org.vinted.homeworktask.service;

import org.vinted.homeworktask.domain.ShippingProvider;
import org.vinted.homeworktask.domain.Transaction;
import org.vinted.homeworktask.utils.ShippingProviderUtils;

import java.math.BigDecimal;
import java.util.List;

import static org.vinted.homeworktask.config.Constants.*;

public class DiscountService {

  private static final int NUMBER_OF_LP_SHIPPING_SIZE_L_IS_FREE = 3;

    public BigDecimal calculateDiscount(Transaction memberTransaction, List<ShippingProvider> shippingProviders, BigDecimal remainingDiscountPerMonth, int packageSizeLNumberPerMonth) {

        switch (memberTransaction.getPackageSizeCode()) {
            case SMALL_PACKAGE_SIZE :
                return calculateDiscountForPackageSizeS(memberTransaction, shippingProviders, remainingDiscountPerMonth);
            case MEDIUM_PACKAGE_SIZE:
                return BigDecimal.ZERO;
            case LARGE_PACKAGE_SIZE:
                return calculateDiscountForPackageSizeL(memberTransaction, shippingProviders, remainingDiscountPerMonth, packageSizeLNumberPerMonth);
            default:
                return BigDecimal.ZERO;
        }
    }

    /**
     * Calculates the discount for a package of size S based on the member's transaction and shipping providers.
     * If the calculated discount is greater than 0 and can be applied from the remaining monthly discount,
     * returns the calculated discount; otherwise, returns the remaining monthly discount or zero.
     *
     * @param memberTransaction the member's transaction
     * @param shippingProviders the list of shipping providers
     * @param remainingDiscountPerMonth the remaining discount amount per month
     * @return the discount amount for the package of size S
     */
    private BigDecimal calculateDiscountForPackageSizeS(Transaction memberTransaction, List<ShippingProvider> shippingProviders, BigDecimal remainingDiscountPerMonth) {
        BigDecimal calculatedDiscount = ShippingProviderUtils.getShippingPrice(shippingProviders, memberTransaction).subtract(ShippingProviderUtils.getSmallestPriceOfPackageSizeS(shippingProviders));

        if (calculatedDiscount.compareTo(BigDecimal.ZERO) > 0) {
            return remainingDiscountPerMonth.subtract(calculatedDiscount).compareTo(BigDecimal.ZERO) >= 0 ? calculatedDiscount : remainingDiscountPerMonth;
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**
     * Calculates the discount for a package of size L based on specific conditions.
     *
     * @param memberTransaction The member's transaction
     * @param shippingProviders The list of available shipping providers
     * @param remainingDiscountPerMonth The remaining discount amount per month
     * @param packageSizeLNumberPerMonth The number of package size L per month
     * @return The calculated discount amount or zero if no discount applies
     */
    private BigDecimal calculateDiscountForPackageSizeL(Transaction memberTransaction, List<ShippingProvider> shippingProviders, BigDecimal remainingDiscountPerMonth, int packageSizeLNumberPerMonth) {
        if (memberTransaction.getProviderCode().equals(LA_POSTE_PROVIDER_CODE) && packageSizeLNumberPerMonth == NUMBER_OF_LP_SHIPPING_SIZE_L_IS_FREE) {
            BigDecimal calculatedDiscount = ShippingProviderUtils.getShippingPrice(shippingProviders, memberTransaction);
            return remainingDiscountPerMonth.subtract(calculatedDiscount).compareTo(BigDecimal.ZERO) >= 0 ? calculatedDiscount : remainingDiscountPerMonth;
        }
        return BigDecimal.ZERO;
    }




}
