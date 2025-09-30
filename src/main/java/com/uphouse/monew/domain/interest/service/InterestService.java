package com.uphouse.monew.domain.interest.service;

import com.uphouse.monew.domain.interest.domain.Interest;
import com.uphouse.monew.domain.interest.domain.InterestKeyword;
import com.uphouse.monew.domain.interest.domain.Keywords;
import com.uphouse.monew.domain.interest.dto.InterestCreateRequest;
import com.uphouse.monew.domain.interest.dto.InterestCreateResponse;
import com.uphouse.monew.domain.interest.repository.InterestKeywordRepository;
import com.uphouse.monew.domain.interest.repository.InterestRepository;
import com.uphouse.monew.domain.interest.repository.KeywordRepository;
import com.uphouse.monew.domain.interest.repository.UserInterestRepository;
import com.uphouse.monew.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;
    private final KeywordRepository keywordRepository;
    private final InterestKeywordRepository interestKeywordRepository;
    private final UserRepository userRepository;
    private final UserInterestRepository userInterestRepository;

    public InterestCreateResponse create(InterestCreateRequest request) {

        Interest interest = saveInterest(request.name());    // 관심사 저장
        List<String> keywordsList = saveKeywords(interest, request.keywords());          // 키워드 저장

        return InterestCreateResponse.builder()
                .id(interest != null ? interest.getId() : null)
                .name(interest != null ? interest.getName() : request.name())
                .keywords(keywordsList)
                .subscriberCount(0L)
                .subscribedByMe(false)
                .build();
    }

    public InterestCreateResponse update(Long interestId, Set<String> keywords) {

        Interest interest = interestRepository.findById(interestId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 관심사: " + interestId));

        List<String> keywordsList = saveKeywords(interest, keywords);

        return InterestCreateResponse.builder()
                .id(interest.getId())
                .name(interest.getName())
                .keywords(keywordsList)
                .subscriberCount(0L)
                .subscribedByMe(false)
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
        List<Keywords> existedKeywords = keywordRepository.findByKeywords(keywords);

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

        // 관심사 - 키워드 저장
        savedNewKeywords.forEach(keyword -> {
            interestKeywordRepository.save(new InterestKeyword(interest, keyword));
        });

        // 기존 + 새로 저장한 키워드 합쳐서 반환
        Set<Keywords> keywordsSet = new HashSet<>();
        keywordsSet.addAll(existedKeywords);
        keywordsSet.addAll(savedNewKeywords);

        // 문자열만 추출
        return keywordsSet.stream()
                .map(Keywords::getKeyword).toList();
    }
}
