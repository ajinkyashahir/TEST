package com.jpmc.sales.processor;

import com.jpmc.sales.domain.DataStore;
import com.jpmc.sales.domain.Product;
import com.jpmc.sales.domain.message.Message;
import com.jpmc.sales.domain.message.MultiSale;
import com.jpmc.sales.exception.MessageProcessorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class MultiSaleMessageProcessor implements MessageProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiSaleMessageProcessor.class);


    @Override
    public DataStore processMessage(Message message, DataStore dataStore) throws MessageProcessorException {
        MultiSale multisale = (MultiSale) message;
        if(multisale.getProductType().isEmpty()) {
            throw new MessageProcessorException("Message Type cannot be null");
        }
        LOGGER.debug("Multi Sale of {} at {}", multisale.getProductQuantity(), multisale.getProductPrice());
        Optional<Product> product = dataStore.fetchProduct(message.getProductType());
        if(product.isPresent()){
            product.get().setQuantity(product.get().getQuantity() + multisale.getProductQuantity());
            dataStore.saveProduct(product.get());
        } else {
            dataStore.saveProduct(Product.createProduct(multisale.getProductType(), multisale.getProductPrice(), multisale.getProductQuantity()));
        }
        return dataStore;
    }
}
