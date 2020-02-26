package com.jpmc.sales.processor;

import com.jpmc.sales.domain.DataStore;
import com.jpmc.sales.domain.Product;
import com.jpmc.sales.domain.message.MultiSale;
import com.jpmc.sales.exception.MessageProcessorException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MultiSaleMessageProcessorTest {

    @Test
    public void whenFirstMultiSaleMessage_thenReturnUpdatedDataStore() throws MessageProcessorException {
        MultiSaleMessageProcessor multiSaleMessageProcessor = new MultiSaleMessageProcessor();
        DataStore dataStore = multiSaleMessageProcessor.processMessage(MultiSale.builder().productType("Banana").productPrice(BigDecimal.valueOf(35.98)).productQuantity(8).build()
                , new DataStore());
        Assert.assertEquals(8,dataStore.getProductSale().get("Banana").getQuantity().intValue());
        Assert.assertEquals(BigDecimal.valueOf(35.98),dataStore.getProductSale().get("Banana").getPrice());
    }

    @Test
    public void whenConsequentMultiSaleMessages_thenReturnUpdatedDataStore() throws MessageProcessorException {
        MultiSaleMessageProcessor multiSaleMessageProcessor = new MultiSaleMessageProcessor();
        DataStore dataStore = new DataStore();
        Map<String, Product> productSale = new HashMap<>();
        productSale.put("Apple", Product.builder().type("Apple").price(BigDecimal.valueOf(20)).quantity(4).build());
        productSale.put("Orange", Product.builder().type("Orange").price(BigDecimal.valueOf(35)).quantity(2).build());

        dataStore.setProductSale(productSale);
        dataStore = multiSaleMessageProcessor.processMessage( MultiSale.builder().productType("Orange").productPrice(BigDecimal.valueOf(35)).productQuantity(4).build()
                ,dataStore );

        Assert.assertEquals(4,dataStore.getProductSale().get("Apple").getQuantity().intValue());
        Assert.assertEquals(BigDecimal.valueOf(20),dataStore.getProductSale().get("Apple").getPrice());
        Assert.assertEquals(6,dataStore.getProductSale().get("Orange").getQuantity().intValue());
        Assert.assertEquals(BigDecimal.valueOf(35),dataStore.getProductSale().get("Orange").getPrice());
    }
}
