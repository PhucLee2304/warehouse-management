package org.example.warehousemanagement.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddProductRequest {
    String name;
    double price;
    int stock;
    String description;
}
