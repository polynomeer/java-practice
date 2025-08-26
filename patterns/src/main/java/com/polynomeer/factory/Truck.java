package com.polynomeer.factory;

class Truck implements Transport {
    @Override
    public String deliver(String cargo) {
        return "Truck delivers " + cargo + " by road";
    }
}
