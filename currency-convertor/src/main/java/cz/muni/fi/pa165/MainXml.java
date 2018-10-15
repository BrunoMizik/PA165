package cz.muni.fi.pa165;


import cz.muni.fi.pa165.currency.CurrencyConvertor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainXml {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        CurrencyConvertor currencyConvertor = (CurrencyConvertor)context.getBean("CurrencyConvertor");
        BigDecimal rate = currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), BigDecimal.ONE);
        if (!rate.equals(new BigDecimal("27.00"))){
            throw new IllegalStateException("Incorrect value returned");
        }
    }


}