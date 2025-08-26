package com.polynomeer.factory;

class Airplane implements Transport {
    @Override
    public String deliver(String cargo) {
        return "Airplane delivers " + cargo + " by air";
    }
}