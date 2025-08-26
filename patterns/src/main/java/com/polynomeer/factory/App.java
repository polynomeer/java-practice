package com.polynomeer.factory;

public class App {
    public static void main(String[] args) {
        // 환경/조건에 따라 어떤 Creator를 쓸지 결정(전략 교체 느낌)
        Logistics logistics;

        String mode = (args.length > 0) ? args[0].toLowerCase() : "road";
        switch (mode) {
            case "sea":
                logistics = new SeaLogistics();
                break;
            case "air":
                logistics = new AirLogistics();
                break;
            default:
                logistics = new RoadLogistics();
        }

        logistics.planDelivery("medical supplies");
    }
}