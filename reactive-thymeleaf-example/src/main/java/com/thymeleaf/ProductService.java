package com.thymeleaf;

import com.thymeleaf.model.Product;
import com.thymeleaf.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<Product> searchAll(){
        Flux<Product> flux1 = productRepository.searchAll();
        Flux<Product> flux2 = productRepository.searchOthers();
        return Flux.merge(flux1,flux2);
    }
}
