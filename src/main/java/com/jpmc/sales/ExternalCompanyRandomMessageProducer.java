package com.jpmc.sales;

import com.jpmc.sales.domain.message.Adjustment;
import com.jpmc.sales.domain.message.Message;
import com.jpmc.sales.domain.message.MultiSale;
import com.jpmc.sales.domain.message.SingleSale;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class ExternalCompanyRandomMessageProducer {


    public Message produceMessage() {
        Message message;
        Double randomNumber = (Math.random() * ((4 - 1) + 1));
        switch(randomNumber.intValue()){
            case 1: message = createRandomSingleSaleMessage();
            break;
            case 2: message = createRandomMultiSaleMessage();
            break;
            case 3: default: message = createRandomAdjustmentMessage();
            break;
        }
        return message;
    }


    private SingleSale createRandomSingleSaleMessage() {
        SingleSale singleSale = SingleSale.builder().build();
        Double randomNumber = (Math.random() * ((3 - 1) + 1));
        Double price = (Math.random() * (( 20 - 1) + 1));
        singleSale.setProductPrice(BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP));
        switch(randomNumber.intValue()) {
            case 1:
                singleSale.setProductType("Apple");
                break;
            case 2:
                singleSale.setProductType("Orange");
                break;
            case 3: default:
                singleSale.setProductType("Banana");
                break;
        }
        return singleSale;
    }

    private MultiSale createRandomMultiSaleMessage() {
        MultiSale multiSale = MultiSale.builder().build();
        Double randomNumber = (Math.random() * ((3 - 1) + 1));
        Double price = (Math.random() * (( 20 - 1) + 1));
        Double quantity = (Math.random() * (( 10 - 2) + 2));
        multiSale.setProductPrice(BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP));
        multiSale.setProductQuantity(quantity.intValue());
        switch(randomNumber.intValue()) {
            case 1:
                multiSale.setProductType("Apple");
                break;
            case 2:
                multiSale.setProductType("Orange");
                break;
            case 3: default:
                multiSale.setProductType("Banana");
                break;
        }
        return multiSale;
    }

    private Adjustment createRandomAdjustmentMessage() {
        Adjustment adjustment = Adjustment.builder().build();
        Double randomNumber = (Math.random() * ((3 - 1) + 1));
        Double price = (Math.random() * (( 5 - 1) + 1));
        adjustment.setProductAdjustmentValue(BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP));
        switch(randomNumber.intValue()) {
            case 1:
                adjustment.setProductType("Apple");
                break;
            case 2:
                adjustment.setProductType("Orange");
                break;
            case 3: default:
                adjustment.setProductType("Banana");
                break;
        }
        return adjustment;
    }
}
