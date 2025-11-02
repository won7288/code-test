package com.wjc.codetest.product.service;

import com.wjc.codetest.product.model.request.CreateProductRequest;
import com.wjc.codetest.product.model.request.GetProductListRequest;
import com.wjc.codetest.product.model.domain.Product;
import com.wjc.codetest.product.model.request.UpdateProductRequest;
import com.wjc.codetest.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(CreateProductRequest dto) {
        Product product = new Product(dto.getCategory(), dto.getName());
        return productRepository.save(product);
    }

    public Product getProductById(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("product not found");
        }
        return productOptional.get();
    }
    //문제: getProductById에서 Optional 체크 후 RuntimeException 직접 발생
    //원인: 존재하지 않는 경우를 단순 RuntimeException으로 처리함
    //개선안: Custom 예외(ProductNotFoundException)등등 생성 후 throw.
    //선택 근거: 예외 타입 명시로 가독성 향상, 서비스 계층에서 예외를 명확하게 구분

    public Product update(UpdateProductRequest dto) {
        Product product = getProductById(dto.getId());
        product.setCategory(dto.getCategory());
        product.setName(dto.getName());
        Product updatedProduct = productRepository.save(product);
        return updatedProduct;

    }

    public void deleteById(Long productId) {
        Product product = getProductById(productId);
        productRepository.delete(product);
    }
    //문제: deleteById 시 getProductById 호출 후 delete
    //원인: 존재 확인 후 delete 로직을 수동 처리
    //개선안: repository.deleteById(productId) + 예외 처리로 간소화 가능.
    //트레이드오프: 존재 여부 확인 로직을 repository 수준에서 처리할 수 있어 코드 간결

    public Page<Product> getListByCategory(GetProductListRequest dto) {
        PageRequest pageRequest = PageRequest.of(dto.getPage(), dto.getSize(), Sort.by(Sort.Direction.ASC, "category"));
        return productRepository.findAllByCategory(dto.getCategory(), pageRequest);
    }
    //문제: ProductListResponse와 마찬가지로 getListByCategory 반환 타입이 Page<Product> Entity를 직접 노출하고 있음
    //원인: Controller에서 그대로 Entity를 ProductListResponse에 담아 전달
    //개선안: Product → ProductResponse DTO 변환 후 반환
    //선택 근거: 엔티티 캡슐화, 직렬화 안정성, API 스펙 안전
    public List<String> getUniqueCategories() {
        return productRepository.findDistinctCategories();
    }
}