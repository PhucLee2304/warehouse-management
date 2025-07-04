package org.example.warehousemanagement.controller;

import org.example.warehousemanagement.entity.ResponseData;
import org.example.warehousemanagement.interfaces.ProductInterface;
import org.example.warehousemanagement.request.AddProductRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
public class ProductController {
    private final ProductInterface productInterface;

    public ProductController(ProductInterface productInterface) {
        this.productInterface = productInterface;
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(@RequestPart("product")AddProductRequest request,
                                        @RequestPart(value = "image", required = true) MultipartFile image) throws IOException {
        ResponseData responseData = productInterface.addProduct(request, image);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody AddProductRequest request){
        ResponseData responseData = productInterface.updateProduct(id, request);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id){
        ResponseData responseData = productInterface.deleteProduct(id);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts(@RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "10") Integer size,
                                            @RequestParam(required = false) String keyword){
        ResponseData responseData = productInterface.getAllProducts(page, size, keyword);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
