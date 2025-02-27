package ma.tuto.productmanagerapi.application.mapper;

import ma.tuto.productmanagerapi.application.dto.CategoryDTO;
import ma.tuto.productmanagerapi.domain.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryManualMapper {

    // Convert to DTO
    public CategoryDTO toDto(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    // Convert to ENTITY
    public Category toEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return null;
        }
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        return category;
    }
}

