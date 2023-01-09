package pattern.service;

import pattern.Price;

public class BasicPriceProcessor implements PriceProcessor {

    @Override
    public Price process(Price price) {
        return price;
    }
}
