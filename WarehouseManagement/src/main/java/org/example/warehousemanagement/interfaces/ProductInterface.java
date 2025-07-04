package org.example.warehousemanagement.interfaces;

import org.example.warehousemanagement.entity.ResponseData;
import org.example.warehousemanagement.request.AddProductRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ProductInterface {
    ResponseData addProduct(AddProductRequest request, MultipartFile image);
    ResponseData updateProduct(Integer id, AddProductRequest request);
    ResponseData deleteProduct(Integer id);
    ResponseData getAllProducts(Integer page, Integer size, String keyword);
}
