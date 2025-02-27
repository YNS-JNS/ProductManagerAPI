package ma.tuto.productmanagerapi.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required") // Cannot be null, empty, or just spaces
    @Size(max = 100, message = "Product name cannot exceed 100 characters") // Prevents excessively long values
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description cannot exceed 255 characters") // Limits description size
    private String description;

    @NotNull(message = "Price is required") // Must always be provided
    @Positive(message = "Price must be a positive number") // Prevents negative or zero values
    private Double price;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative") // Allows 0 but no negative values
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull(message = "Category is required") // A product must belong to a category
    private Category category;
}
