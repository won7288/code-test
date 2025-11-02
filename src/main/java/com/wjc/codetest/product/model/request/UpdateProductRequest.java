package com.wjc.codetest.product.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductRequest {
    private Long id;
    private String category;
    private String name;

    public UpdateProductRequest(Long id) {
        this.id = id;
    }

    public UpdateProductRequest(Long id, String category) {
        this.id = id;
        this.category = category;
    }

    public UpdateProductRequest(Long id, String category, String name) {
        this.id = id;
        this.category = category;
        this.name = name;
    }
}
// 문제: CreateProductRequest와 마찬가지로 생성자 오버로딩이 많아 혼동 가능성 증가, 유지보수 어려움.
// 원인: 다양한 초기화 방식을 고려했지만, 실제 사용처는 대부분 단일 형태일 가능성 높음.
// 개선안: 모든 필드를 받는 하나의 생성자만 유지하거나 @Builder 패턴으로 대체.
