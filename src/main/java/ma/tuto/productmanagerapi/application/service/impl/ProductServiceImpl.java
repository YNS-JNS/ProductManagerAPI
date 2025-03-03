package ma.tuto.productmanagerapi.application.service.impl;

import jakarta.transaction.Transactional;
import ma.tuto.productmanagerapi.application.dto.ProductRequestDTO;
import ma.tuto.productmanagerapi.application.dto.ProductResponseDTO;
import ma.tuto.productmanagerapi.application.mapper.manual.ProductManualMapper;
import ma.tuto.productmanagerapi.common.exception.CategoryNotFoundException;
import ma.tuto.productmanagerapi.common.exception.ProductNotFoundException;
import ma.tuto.productmanagerapi.domain.model.Category;
import ma.tuto.productmanagerapi.domain.model.Product;
import ma.tuto.productmanagerapi.domain.repository.CategoryRepository;
import ma.tuto.productmanagerapi.domain.repository.ProductRepository;
import ma.tuto.productmanagerapi.application.service.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    // DI :---------------------------------------------------------------
    private final ProductRepository productRepository;
    private final ProductManualMapper productManualMapper;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductManualMapper productManualMapper, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productManualMapper = productManualMapper;
        this.categoryRepository = categoryRepository;
    }
    // -------------------------------------------------------------------

    @Override
    public ProductResponseDTO createProductServ(ProductRequestDTO productRequestDTO) {
        // Check if category exists
        // Category category = categoryRepository.findById(productRequestDTO.getCategoryId())
        // .orElseThrow(()-> new RuntimeException("Category not found") );

        // Get Category id from productRequestDTO
        Long categoryId = productRequestDTO.getCategoryId();
        // Check if category exists
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        // Conversion DTO to Entity
        Product product = productManualMapper.toEntity(productRequestDTO, category);
        return productManualMapper.toDTO(productRepository.save(product));
    }

    @Override
    public List<ProductResponseDTO> getProductsServ() {
        return productRepository.findAll().stream()
                //.map( prod -> productManualMapper.toDTO(prod))
                .map(productManualMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO getOneProductServ(Long id) {
        Product productFound = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return productManualMapper.toDTO(productFound);
    }

    @Override
    public ProductResponseDTO updateProductServ(Long id, ProductRequestDTO productRequestDTO) {
        // Check the product existence or else throw error message :
        Product productFound = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        // If product exists then do this :
        productFound.setName(productRequestDTO.getName());
        productFound.setDescription(productRequestDTO.getDescription());
        productFound.setPrice(productRequestDTO.getPrice());
        productFound.setQuantity(productRequestDTO.getQuantity());

        return productManualMapper.toDTO(productRepository.save(productFound));
    }
}
