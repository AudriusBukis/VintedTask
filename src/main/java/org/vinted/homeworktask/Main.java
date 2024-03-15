package org.vinted.homeworktask;

import org.vinted.homeworktask.service.ShippingService;

public class Main {
    public static void main(String[] args) {
        ShippingService shippingService = new ShippingService();
        shippingService.calculateShippingPrice();
    }
}