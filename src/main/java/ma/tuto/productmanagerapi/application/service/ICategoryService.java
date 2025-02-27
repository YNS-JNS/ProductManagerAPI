package ma.tuto.productmanagerapi.application.service;

import ma.tuto.productmanagerapi.application.dto.CategoryDTO;
import java.util.List;

public interface ICategoryService {

    // Create
    CategoryDTO createCategoryServ(CategoryDTO categoryDTO);

    // Get All
    List<CategoryDTO> getCategoriesServ();
}
