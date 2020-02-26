package com.jpmc.sales.processor;

import com.jpmc.sales.domain.DataStore;
import com.jpmc.sales.domain.Product;
import com.jpmc.sales.domain.message.Adjustment;
import com.jpmc.sales.domain.message.Message;
import com.jpmc.sales.exception.MessageProcessorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class AdjustmentMessageProcessor implements MessageProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdjustmentMessageProcessor.class);

    @Override
    public DataStore processMessage(Message message, DataStore dataStore) throws MessageProcessorException {
        Adjustment adjustment = (Adjustment) message;
        if(adjustment.getProductType().isEmpty()) {
            throw new MessageProcessorException("Message Type cannot be null");
        }
        LOGGER.debug("Adjustment of: {} ", adjustment.getProductAdjustmentValue());
        dataStore.saveAdjustment(adjustment);
        Optional<Product> product = dataStore.fetchProduct(message.getProductType());
        product.ifPresent(prdct -> {
            prdct.setPrice(prdct.getPrice().add(adjustment.getProductAdjustmentValue()));
            dataStore.updateSales(prdct);
        });

        return dataStore;
    }
}
