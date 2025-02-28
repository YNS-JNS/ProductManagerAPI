package ma.tuto.productmanagerapi.application.mapper.manual;

import ma.tuto.productmanagerapi.application.dto.CategoryDTO;
import ma.tuto.productmanagerapi.application.dto.ProductRequestDTO;
import ma.tuto.productmanagerapi.application.dto.ProductResponseDTO;
import ma.tuto.productmanagerapi.domain.model.Category;
import ma.tuto.productmanagerapi.domain.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductManualMapper {

    public ProductResponseDTO toDTO(Product productEntity) {
        if (productEntity == null) {
            return null;
        }
        ProductResponseDTO resDTO = new ProductResponseDTO();
        resDTO.setId(productEntity.getId());
        resDTO.setName(productEntity.getName());
        resDTO.setDescription(productEntity.getDescription());
        resDTO.setPrice(productEntity.getPrice());
        resDTO.setQuantity(productEntity.getQuantity());
        // Get category object
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(productEntity.getCategory().getId());
        categoryDTO.setName(productEntity.getCategory().getName());
        // Affect category object to:
        resDTO.setCategory(categoryDTO);

        return resDTO;
    }

    public Product toEntity(ProductRequestDTO reqDTO, Category category) {
        if (reqDTO == null || category == null) {
            return null;
        }

        Product product = new Product();
        product.setId(reqDTO.getId());
        product.setName(reqDTO.getName());
        product.setDescription(reqDTO.getDescription());
        product.setPrice(reqDTO.getPrice());
        product.setQuantity(reqDTO.getQuantity());
        product.setCategory(category);
        return product;
    }
}

