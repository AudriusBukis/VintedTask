package org.vinted.homeworktask.validation;

import org.vinted.homeworktask.domain.Transaction;

import static org.vinted.homeworktask.service.ShippingService.shippingProviders;

public class ShippingProviderValidator {
    public boolean isValid(Transaction memberTransaction) {
        return shippingProviders
                .stream()
                .anyMatch(shippingProvider -> shippingProvider.packageSizeCode().equals(memberTransaction.getPackageSizeCode())
                        && shippingProvider.providerCode().equals(memberTransaction.getProviderCode()));

    }
}
