package com.wjc.codetest.product.model.response;

import com.wjc.codetest.product.model.domain.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author : 변영우 byw1666@wjcompass.com
 * @since : 2025-10-27
 */
@Getter
@Setter
public class ProductListResponse {
    private List<Product> products;
    //문제: Controller에서 ProductListResponse에 Entity(Product)를 직접 담아 반환하고 있음.
    //원인: Service 레이어 결과(Page<Product>)를 변환하지 않고 그대로 Response DTO에 주입함.
    //개선안: ProductResponse DTO를 별도로 생성해 Entity → DTO 변환 후 반환.
    //(선택 근거: 엔티티 캡슐화, API 안정성 확보, 직렬화 오류 예방)
    private int totalPages;
    private long totalElements;
    private int page;

    public ProductListResponse(List<Product> content, int totalPages, long totalElements, int number) {
        this.products = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.page = number;
    }
}
