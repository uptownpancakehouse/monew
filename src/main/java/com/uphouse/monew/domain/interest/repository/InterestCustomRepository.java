package com.uphouse.monew.domain.interest.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.uphouse.monew.domain.interest.domain.Interest;
import com.uphouse.monew.domain.interest.domain.QInterest;
import com.uphouse.monew.domain.interest.dto.InterestQueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InterestCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final KeywordRepository keywordRepository;

    public List<Interest> findByInterestName(InterestQueryParams params) {
        QInterest interest = QInterest.interest;

        BooleanBuilder where = new BooleanBuilder();

        // keyword 조건 추가
        if (params.keyword() != null && !params.keyword().trim().isEmpty()) {
            where.and(interest.name.containsIgnoreCase(params.keyword()));
        }

        OrderSpecifier<?> orderSpecifier = makeOrderSpecifier(params.direction(), params.orderBy());

        return queryFactory.selectFrom(interest)
                .where(where)
                .orderBy(orderSpecifier)
                .limit(params.limit() + 1)
                .fetch();
    }

    /**
     * 동적으로 ORDER BY 쿼리 생성
     * @param direction - DESC, ASC
     * @param orderBy - name, subscriberCount
     */
    private OrderSpecifier<?> makeOrderSpecifier(String direction, String orderBy) {
        QInterest interest = QInterest.interest;

        Order order = "desc".equalsIgnoreCase(direction) ? Order.DESC : Order.ASC;
        OrderSpecifier<?> orderSpecifier;

        switch (orderBy) {
            case "name" -> {
                // 한글/영문 혼합 정렬 안정화를 위해 lower() 적용
                orderSpecifier = new OrderSpecifier<>(order, interest.name.lower());
            }
            case "subscriberCount" -> {
                orderSpecifier = new OrderSpecifier<>(order, interest.subscriberCount);
            }
            default -> {
                orderSpecifier = new OrderSpecifier<>(order, interest.name.lower());
            }
        }

        return orderSpecifier;
    }

}
