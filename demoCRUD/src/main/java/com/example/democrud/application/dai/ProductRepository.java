package com.example.democrud.application.dai;

import com.example.democrud.model.InsertProductParam;
import com.example.democrud.model.Product;

import java.util.List;

public interface ProductRepository {

    int countItems(String keyword);

    List<Product> searchProducts(String keyword, String order, int sizeOfPage, int offset);

    Product findProductById(Integer id);

    void saveProduct(InsertProductParam product);

    void updateProductInfo(int id, Product product);

    void deleteProduct(int id);

}
