package com.example.democrud.infrastructure.repository.database;

import com.example.democrud.application.dai.ProductRepository;
import com.example.democrud.input.InsertProductInput;
import com.example.democrud.model.InsertProductParam;
import com.example.democrud.model.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("DatabaseProductRepository")
public interface DatabaseProductRepository extends ProductRepository {

    @Override
    int countItems(@Param("keyword") String keyword);

    @Override
    List<Product> searchProducts(@Param("keyword") String keyword,
                                 @Param("order") String order,
                                 @Param("sizeOfPage") int sizeOfPage,
                                 @Param("offset") int offset);

    @Override
    Product findProductById(@Param("id") Integer id);

    @Override
    void saveProduct(@Param("product") InsertProductInput product);

    @Override
    void updateProductInfo(@Param("id") int id, @Param("product") Product product);

    @Override
    void deleteProduct(@Param("id") int id);
}
