package com.jpmc.sales.domain.message;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class Message {
    String productType;
}
