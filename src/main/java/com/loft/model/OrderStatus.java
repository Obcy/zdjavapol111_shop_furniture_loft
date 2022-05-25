package com.loft.model;

public enum OrderStatus {

    NEW(0),
    PACKING(1),
    READY_TO_SEND(2),
    SEND(3),
    CANCELED(4);

    OrderStatus(int status) {
    }
}
