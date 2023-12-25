package com.example.springboot.Controllers;

import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.model.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct (@RequestBody @Valid ProductRecordDto productRecordDto) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto,productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));

    }

    @GetMapping("/products")
    public ResponseEntity <List<ProductModel>> getAllproducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());

            }

    @GetMapping("/products/{id}")
    public ResponseEntity <Object> getOneproduct(@PathVariable (value="id") UUID id){

        Optional<ProductModel> productO = productRepository.findById(id);
        if(productO.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body("Product not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(productO.get());

    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> UpdateProduct(@PathVariable (value ="id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto){
        Optional<ProductModel> productO = productRepository.findById(id);
        if(productO.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body("Product not found");
        }
        var productModel = productO.get();
        BeanUtils.copyProperties(productRecordDto,productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));


    }




}
