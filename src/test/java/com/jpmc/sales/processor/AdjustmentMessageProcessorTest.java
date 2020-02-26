package com.jpmc.sales.processor;

import com.jpmc.sales.domain.DataStore;
import com.jpmc.sales.domain.Product;
import com.jpmc.sales.domain.message.Adjustment;
import com.jpmc.sales.exception.MessageProcessorException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class AdjustmentMessageProcessorTest {

    @Test
    public void whenAdjustmentMessage_thenReturnUpdatedDataStore() throws MessageProcessorException {
        AdjustmentMessageProcessor adjustmentMessageProcessor = new AdjustmentMessageProcessor();
        DataStore dataStore = adjustmentMessageProcessor.processMessage( Adjustment.builder().productAdjustmentValue(BigDecimal.valueOf(1.50)).productType("Apple").build()
                , new DataStore());
        Assert.assertEquals(BigDecimal.valueOf(1.50),dataStore.getAdjustments().get("Apple").get(0));
    }

    @Test
    public void whenMultipleAdjustmentMessages_thenReturnUpdatedDataStore() throws MessageProcessorException {
        AdjustmentMessageProcessor adjustmentMessageProcessor = new AdjustmentMessageProcessor();
        DataStore dataStore = new DataStore();
        Map<String, Product> productSale = new HashMap<>();
        productSale.put("Apple", Product.builder().type("Apple").price(BigDecimal.valueOf(20)).quantity(4).build());
        dataStore.setProductSale(productSale);
        dataStore = adjustmentMessageProcessor.processMessage( Adjustment.builder().productAdjustmentValue(BigDecimal.valueOf(2.50)).productType("Apple").build()
                ,dataStore );

        dataStore = adjustmentMessageProcessor.processMessage( Adjustment.builder().productAdjustmentValue(BigDecimal.valueOf(0.50)).productType("Apple").build()
                ,dataStore );
        Assert.assertEquals(BigDecimal.valueOf(2.50),dataStore.getAdjustments().get("Apple").get(0));
        Assert.assertEquals(BigDecimal.valueOf(0.50),dataStore.getAdjustments().get("Apple").get(1));
        Assert.assertEquals(4,dataStore.getProductSale().get("Apple").getQuantity().intValue());
        Assert.assertEquals(BigDecimal.valueOf(23.0),dataStore.getProductSale().get("Apple").getPrice());
    }
}
