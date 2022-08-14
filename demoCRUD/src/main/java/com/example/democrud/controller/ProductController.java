package com.example.democrud.controller;

import com.example.democrud.input.InsertProductInput;
import com.example.democrud.input.SearchProductInput;
import com.example.democrud.model.InsertProductParam;
import com.example.democrud.model.SearchProductParam;
import com.example.democrud.model.Product;
import com.example.democrud.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService service;

    private final ModelMapper mapper;

    public ProductController(@Qualifier("ApplicationProductService") ProductService service,
                             @Qualifier("ModelMapper") ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/product")
    public List<Product> searchProducts(@Valid SearchProductParam parameter) {
//        SearchProductInput input = new SearchProductInput(
//                parameter.getKeyword(),
//                parameter.getOrderColumn(),
//                parameter.getTypeSort(),
//                parameter.getPageIndex(),
//                parameter.getSizeOfPage()
//        );

        SearchProductInput input = new SearchProductInput();
        mapper.map(parameter, input);

//        SearchProductInput input = new SearchProductInput();
//        service.convertParamToInput(parameter, input);
        return service.searchProducts(input);
    }

    @GetMapping("/product/{id}")
    public Product findProductById(@PathVariable(name = "id") Integer id) {
        return service.findProductById(id);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestBody InsertProductParam product) {
//        InsertProductInput input = new InsertProductInput(
//                product.getName(),
//                product.getDescription(),
//                product.getOrgId()
//        );

        InsertProductInput input = new InsertProductInput();
        mapper.map(product, input);
        service.saveProduct(input);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProductInfo(@PathVariable(name = "id") Integer id,
                                               @RequestBody Product product) {
        service.updateProductInfo(id, product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") Integer id) {
        service.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
