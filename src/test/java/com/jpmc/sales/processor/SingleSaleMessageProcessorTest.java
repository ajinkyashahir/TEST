package com.jpmc.sales.processor;

import com.jpmc.sales.domain.DataStore;
import com.jpmc.sales.domain.Product;
import com.jpmc.sales.domain.message.SingleSale;
import com.jpmc.sales.exception.MessageProcessorException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class SingleSaleMessageProcessorTest {

    @Test
    public void whenFirstSingleSaleMessage_thenReturnUpdatedDataStore() throws MessageProcessorException {
        SingleSaleMessageProcessor singleSaleMessageProcessor = new SingleSaleMessageProcessor();
        DataStore dataStore = singleSaleMessageProcessor.processMessage(SingleSale.builder().productType("Orange").productPrice(BigDecimal.valueOf(35)).build()
                , new DataStore());
        Assert.assertEquals(1,dataStore.getProductSale().get("Orange").getQuantity().intValue());
        Assert.assertEquals(BigDecimal.valueOf(35),dataStore.getProductSale().get("Orange").getPrice());
    }

    @Test
    public void whenConsequentSingleSaleMessages_thenReturnUpdatedDataStore() throws MessageProcessorException {
        SingleSaleMessageProcessor singleSaleMessageProcessor = new SingleSaleMessageProcessor();
        DataStore dataStore = new DataStore();
        Map<String, Product> productSale = new HashMap<>();
        productSale.put("Apple", Product.builder().type("Apple").price(BigDecimal.valueOf(20)).quantity(4).build());
        productSale.put("Orange", Product.builder().type("Orange").price(BigDecimal.valueOf(30)).quantity(2).build());

        dataStore.setProductSale(productSale);
        dataStore = singleSaleMessageProcessor.processMessage( SingleSale.builder().productType("Orange").productPrice(BigDecimal.valueOf(35)).build()
                ,dataStore );

        Assert.assertEquals(4,dataStore.getProductSale().get("Apple").getQuantity().intValue());
        Assert.assertEquals(BigDecimal.valueOf(20),dataStore.getProductSale().get("Apple").getPrice());
        Assert.assertEquals(3,dataStore.getProductSale().get("Orange").getQuantity().intValue());
        Assert.assertEquals(BigDecimal.valueOf(30),dataStore.getProductSale().get("Orange").getPrice());
    }
}
