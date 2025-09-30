package com.uphouse.monew.domain.interest.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.uphouse.monew.domain.interest.domain.Interest;
import com.uphouse.monew.domain.interest.domain.QInterest;
import com.uphouse.monew.domain.interest.domain.QInterestKeyword;
import com.uphouse.monew.domain.interest.domain.QKeywords;
import com.uphouse.monew.domain.interest.dto.InterestDto;
import com.uphouse.monew.domain.interest.dto.InterestQueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class InterestKeywordCustomRepository {

    private final JPAQueryFactory queryFactory;



    public List<InterestDto> findInterestList(InterestQueryParams  params) {

        QInterest interest = QInterest.interest;
        QKeywords keywords = QKeywords.keywords;
        QInterestKeyword interestKeyword = QInterestKeyword.interestKeyword;

        // Where
        BooleanBuilder where = new BooleanBuilder();
        if(params.keyword() != null && !params.keyword().trim().isBlank()) where.and(keywords.keyword.eq(params.keyword()));

        // Order
        Order order = "desc".equalsIgnoreCase(params.direction()) ? Order.DESC : Order.ASC ;
        OrderSpecifier<?> orderSpecifier;

        switch (params.orderBy()) {
            case "name" -> orderSpecifier = new OrderSpecifier<>(order, interest.name);
            case "subscriberCount" -> orderSpecifier = new OrderSpecifier<>(order, interest.subscriberCount);
            default -> orderSpecifier = new OrderSpecifier<>(Order.ASC, interest.name);
        }

        List<Tuple> rows = queryFactory
                .select(interest.id, interest.name, keywords.keyword, interest.subscriberCount)
                .from(interestKeyword)
                .join(interest).on(interest.id.eq(interestKeyword.interest.id))
                .join(keywords).on(keywords.id.eq(interestKeyword.keywords.id))
                .where(where)
                .orderBy(orderSpecifier)
                .fetch();

        Map<Long, InterestDto> grouped = new HashMap<>();

        rows.forEach(row -> {
            Long id = row.get(interest.id);
            String name = row.get(interest.name);
            String keyword = row.get(keywords.keyword);
            int subscriberCount = row.get(interest.subscriberCount);

            grouped.computeIfAbsent(id, key -> new InterestDto(
                    id,
                    name,
                    new ArrayList<>(),   // 키워드 리스트 생성
                    subscriberCount,
                    false
            )).keywords().add(keyword); // 기존 DTO 의 키워드 리스트에 추가
        });

        return new ArrayList<>(grouped.values());
    }
}
