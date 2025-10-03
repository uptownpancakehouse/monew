package com.uphouse.monew.domain.interest.service;

import com.uphouse.monew.domain.interest.domain.Interest;
import com.uphouse.monew.domain.interest.domain.InterestKeyword;
import com.uphouse.monew.domain.interest.domain.Keywords;
import com.uphouse.monew.domain.interest.domain.UserInterest;
import com.uphouse.monew.domain.interest.dto.InterestCreateRequest;
import com.uphouse.monew.domain.interest.dto.InterestDto;
import com.uphouse.monew.domain.interest.dto.InterestQueryParams;
import com.uphouse.monew.domain.interest.dto.InterestSubscribeResponse;
import com.uphouse.monew.domain.interest.repository.*;
import com.uphouse.monew.domain.user.domain.User;
import com.uphouse.monew.domain.user.repository.UserRepository;
import com.uphouse.monew.global.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;
    private final KeywordRepository keywordRepository;
    private final InterestKeywordRepository interestKeywordRepository;
    private final InterestKeywordCustomRepository interestKeywordCustomRepository;
    private final UserRepository userRepository;
    private final UserInterestRepository userInterestRepository;

    @Transactional
    public InterestDto create(InterestCreateRequest request) {

        Interest interest = saveInterest(request.name());    // 관심사 저장
        List<String> keywordsList = saveKeywords(interest, request.keywords());          // 키워드 저장

        return InterestDto.builder()
                .id(interest.getId())
                .name(interest.getName())
                .keywords(keywordsList)
                .subscriberCount(0)
                .subscribedByMe(false)
                .build();
    }

    @Transactional(readOnly = true)
    public PageResponse getInterests(UUID userId, InterestQueryParams params) {

        Long total = 0L;
        List<InterestDto> interestList = new ArrayList<>();

        if(params.keyword() == null || params.keyword().isEmpty()){
            total = interestRepository.countTotal();
            interestList = interestKeywordCustomRepository.findInterestBySearch(params);
        }

        total = interestKeywordRepository.countInterestsByKeyword(params.keyword());
        interestList = interestKeywordCustomRepository.findInterestsByKeyword(params);

        String nextCursor = null;
        String nextAfter = null;
        boolean hasNext = interestList.size() > params.limit();

        if(hasNext) {
            InterestDto lastItem = interestList.get(interestList.size() - 1);
            nextCursor = lastItem.id().toString();
            interestList = interestList.subList(0, params.limit()); // 초과분 제거
        }

        return PageResponse.builder()
                .content(interestList)
                .nextCursor(nextCursor)
                .size(interestList.size() - 1)
                .totalElements(total)
                .hasNext(hasNext)
                .build();
    }

    public InterestDto update(Long interestId, Set<String> keywords) {

        Interest interest = interestRepository.findById(interestId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 관심사: " + interestId));

        List<String> keywordsList = saveKeywords(interest, keywords);

        return InterestDto.builder()
                .id(interest.getId())
                .name(interest.getName())
                .keywords(keywordsList)
                .subscriberCount(0)
                .subscribedByMe(false)
                .build();
    }

    public InterestSubscribeResponse subscribe(Long interestId, UUID userId) {
        Interest interest = interestRepository.findById(interestId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 관심사 입니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 입니다."));

        UserInterest userInterest = interestSubscribe(user, interest); // 구독하기
        int interestSubscriberCount = userInterestRepository.countByInterestAndSubscribedByMeTrue(interest); // 구독자 수 카운트
        interest.subscriberCount(interestSubscriberCount); // 구독자 증가
        interestRepository.save(interest);

        List<String> keywordsList = getKeywordsName(interest.getId());

        return InterestSubscribeResponse.builder()
                .id(user.getId())
                .interestId(interest.getId())
                .interestName(interest.getName())
                .interestKeywords(keywordsList)
                .interestSubscriberCount(interestSubscriberCount)
                .createdAt(interest.getCreatedAt())
                .build();
    }

    private Interest saveInterest(String name) {

        // 완전 동일한 관심사 존재 여부 - 존재하면 찾은 관심사 return
        Interest interest = interestRepository.findByName(name).orElse(null);

        if (interest != null) {
            return interest;
        }

        // 2. 유사 관심사 존재 여부 (80% 이상)
        if (interestRepository.existsBySimilarName(name)) {
            throw new IllegalArgumentException("유사한 이름의 관심사가 이미 존재합니다.");
        }

        return interestRepository.save(new Interest(name, 0));
    }

    private List<String> saveKeywords(Interest interest, Set<String> keywords) {
        // 이미 존재하는 키워드인지 조회
        List<Keywords> existedKeywords = keywordRepository.findByKeywordIn(keywords);

        // 존재하는 키워드의 문자열만 추출
        Set<String> existedKeywordStrings = existedKeywords.stream()
                .map(Keywords::getKeyword)
                .collect(Collectors.toSet());

        // 새로 들어온 keywords 중에서 DB에 없는 것만 필터링
        List<Keywords> newKeywords = keywords.stream()
                .filter(keyword -> !existedKeywordStrings.contains(keyword))
                .map(Keywords::new) // 생성자: new Keywords("주식")
                .toList();

        // 새로운 키워드를 DB에 저장
        List<Keywords> savedNewKeywords = keywordRepository.saveAll(newKeywords);

        // 기존 + 새로 저장한 키워드 합쳐서 반환
        Set<Keywords> keywordsSet = new HashSet<>();
        keywordsSet.addAll(existedKeywords);
        keywordsSet.addAll(savedNewKeywords);

        // 관심사 - 키워드 저장
        keywordsSet.forEach(keyword -> {
            interestKeywordRepository.save(new InterestKeyword(interest, keyword));
        });

        // 문자열만 추출
        return keywordsSet.stream()
                .map(Keywords::getKeyword).toList();
    }

    private List<InterestDto> updateSubscribedByMe(UUID userId, List<InterestDto> interestList) {
        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 사용자 아이디 입니다. " + userId));

        // 사용자가 구독한 관심사 조회
        List<UserInterest> userInterests = userInterestRepository.findByUser(user);

        // 구독한 interestId를 Set으로 모음
        Set<Long> subscribedInterestIds = userInterests.stream()
                .map(ui -> ui.getInterest().getId())
                .collect(Collectors.toSet());

        // subscribedByMe 반영해서 새로운 리스트 반환
        return interestList.stream()
                .map(dto -> InterestDto.builder()
                        .id(dto.id())
                        .name(dto.name())
                        .keywords(dto.keywords())
                        .subscriberCount(dto.subscriberCount())
                        .subscribedByMe(subscribedInterestIds.contains(dto.id()))
                        .build())
                .toList();
    }

    private List<String> getKeywordsName(Long interestId) {
        List<Keywords> keywordsList = interestKeywordRepository.findByInterestId(interestId).stream()
                .map(InterestKeyword::getKeywords)
                .toList();

        return keywordsList.stream().map(Keywords::getKeyword).toList();
    }

    private UserInterest interestSubscribe(User user, Interest interest) {
        UserInterest userInterest = userInterestRepository.findByUserAndInterest(user,interest)
                .orElse(new UserInterest(user,interest,true));

        userInterest.interestSubscribe(userInterest.getSubscribedByMe()); // 구독 or 해제

        return userInterestRepository.save(userInterest);
    }
}
