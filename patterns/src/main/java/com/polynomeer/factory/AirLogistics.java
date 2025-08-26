package com.polynomeer.factory;

class AirLogistics extends Logistics {
    @Override
    protected Transport createTransport() {
        return new Airplane();
    }
}