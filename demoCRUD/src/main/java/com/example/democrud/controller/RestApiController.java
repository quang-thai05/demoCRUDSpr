package com.example.democrud.controller;

import com.example.democrud.input.SearchProductInput;
import com.example.democrud.model.SearchProductParam;
import com.example.democrud.model.Product;
import com.example.democrud.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RestApiController {

    private final ProductService service;

    public RestApiController(@Qualifier("ApplicationProductService") ProductService service) {
        this.service = service;
    }

    @GetMapping("/product")
    public List<Product> searchProducts(@Valid SearchProductParam parameter) {
        SearchProductInput input = new SearchProductInput(
                parameter.getKeyword(),
                parameter.getOrderColumn(),
                parameter.getTypeSort(),
                parameter.getPageIndex(),
                parameter.getSizeOfPage()
        );
        return service.searchProducts(input);
    }

    @GetMapping("/product/{id}")
    public Product findProductById(@PathVariable(name = "id") Integer id) {
        return service.findProductById(id);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        service.saveProduct(product);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProductInfo(@PathVariable(name = "id") Integer id, @RequestBody Product product) {
        service.updateProductInfo(id, product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") Integer id) {
        service.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
