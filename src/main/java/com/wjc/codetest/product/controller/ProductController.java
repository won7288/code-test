package com.wjc.codetest.product.controller;

import com.wjc.codetest.product.model.request.CreateProductRequest;
import com.wjc.codetest.product.model.request.GetProductListRequest;
import com.wjc.codetest.product.model.domain.Product;
import com.wjc.codetest.product.model.request.UpdateProductRequest;
import com.wjc.codetest.product.model.response.ProductListResponse;
import com.wjc.codetest.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping(value = "/get/product/by/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable(name = "productId") Long productId){
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @PostMapping(value = "/create/product")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest dto){
        Product product = productService.create(dto);
        return ResponseEntity.ok(product);
    }
    // 위의 Get과 Post mapping에 관하여
    // 문제1: 엔티티(Product)를 그대로 반환하고 있음 → API 응답 모델로 적절하지 않음 (보안/확장성 문제)
    // 원인1: DTO 변환 레이어 부재로 인해 Entity를 직접 ResponseEntity로 감싸 반환
    // 개선안1: Response DTO (e.g. ProductResponse)를 별도로 만들어, 노출할 필드만 반환
    // 문제2: POST 메서드로 생성 후 200 OK 반환 → REST 관점에서 부적절
    // 원인2: HTTP 상태 코드 설계 미흡
    // 개선안2: 생성 성공 시 201 Created와 Location 헤더로 자원 위치 반환

    @PostMapping(value = "/delete/product/{productId}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable(name = "productId") Long productId){
        productService.deleteById(productId);
        return ResponseEntity.ok(true);
    }
    // 문제: 삭제인데 POST 사용 → RESTful하지 않음 (DELETE 사용이 적절)
    // 원인: HTTP 메서드 선택 기준 불분명
    // 개선안: @DeleteMapping("/product/{productId}") 사용

    @PostMapping(value = "/update/product")
    public ResponseEntity<Product> updateProduct(@RequestBody UpdateProductRequest dto){
        Product product = productService.update(dto);
        return ResponseEntity.ok(product);
    }
    // 문제: PUT/PATCH 대신 POST 사용 → 자원의 수정 의미가 불명확, 위와 마찬가지로 Entity(Product)를 그대로 반환하고 있음
    // 원인: HTTP 메서드의 의미적 차이 미고려, DTO 변환 레이어 부재
    // 개선안: 수정은 PUT(전체 교체) 또는 PATCH(부분 수정) 사용, 앞서말한 Response DTO를 별도로 만들어, 노출할 필드만 반환

    @PostMapping(value = "/product/list")
    public ResponseEntity<ProductListResponse> getProductListByCategory(@RequestBody GetProductListRequest dto){
        Page<Product> productList = productService.getListByCategory(dto);
        return ResponseEntity.ok(new ProductListResponse(productList.getContent(), productList.getTotalPages(), productList.getTotalElements(), productList.getNumber()));
    }
    // 문제: 조회인데 POST 사용 → 캐싱 불가 및 REST 규약 위반
    // 원인: 단순 구현 편의로 POST 사용, DTO 변환 레이어 부재
    // 개선안: @GetMapping + @RequestParam(category, page 등) 으로 변경, 앞서말한 Response DTO를 별도로 만들어, 노출할 필드만 반환
    // 트레이드오프: 복잡한 검색 조건이 많다면 POST 유지 가능 (단, 명시적으로 설계 문서에 표기)

    @GetMapping(value = "/product/category/list")
    public ResponseEntity<List<String>> getProductListByCategory(){
        List<String> uniqueCategories = productService.getUniqueCategories();
        return ResponseEntity.ok(uniqueCategories);
    }
    // 문제: 메서드명 중복사용
    // 원인: 위에 메서드와 동일한 네이밍으로 중복 및 일관성 부족
    // 개선안: 메서드명을 서로 다르게 작성 구별이 잘 가도록
}