package org.vinted.homeworktask.domain;

import java.math.BigDecimal;

public record ShippingProvider(String providerCode, String packageSizeCode, BigDecimal price) {
}
