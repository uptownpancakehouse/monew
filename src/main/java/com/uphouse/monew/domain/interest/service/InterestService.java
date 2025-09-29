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
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;
    private final KeywordRepository keywordRepository;
    private final InterestKeywordRepository interestKeywordRepository;
    private final UserRepository userRepository;
    private final UserInterestRepository userInterestRepository;



    public InterestCreateResponse create(InterestCreateRequest request) {

        Interest interest = saveInterest(request.name()).orElse(null);      // 관심사 저장
        List<Keywords> keywordList = saveKeywords(request.keywords());              // 키워드 저장

        List<String> keywords = new ArrayList<>();

        // 관심사 - 키워드 저장
        keywordList.forEach(keyword -> {
            interestKeywordRepository.save(new InterestKeyword(interest, keyword));
            keywords.add(keyword.getKeyword());
        });

        return InterestCreateResponse.builder()
                .id(interest != null ? interest.getId() : null)
                .name(interest != null ? interest.getName() : request.name())
                .keywords(keywords)
                .subscriberCount(0L)
                .subscribedByMe(false)
                .build();
    }

    private Optional<Interest> saveInterest(String name) {

        // 1. 완전 동일한 관심사 존재 여부
        Interest interest = interestRepository.findByName(name).orElse(null);

        if (interest != null) {
            return Optional.of(interest);
        }

        // 2. 유사 관심사 존재 여부 (80% 이상)
        if (interestRepository.existsBySimilarName(name)) {
            return Optional.empty(); // 등록 안 함
        }

        // 3. 새로운 관심사 등록
        Interest newInterest = interestRepository.save(new Interest(name, 0));
        return Optional.of(newInterest);
    }

    private List<Keywords> saveKeywords(List<String> keywords) {
        Set<String> keywordSet = new HashSet<>();
        List<Keywords> keywordList = new ArrayList<>();

        keywordRepository.findAll().forEach(keyword -> keywordSet.add(keyword.getKeyword()));

        keywords.forEach(keyword -> {
            if(!keywordSet.contains(keyword)) {
                keywordList.add(keywordRepository.save(new Keywords(keyword)));
            }
        });

        return keywordList;
    }
}
