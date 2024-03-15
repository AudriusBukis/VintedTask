package org.vinted.homeworktask.validation;

import org.junit.jupiter.api.Test;
import org.vinted.homeworktask.domain.Transaction;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.vinted.homeworktask.config.Constants.LA_POSTE_PROVIDER_CODE;
import static org.vinted.homeworktask.config.Constants.SMALL_PACKAGE_SIZE;

public class ShippingProviderValidatorTest {
    private final ShippingProviderValidator shippingProviderValidator = new ShippingProviderValidator();

    @Test
    public void testIfIsValidReturnsTrueWhenThenShippingProviderAndPackageSizeCodeExists() {
        Transaction memberTransaction = new Transaction();
        memberTransaction.setProviderCode(LA_POSTE_PROVIDER_CODE);
        memberTransaction.setPackageSizeCode(SMALL_PACKAGE_SIZE);
        memberTransaction.setPackageDate(new Date());

        boolean isValid = shippingProviderValidator.isValid(memberTransaction);

        assertTrue(isValid);
    }

    @Test
    public void testIfIsValidReturnsFalseWhenThenShippingProviderNotExistsAndPackageSizeCodeExists() {
        Transaction memberTransaction = new Transaction();
        memberTransaction.setProviderCode("KK");
        memberTransaction.setPackageSizeCode(SMALL_PACKAGE_SIZE);
        memberTransaction.setPackageDate(new Date());

        boolean isValid = shippingProviderValidator.isValid(memberTransaction);

        assertFalse(isValid);
    }

    @Test
    public void testIfIsValidReturnsFalseWhenThenShippingProviderExistsAndPackageSizeCodeNotExists() {
        Transaction memberTransaction = new Transaction();
        memberTransaction.setProviderCode(LA_POSTE_PROVIDER_CODE);
        memberTransaction.setPackageSizeCode("G");
        memberTransaction.setPackageDate(new Date());

        boolean isValid = shippingProviderValidator.isValid(memberTransaction);

        assertFalse(isValid);
    }
}
