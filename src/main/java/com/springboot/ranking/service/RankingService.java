package com.springboot.ranking.service;

import com.springboot.ranking.dto.MemberRankingResponseDto;
import com.springboot.ranking.dto.RankingResponseDto;
import com.springboot.ranking.repository.RankingRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RankingService {

    private final RankingRepository rankingRepository;

    public RankingService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    public List<RankingResponseDto> getTopRecipeBoardWriters() {
        List<Object[]> results = rankingRepository.findTopMembersByRecipeBoardCount();

        return results.stream()
                .map(obj -> new RankingResponseDto(
                        ((Number) obj[0]).longValue(),    // memberId
                        (String) obj[1],                  // nickName
                        (String) obj[2],                  // profileImagePath
                        ((Number) obj[3]).longValue(),     // count
                        (obj[4] != null) ? ((Timestamp) obj[4]).toLocalDateTime() : null // firstRecipeCreatedAt
                ))
                .collect(Collectors.toList());
    }

    public List<RankingResponseDto> getTopLikeReceivers() {
        List<Object[]> results = rankingRepository.findTopMembersByLikeCount();

        return results.stream()
                .map(obj -> new RankingResponseDto(
                        ((Number) obj[0]).longValue(),    // memberId
                        (String) obj[1],                  // nickName
                        (String) obj[2],                  // profileImagePath
                        ((Number) obj[3]).longValue(),    // likeCount
                        (obj[4] != null) ? ((Timestamp) obj[4]).toLocalDateTime() : null // firstLikeCreatedAt
                ))
                .collect(Collectors.toList());
    }

    public List<RankingResponseDto> getTopBookmarkReceivers() {
        List<Object[]> results = rankingRepository.findTopMembersByBookmarkCount();

        return results.stream()
                .map(obj -> new RankingResponseDto(
                        ((Number) obj[0]).longValue(),    // memberId
                        (String) obj[1],                  // nickName
                        (String) obj[2],                  // profileImagePath
                        ((Number) obj[3]).longValue(),    // bookmarkCount
                        (obj[4] != null) ? ((Timestamp) obj[4]).toLocalDateTime() : null // firstBookmarkCreatedAt
                ))
                .collect(Collectors.toList());
    }

    public List<RankingResponseDto> getTopTitleCollectors() {
        List<Object[]> results = rankingRepository.findTopMembersByTitleCount();

        return results.stream()
                .map(obj -> new RankingResponseDto(
                        ((Number) obj[0]).longValue(),    // memberId
                        (String) obj[1],                  // nickName
                        (String) obj[2],                  // profileImagePath
                        ((Number) obj[3]).longValue(),    // titleCount
                        (obj[4] != null) ? ((Timestamp) obj[4]).toLocalDateTime() : null // lastTitleAcquiredAt
                ))
                .collect(Collectors.toList());
    }

    public MemberRankingResponseDto getMemberAllRanks(long memberId) {
        Integer recipeBoardRank = rankingRepository.findRecipeBoardRankByMemberId(memberId);
        Integer likeRank = rankingRepository.findLikeRankByMemberId(memberId);
        Integer bookmarkRank = rankingRepository.findBookmarkRankByMemberId(memberId);
        Integer titleRank = rankingRepository.findTitleRankByMemberId(memberId);

        return new MemberRankingResponseDto(recipeBoardRank, likeRank, bookmarkRank, titleRank);
    }
}