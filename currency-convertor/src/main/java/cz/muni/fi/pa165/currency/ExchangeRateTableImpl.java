package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.util.Currency;

public class ExchangeRateTableImpl implements ExchangeRateTable {
    @Override
    public BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency) throws ExternalServiceFailureException {
        if (sourceCurrency.getCurrencyCode().equals("EUR") && targetCurrency.getCurrencyCode().equals("CZK")) {
            return new BigDecimal("27.00");
        } else {
            return null;
        }
    }
}
