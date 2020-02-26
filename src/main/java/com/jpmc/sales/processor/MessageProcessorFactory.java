package com.jpmc.sales.processor;

import com.jpmc.sales.domain.message.Adjustment;
import com.jpmc.sales.domain.message.Message;
import com.jpmc.sales.domain.message.MultiSale;

public class MessageProcessorFactory {
    public static MessageProcessor getProcessor(Message message) {
        if(message instanceof Adjustment) {
            return new AdjustmentMessageProcessor();
        } else if(message instanceof MultiSale) {
            return new MultiSaleMessageProcessor();
        } else {
            return new SingleSaleMessageProcessor();
        }
    }
}
