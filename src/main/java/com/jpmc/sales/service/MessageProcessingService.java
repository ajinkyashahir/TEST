package com.jpmc.sales.service;

import com.jpmc.sales.domain.DataStore;
import com.jpmc.sales.domain.message.Message;
import com.jpmc.sales.exception.MessageProcessorException;
import com.jpmc.sales.processor.MessageProcessor;
import com.jpmc.sales.processor.MessageProcessorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProcessingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProcessingService.class);

    @Autowired
    private DataStore dataStore;

    @Autowired
    private ReportGenerationService reportGenerationService;

    public void processMessage(Message message) {
        LOGGER.debug("Started message processing for {}", message.getProductType());
        dataStore.getMessageQueue().add(message);
        try {
            MessageProcessor messageProcessor = MessageProcessorFactory.getProcessor(message);
            dataStore = messageProcessor.processMessage(message, dataStore);
            reportGenerationService.printReport(dataStore);
            LOGGER.debug("Completed message processing for {}", message.getProductType());
        }  catch (MessageProcessorException e) {
            LOGGER.error("Error processing message {}", e.getMessage());
        }
    }




}
