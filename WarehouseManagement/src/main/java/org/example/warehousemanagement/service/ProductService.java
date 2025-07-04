package org.example.warehousemanagement.service;

import lombok.RequiredArgsConstructor;
import org.example.warehousemanagement.entity.Product;
import org.example.warehousemanagement.entity.ResponseData;
import org.example.warehousemanagement.interfaces.ProductInterface;
import org.example.warehousemanagement.repository.ProductRepository;
import org.example.warehousemanagement.request.AddProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductInterface {
    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public ResponseData addProduct(AddProductRequest request, MultipartFile image) {
        try{
            Product product = new Product();
            product.setName(request.getName());
            product.setStock(request.getStock());
            product.setPrice(request.getPrice());
            product.setDescription(request.getDescription());

            if (image != null && !image.isEmpty()) {
                try{
                    String imageUrl = (String) cloudinaryService.upload(image).get("secure_url");
                    product.setImage(imageUrl);
                } catch (Exception e) {
                    return ResponseData.error(e.getMessage());
                }
            }

            productRepository.save(product);

            return ResponseData.success("Add product successfully", product);

        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }

    @Override
    public ResponseData updateProduct(Integer id, AddProductRequest request) {
        try{
            Optional<Product> productOptional = productRepository.findById(id);
            if (productOptional.isEmpty()) {
                return ResponseData.error("Product not found");
            }

            Product product = productOptional.get();
            product.setName(request.getName());
            product.setStock(request.getStock());
            product.setPrice(request.getPrice());
            product.setDescription(request.getDescription());

            productRepository.save(product);

            return ResponseData.success("Update product successfully", product);

        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }

    @Override
    public ResponseData deleteProduct(Integer id) {
        try{
            if (!productRepository.existsById(id)) {
                return ResponseData.error("Product not found");
            }

            productRepository.deleteById(id);

            return ResponseData.success("Delete product successfully", null);

        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }

    @Override
    public ResponseData getAllProducts(Integer page, Integer size, String keyword) {
        try{
            PageRequest pageRequest = PageRequest.of(page, size);

            if (keyword != null && !keyword.trim().isEmpty()) {
                keyword = keyword.trim();
            }

            Page<Product> products = productRepository.getAllProducts(pageRequest, keyword);

            return ResponseData.success("Fetch all products successfully", products);

        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }


}
