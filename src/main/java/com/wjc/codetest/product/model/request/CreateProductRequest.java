package com.wjc.codetest.product.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequest {
    private String category;
    private String name;

    public CreateProductRequest(String category) {
        this.category = category;
    }

    public CreateProductRequest(String category, String name) {
        this.category = category;
        this.name = name;
    }
}
//문제: 생성자 오버로딩이 과도하여 유지보수성이 떨어짐.
//원인: 여러 생성자를 통해 객체를 초기화하려 하지만, DTO는 단순 데이터 전달 목적이라 불필요함.
//개선안: 모든 필드를 포함하는 단일 생성자 또는 @Builder 사용으로 명확하게 통일.
//(선택 근거: 코드 일관성 유지 및 테스트 시 가독성 향상)

