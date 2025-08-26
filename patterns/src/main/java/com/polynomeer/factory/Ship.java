package com.polynomeer.factory;

class Ship implements Transport {
    @Override
    public String deliver(String cargo) {
        return "Ship delivers " + cargo + " by sea";
    }
}