package com.jpmc.sales.domain;

import com.jpmc.sales.domain.message.Adjustment;
import com.jpmc.sales.domain.message.Message;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Data
@Repository
@NoArgsConstructor
public class DataStore {
    private Queue<Message> messageQueue = new LinkedList<>();
    private Map<String, Product> productSale = new HashMap<>();
    private Map<String, List<BigDecimal>> adjustments = new HashMap<>();

    public void saveAdjustment(Adjustment adjustment){
        List<BigDecimal> adjustmentValues = new ArrayList<>();
        if(adjustments.get(adjustment.getProductType()) == null){
            adjustmentValues.add(adjustment.getProductAdjustmentValue());
            adjustments.put(adjustment.getProductType(), adjustmentValues);
        } else {
            adjustments.get(adjustment.getProductType()).add(adjustment.getProductAdjustmentValue());
        }
    }

    public void updateSales(Product product) {
        productSale.put(product.getType(), product);

    }



    public Optional<Product> fetchProduct(String productType) {
       return  Optional.ofNullable(productSale.get(productType));
    }

    public void saveProduct(Product product) {
        productSale.put(product.getType(), product);
    }
}
