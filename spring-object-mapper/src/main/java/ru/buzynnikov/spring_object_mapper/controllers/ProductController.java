package ru.buzynnikov.spring_object_mapper.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;
import ru.buzynnikov.spring_object_mapper.mappers.ProductMapper;
import ru.buzynnikov.spring_object_mapper.models.Product;
import ru.buzynnikov.spring_object_mapper.services.ProductService;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    private final ProductMapper mapper;


    public ProductController(ProductService productService, ProductMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<String> getProducts(){
        return ResponseEntity.ok(mapper.toListJson(productService.getAllProducts()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(mapper.toJson(productService.getProductById(id)));
    }
    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody String json, UriComponentsBuilder builder){
        Product product = productService.createProduct(mapper.toProduct(json));
        return ResponseEntity
                .created(builder.path("/products/{id}").buildAndExpand(product.getProductId()).toUri())
                .body(mapper.toJson(product));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @RequestBody String json){
        productService.updateProduct(mapper.toProduct(json), id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

}

