package ma.tuto.productmanagerapi.application.service.impl;

import ma.tuto.productmanagerapi.application.dto.CategoryDTO;
import ma.tuto.productmanagerapi.application.mapper.manual.CategoryManualMapper;
import ma.tuto.productmanagerapi.domain.model.Category;
import ma.tuto.productmanagerapi.domain.repository.CategoryRepository;
import ma.tuto.productmanagerapi.application.service.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryService {

    // DI :---------------------------------------------------------------
    private final CategoryRepository categoryRepository;
    private final CategoryManualMapper categoryManualMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryManualMapper categoryManualMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryManualMapper = categoryManualMapper;
    }
    // -------------------------------------------------------------------

    // Create New Category Service
    @Override
    public CategoryDTO createCategoryServ(CategoryDTO categoryDTO) {
        Category categoryEntity = categoryManualMapper.toEntity(categoryDTO);
        return categoryManualMapper.toDto(categoryRepository.save(categoryEntity));
    }

    // Get All Categories Service
    @Override
    public List<CategoryDTO> getCategoriesServ() {
        return categoryRepository.findAll().stream()
                .map(categoryManualMapper::toDto)
                .collect(Collectors.toList());
    }

}

