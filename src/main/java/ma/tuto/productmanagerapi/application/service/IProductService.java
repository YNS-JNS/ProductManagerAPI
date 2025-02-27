package ma.tuto.productmanagerapi.application.service;

import ma.tuto.productmanagerapi.application.dto.ProductRequestDTO;
import ma.tuto.productmanagerapi.application.dto.ProductResponseDTO;

import java.util.List;

public interface IProductService {

    ProductResponseDTO createProductServ(ProductRequestDTO productRequestDTO);

    List<ProductResponseDTO> getProductsServ();
}

