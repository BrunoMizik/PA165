package cz.muni.fi.pa165.currency;

import org.junit.Test;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConvertorImplTest {

    private static Currency CZK = Currency.getInstance("CZK");
    private static Currency EUR = Currency.getInstance("EUR");

    @Mock
    private ExchangeRateTable exchangeRateTable;

    private CurrencyConvertor currencyConvertor;

    @Before
    public void init() {
        currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testConvert() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(EUR, CZK))
                .thenReturn(new BigDecimal("25.00"));

        assertEquals(new BigDecimal("25.00"), currencyConvertor.convert(EUR, CZK, new BigDecimal("1.00")));
        assertEquals(new BigDecimal("37.50"), currencyConvertor.convert(EUR, CZK, new BigDecimal("1.50")));
        assertEquals(new BigDecimal("50.00"), currencyConvertor.convert(EUR, CZK, new BigDecimal("2.00")));
    }

    @Test
    public void testConvertWithNullSourceCurrency() {
        expectedException.expect(IllegalArgumentException.class);
        currencyConvertor.convert(null, CZK, new BigDecimal("1.00"));
    }

    @Test
    public void testConvertWithNullTargetCurrency() {
        expectedException.expect(IllegalArgumentException.class);
        currencyConvertor.convert(EUR, null, new BigDecimal("1.00"));
    }

    @Test
    public void testConvertWithNullSourceAmount() {
        expectedException.expect(IllegalArgumentException.class);
        currencyConvertor.convert(EUR, CZK, null);
    }

    @Test
    public void testConvertWithUnknownCurrency() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(EUR, CZK))
                .thenReturn(null);
        expectedException.expect(UnknownExchangeRateException.class);
        currencyConvertor.convert(EUR, CZK, new BigDecimal("1.00"));

    }

    @Test
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(EUR, CZK))
                .thenThrow(UnknownExchangeRateException.class);
        expectedException.expect(UnknownExchangeRateException.class);
        currencyConvertor.convert(EUR, CZK, new BigDecimal("1.00"));
    }
}
