package com.jpmc.sales.domain.message;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class MultiSale extends SingleSale {

    private Integer productQuantity;

}
