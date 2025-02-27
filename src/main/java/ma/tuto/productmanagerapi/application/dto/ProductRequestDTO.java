package ma.tuto.productmanagerapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private Integer quantity;
    private Long categoryId; // Store only the category ID
}
