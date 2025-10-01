package com.uphouse.monew.domain.interest.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.uphouse.monew.domain.interest.domain.*;
import com.uphouse.monew.domain.interest.dto.InterestDto;
import com.uphouse.monew.domain.interest.dto.InterestQueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class InterestKeywordCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final KeywordRepository keywordRepository;

    /**
     * 키워드 없이 일반 조회, order by 만
     */
    public List<InterestDto> findInterestBySearch(InterestQueryParams params) {

        QInterest interest = QInterest.interest;

        OrderSpecifier<?> orderSpecifier = makeOrderSpecifier(params.direction(), params.orderBy());

        // 관심사 목록 조회
        List<Interest> interests = queryFactory.selectFrom(interest)
                .orderBy(orderSpecifier)
                .limit(params.limit() + 1)
                .fetch();

        return findInterestKeyword(interests);
    }

    /**
     * 키워드로 찾은 관심사
     * */
    public List<InterestDto> findInterestsByKeyword(InterestQueryParams params) {

        if (!keywordRepository.existsByKeyword(params.keyword())) {
            return Collections.emptyList();
        }

        QInterestKeyword ik = QInterestKeyword.interestKeyword;
        QInterest i = QInterest.interest;
        QKeywords k = QKeywords.keywords;

        OrderSpecifier<?> orderSpecifier = makeOrderSpecifier(params.direction(), params.orderBy());

        // 키워드로 찾은 관심사 목록
        List<Interest> interests = queryFactory
                .select(i)
                .from(ik)
                .join(ik.interest, i)
                .join(ik.keywords, k)
                .where(k.keyword.eq(params.keyword()))
                .orderBy(orderSpecifier)
                .limit(params.limit() + 1)
                .fetch();

        return findInterestKeyword(interests);
    }

    /**
     * 관심사(Interest) id로 키워드 찾을 다음 DTO로 변환
     */
    private List<InterestDto> findInterestKeyword(List<Interest> interests) {

        // 2) 관심사 아이디만 추출
        List<Long> interestIds = interests.stream().map(Interest::getId).toList();

        QInterestKeyword ik = QInterestKeyword.interestKeyword;
        QKeywords k = QKeywords.keywords;

        List<Tuple> ikTuples = queryFactory
                .select(ik.interest.id, k.keyword)
                .from(ik)
                .join(ik.keywords, k)
                .where(ik.interest.id.in(interestIds))
                .fetch();

        // 4) interestId -> keywordList 맵핑
        Map<Long, List<String>> keywordMap = ikTuples.stream()
                .collect(Collectors.groupingBy(
                        t -> t.get(ik.interest.id),
                        Collectors.mapping(t -> t.get(k.keyword), Collectors.toList())
                ));

        return interests.stream()
                .map(in -> InterestDto.builder()
                        .id(in.getId())
                        .name(in.getName())
                        .subscriberCount(in.getSubscriberCount())
                        .keywords(keywordMap.getOrDefault(in.getId(), List.of()))
                        .subscribedByMe(false) // TODO: userId 기반 로직 추가
                        .build())
                .toList();
    }

    /**
     * 동적으로 ORDER BY 쿼리 생성
     * @param direction - DESC, ASC
     * @param orderBy - name, subscriberCount
     */
    private OrderSpecifier<?> makeOrderSpecifier(String direction, String orderBy) {

        QInterest interest = QInterest.interest;

        Order order = "desc".equalsIgnoreCase(direction) ? Order.DESC : Order.ASC ;
        OrderSpecifier<?> orderSpecifier;

        switch (orderBy) {
            case "name" -> orderSpecifier = new OrderSpecifier<>(order, interest.name);
            case "subscriberCount" -> orderSpecifier = new OrderSpecifier<>(order, interest.subscriberCount);
            default -> orderSpecifier = new OrderSpecifier<>(order, interest.name);
        }

        return orderSpecifier;
    }
}
