package ma.tuto.productmanagerapi.web.controller;

import ma.tuto.productmanagerapi.application.dto.CategoryDTO;
import ma.tuto.productmanagerapi.application.service.ICategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/categories")
public class CategoryController {

    // DI :---------------------------------------------------------------
    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }
    // --------------------------------------------------------------------

    // POST
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategoryCtrl(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO categoryCreated = categoryService.createCategoryServ(categoryDTO);
        return new ResponseEntity<>(categoryCreated, HttpStatus.CREATED);
    }

    // Get
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategoriesCtrl() {
        List<CategoryDTO> categories = categoryService.getCategoriesServ();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
