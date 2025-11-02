package com.wjc.codetest.product.model.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "name")
    private String name;

    protected Product() {
    }

    public Product(String category, String name) {
        this.category = category;
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }
    // 문제: 이미 @Getter가 있는데, 명시적으로 get 메서드 재정의 → 불필요한 코드 중복
    // 원인: Lombok + 수동 getter 혼용
    // 개선안: 중복된 getter 제거 / Lombok 일관성 유지
}
