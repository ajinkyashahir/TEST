package com.jpmc.sales.processor;

import com.jpmc.sales.domain.DataStore;
import com.jpmc.sales.exception.MessageProcessorException;
import com.jpmc.sales.domain.message.Message;

public interface MessageProcessor {

    DataStore processMessage(Message message, DataStore dataStore) throws MessageProcessorException;
}