package com.jpmc.sales.processor;

import com.jpmc.sales.domain.DataStore;
import com.jpmc.sales.domain.message.Message;
import com.jpmc.sales.domain.Product;
import com.jpmc.sales.domain.message.SingleSale;
import com.jpmc.sales.exception.MessageProcessorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class SingleSaleMessageProcessor implements MessageProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleSaleMessageProcessor.class);
    public static final int SINGLE_QUANTITY = 1;


    @Override
    public DataStore processMessage(Message message, DataStore dataStore) throws MessageProcessorException {
        LOGGER.debug("Single Sale");
        SingleSale singleSale = (SingleSale) message;
        if(singleSale.getProductType().isEmpty()) {
            throw new MessageProcessorException("Message Type cannot be null");
        }
        Optional<Product> product = dataStore.fetchProduct(message.getProductType());
        if(product.isPresent()) {
            product.get().setQuantity(product.get().getQuantity() + SINGLE_QUANTITY);
            dataStore.saveProduct(product.get());
        } else {
            dataStore.saveProduct(Product.createProduct(singleSale.getProductType(), singleSale.getProductPrice(), SINGLE_QUANTITY));
        }
        return dataStore;
    }
}
