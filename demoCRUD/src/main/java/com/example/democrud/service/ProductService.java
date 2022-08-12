package com.example.democrud.service;

import com.example.democrud.application.dai.ProductRepository;
import com.example.democrud.controller.ApiExceptionHandler;
import com.example.democrud.infrastructure.repository.database.DatabaseProductRepository;
import com.example.democrud.input.SearchProductInput;
import com.example.democrud.model.ErrorMessage;
import com.example.democrud.model.Product;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

@Transactional
@Service("ApplicationProductService")
public class ProductService {
    private final DatabaseProductRepository databaseProductRepository;
    private static final String NAME_PATTERN = "^[a-zA-Z]+$";

    public ProductService(@Qualifier("DatabaseProductRepository") DatabaseProductRepository databaseProductRepository) {
        this.databaseProductRepository = databaseProductRepository;
    }

    /**
     * @param input SearchProductInput
     * @return List<Product>
     */
    public List<Product> searchProducts(SearchProductInput input) {
        int sizeOfPage = input.getLimit();
        if (sizeOfPage < 1) {
            sizeOfPage = 10;
        }

        String orderBy = standardOrder(inputOrder);
        String sortType = selectTypeSort(inputTypeSort);
        String order = orderBy + " " + sortType;

        int totalItem = databaseProductRepository.countItems(keyword);
        int totalPage;

        if (totalItem % sizeOfPage == 0) {
            totalPage = totalItem / sizeOfPage;
        } else {
            totalPage = totalItem / sizeOfPage + 1;
        }

        if (pageIndex > totalPage) {
            pageIndex = totalPage;
        }
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        int offset = (pageIndex - 1) * sizeOfPage;

        List<Product> list = databaseProductRepository.searchProducts(keyword, order, sizeOfPage, offset);
        if (CollectionUtils.isEmpty(list)) {
            throw new ErrorMessage(404, "Product Not Found!");
        }
        return list;
    }

    public Product findProductById(int id) {
        Product product = databaseProductRepository.findProductById(id);
        if (product == null) {
            throw new ErrorMessage(404, "Product Not Found!");
        }
        return product;
    }

    public void saveProduct(Product product) {
        if (!product.getName().matches(NAME_PATTERN)) {
            throw new ErrorMessage(500, "Internal Server Error");
        }
        databaseProductRepository.saveProduct(product);

    }

    public void updateProductInfo(int id, Product product) {
        if (!product.getName().matches(NAME_PATTERN)) {
            throw new ErrorMessage(500, "Internal Server Error");
        }
        databaseProductRepository.updateProductInfo(id, product);

    }

    public void deleteProduct(int id) {
        databaseProductRepository.deleteProduct(id);
    }

    public String standardOrder(String input) {
        if (StringUtils.isBlank(input)) {
            return "id";
        }
        switch (input) {
            case "id":
            case "name":
            case "description":
                return input;
            default:
                return "id";
        }
    }

    public String selectTypeSort(String input) {
        if ("desc".equals(input)) {
            return input;
        }
        return "asc";
    }

}
