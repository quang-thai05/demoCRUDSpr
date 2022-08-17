package com.example.democrud.service;

import com.example.democrud.application.dai.ProductRepository;
import com.example.democrud.controller.ApiExceptionHandler;
import com.example.democrud.infrastructure.repository.database.DatabaseProductRepository;
import com.example.democrud.input.InsertProductInput;
import com.example.democrud.input.SearchProductInput;
import com.example.democrud.model.ErrorMessage;
import com.example.democrud.model.InsertProductParam;
import com.example.democrud.model.Product;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Transactional
@Service("ApplicationProductService")
public class ProductService {
    private final DatabaseProductRepository databaseProductRepository;
    private static final String NAME_PATTERN = "^[a-zA-Z\\s]+$";

    public ProductService(@Qualifier("DatabaseProductRepository") DatabaseProductRepository databaseProductRepository) {
        this.databaseProductRepository = databaseProductRepository;
    }

    /**
     * @param input SearchProductInput
     * @return List<Product>
     */
    public List<Product> searchProducts(SearchProductInput input) {
        int limit = input.getLimit();
        if (limit < 1) {
            limit = 10;
        }

        String orderBy = standardOrder(input.getOrderBy());
        String sortType = selectTypeSort(input.getSortBy());
        String order = orderBy + " " + sortType;

        int totalItem = databaseProductRepository.countItems(input.getKeyword());
        int totalPage;

        if (totalItem % limit == 0) {
            totalPage = totalItem / limit;
        } else {
            totalPage = totalItem / limit + 1;
        }

        int page = input.getPage();
        if (page > totalPage) {
            page = totalPage;
        }
        if (page < 1) {
            page = 1;
        }
        int offset = (page - 1) * limit;

        return databaseProductRepository.searchProducts(input.getKeyword(), order, limit, offset);
    }

    public Product findProductById(int id) {
        Product product = databaseProductRepository.findProductById(id);
        if (product == null) {
            throw new ErrorMessage(404, "Product Not Found!");
        }
        return product;
    }

    public void saveProduct(InsertProductInput product) {
        if (!product.getName().matches(NAME_PATTERN)) {
            throw new ErrorMessage(500, "Internal Server Error");
        }
        product.setCreatedBy("thai.dinhquang");
        product.setCreatedDate(14082022);
        product.setLastModifiedBy("thai.dinhquang");
        product.setLastModifiedDate(14082022);
        product.setActiveFlag(true);
        product.setDeleteFlag(false);
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
        if (StringUtils.isEmpty(input)) {
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
