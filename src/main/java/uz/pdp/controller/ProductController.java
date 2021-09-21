package uz.pdp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Product;
import uz.pdp.repository.ProductRepo;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductRepo productRepo;

    @PreAuthorize(value = "hasAuthority('getAll')")
    @GetMapping()
    public HttpEntity<?> getAll(){
        List<Product> all = productRepo.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(all);
    }

    //    @PreAuthorize(value = "hasAnyRole('DIRECTOR' , 'MANAGER' , 'USER')")
    @PreAuthorize(value = "hasAuthority('get')")
    @GetMapping("/{id}")
    public HttpEntity<?> get(@PathVariable Long id){
        Optional<Product> byId = productRepo.findById(id);
        return ResponseEntity.status(byId.isPresent()?HttpStatus.OK : HttpStatus.CONFLICT).body(byId);
    }

    //    @PreAuthorize(value = "hasAnyRole('DIRECTOR')")
    @PreAuthorize(value = "hasAuthority('save')")
    @PostMapping()
    public HttpEntity<?> save(@RequestBody Product product){
        Product save = productRepo.save(product);
        return ResponseEntity.status(HttpStatus.OK).body(save);
    }

    //    @PreAuthorize(value = "hasAnyRole('DIRECTOR')")
    @PreAuthorize(value = "hasAuthority('edit')")
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Long id , @RequestBody Product product){
        Product product1 = new Product();
        product.setName(product.getName());
        Product saved = productRepo.save(product1);
        return ResponseEntity.status(HttpStatus.OK).body(saved);
    }

    //    @PreAuthorize(value = "hasAnyRole('DIRECTOR')")
    @PreAuthorize(value = "hasAuthority('delete')")
    @DeleteMapping("/{id}")
    HttpEntity<?> delete(@PathVariable Long id){
        productRepo.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("delete");
    }
}
