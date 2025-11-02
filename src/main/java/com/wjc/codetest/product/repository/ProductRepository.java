package com.wjc.codetest.product.repository;

import com.wjc.codetest.product.model.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByCategory(String name, Pageable pageable);

    @Query("SELECT DISTINCT p.category FROM Product p")
    List<String> findDistinctCategories();
    //문제: 레포지토리에서 페이징 처리 시 성능 이슈 가능.
    //원인: findAllByCategory는 Page<Product> 반환 시 연관 엔티티가 많다면 N+1 문제가 발생할 수 있음.
    //개선안: 필요 시 @EntityGraph 사용하거나 fetch join으로 연관 데이터를 미리 로딩하여 성능 개선.
    //(트레이드오프: 쿼리가 복잡해질 수 있으나 N+1 문제 방지 가능)
}
