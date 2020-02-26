package com.jpmc.sales.domain.message;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
public class SingleSale extends Message {

    private BigDecimal productPrice;

}
