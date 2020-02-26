package com.jpmc.sales.service;

import com.jpmc.sales.domain.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class ReportGenerationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportGenerationService.class);

    private static final int SALES_REPORT_MESSAGE_LIMIT = 10;
    private static final int ADJUSTMENT_REPORT_MESSAGE_COUNT = 50;
    private static final int ZERO = 0;

    public void printReport(DataStore dataStore) {
        if(dataStore.getMessageQueue().size() % SALES_REPORT_MESSAGE_LIMIT == ZERO) {
            generateSalesReport(dataStore);
        }
        if(dataStore.getMessageQueue().size() % ADJUSTMENT_REPORT_MESSAGE_COUNT == ZERO) {
            generateAdjustmentReport(dataStore);
        }
    }

    private void generateAdjustmentReport(DataStore dataStore) {

        Map<String, List<BigDecimal>> adjustments = dataStore.getAdjustments();
        LOGGER.info("**********Pausing Application*********");
        for(Map.Entry<String, List<BigDecimal>> entry: adjustments.entrySet()) {
            LOGGER.info("Product: {} Adjustments: {}" , entry.getKey(), entry.getValue());
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOGGER.error("THREAD sleep interrupted {}", e.getMessage());
        }

    }

    private void generateSalesReport(DataStore dataStore) {
        dataStore.getProductSale().values().forEach(product -> LOGGER.info(product.salesReportString()));
    }

}
