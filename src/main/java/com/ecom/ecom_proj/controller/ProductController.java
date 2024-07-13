package com.ecom.ecom_proj.controller;

import com.ecom.ecom_proj.model.Product;
import com.ecom.ecom_proj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
@CrossOrigin // to prevent cors error
@RequestMapping("/api") // base url. every mapping in the class methods will have a /api before it
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/products")
    // ResponseEntity is required to send the status along with the data
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = service.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        Product product = service.getProductById(id);
        if(product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
        // @RequestPart to get parts of the body
        // @RequestBody gets the entire body as a single json

        // product details and image is sent from the client as two objects separately

        try {
            Product product1 = service.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);

        }
        catch(Exception e) {
            System.out.println("\n Error in controller \n");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId) {
        Product product = service.getProductById(productId);
        byte[] imageFile = product.getImageData();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile) {
        try {
            Product prod = service.updateProduct(id, product, imageFile);
        } catch (IOException e) {
//            throw new RuntimeException(e);
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
        }

//        if(product != null) {
//            return new ResponseEntity<>("Updated", HttpStatus.OK);
//        }
//        else {
//            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
//        }

        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        Product product = service.getProductById(id);
        if(product != null) {
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }
}
