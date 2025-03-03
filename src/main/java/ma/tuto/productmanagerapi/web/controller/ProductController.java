package ma.tuto.productmanagerapi.web.controller;

import jakarta.validation.Valid;
import ma.tuto.productmanagerapi.application.dto.ProductRequestDTO;
import ma.tuto.productmanagerapi.application.dto.ProductResponseDTO;
import ma.tuto.productmanagerapi.application.service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/products")
public class ProductController {

    // DI :---------------------------------------------------------------
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }
    // --------------------------------------------------------------------

    // POST
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProductCtrl(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productCreated = productService.createProductServ(productRequestDTO);
        return new ResponseEntity<>(productCreated, HttpStatus.CREATED);
    }

    // Get All Products
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProductsCtrl() {
        List<ProductResponseDTO> products = productService.getProductsServ();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Get One Product
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductCtrl(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getOneProductServ(id), HttpStatus.OK);
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProductCtrl(@PathVariable Long id, @Valid  @RequestBody ProductRequestDTO productRequestDTO) {
        return new ResponseEntity<>(productService.updateProductServ(id, productRequestDTO), HttpStatus.OK);
    }
}
