package com.springboot.payment.service;

import com.springboot.challenge.entity.ChallengeCategory;
import com.springboot.challenge.repository.ChallengeCategoryRepository;
import com.springboot.challenge.repository.MemberChallengeRepository;
import com.springboot.config.TossPaymentConfig;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.entity.Member;
import com.springboot.member.entity.MemberChallenge;
import com.springboot.member.service.MemberService;
import com.springboot.payment.dto.*;
import com.springboot.payment.entity.Payment;
import com.springboot.payment.repository.PaymentRepository;
import com.springboot.recipeboard.entity.RecipeBoard;
import com.springboot.title.service.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final MemberService memberService;
    private final TossPaymentConfig tossPaymentConfig;
    private final TitleService titleService;
    private final MemberChallengeRepository memberChallengeRepository;
    private final ChallengeCategoryRepository challengeCategoryRepository;

    @Transactional
    public Payment requestTossPayment(PaymentRequestDto dto, Long memberId) {
        Member member = memberService.findMember(memberId);

        // 최소 결제 금액 검증
        if (dto.getAmount() < 1000) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PAYMENT_AMOUNT);
        }

        Payment payment = new Payment();
        payment.setAmount(dto.getAmount());
        payment.setRiceAmount(dto.getRiceAmount());
        payment.setOrderId(UUID.randomUUID().toString());
        payment.setPaymentKey(""); // 초기엔 비워두고, 성공 시 업데이트
        payment.setPaymentStatus(Payment.PaymentStatus.PENDING);
        payment.setMember(member);

        return paymentRepository.save(payment);
    }

    @Transactional
    public void confirmPayment(TossSuccessDto successDto) {
        // 1. Toss Confirm API 호출
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(tossPaymentConfig.getTestSecretApiKey(), ""); // Basic Auth
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("paymentKey", successDto.getPaymentKey());
        requestBody.put("orderId", successDto.getOrderId());
        requestBody.put("amount", successDto.getAmount());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                TossPaymentConfig.PAYMENT_CONFIRM_URL,
                request,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            // 2. 결제 성공 -> DB 업데이트
            Payment payment = paymentRepository.findByOrderId(successDto.getOrderId())
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PAYMENT_NOT_FOUND));
            payment.setPaymentStatus(Payment.PaymentStatus.COMPLETED);
            payment.setPaymentKey(successDto.getPaymentKey());
            paymentRepository.save(payment);

            // 🔥 밥풀 포인트 지급
            Member member = payment.getMember();
            int currentRicePoint = member.getRicePoint();  // 기존 밥풀
            member.setRicePoint(currentRicePoint + payment.getRiceAmount());  // 밥풀 추가
            setChallengeIncrement(member.getMemberId(), "밥풀", payment.getRiceAmount());
        } else {
            throw new BusinessLogicException(ExceptionCode.PAYMENT_FAILED);
        }
    }

    @Transactional
    public void failPayment(TossFailDto tossFailDto) {
        // 1. 결제 정보 가져오기
        Payment payment = paymentRepository.findByOrderId(tossFailDto.getOrderId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PAYMENT_NOT_FOUND));

        // 2. 상태 업데이트
        payment.setPaymentStatus(Payment.PaymentStatus.FAILED);
        payment.setPaymentKey(tossFailDto.getPaymentKey());
        paymentRepository.save(payment);
    }

    @Transactional
    public void cancelPayment(TossCancelDto cancelDto) {
        // 1. PaymentKey로 결제 내역 조회
        Payment payment = paymentRepository.findByPaymentKey(cancelDto.getPaymentKey())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PAYMENT_NOT_FOUND));

        // 2. Toss 취소 API 호출
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(tossPaymentConfig.getTestSecretApiKey(), "");
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("cancelReason", cancelDto.getCancelReason());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://api.tosspayments.com/v1/payments/" + cancelDto.getPaymentKey() + "/cancel",
                request,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            // 3. DB 상태 업데이트
            payment.setPaymentStatus(Payment.PaymentStatus.CANCELLED);
            payment.setRefundReason(cancelDto.getCancelReason());
            paymentRepository.save(payment);
        } else {
            throw new BusinessLogicException(ExceptionCode.PAYMENT_FAILED);
        }
    }

    @Transactional
    public List<PaymentHistoryDto> historyPayment(long memberId) {
        List<Payment> payments = paymentRepository.findAllByMember_MemberId(memberId);

        // Dto로 변환
        List<PaymentHistoryDto> response = payments.stream()
                .map(payment -> PaymentHistoryDto.builder()
                        .orderId(payment.getOrderId())
                        .amount(payment.getAmount())
                        .riceAmount(payment.getRiceAmount())
                        .paymentStatus(payment.getPaymentStatus())
                        .requestedAt(payment.getRequestedAt())
                        .completedAt(payment.getCompletedAt())
                        .build())
                .collect(Collectors.toList());

        return response;
    }

    private void setChallengeIncrement(long memberId, String category, int cost) {
        ChallengeCategory challengeCategory = challengeCategoryRepository.findByCategory(category)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.CHALLENGE_CATEGORY_NOT_FOUND));
        MemberChallenge memberChallenge = memberChallengeRepository.findByMember_MemberIdAndChallengeCategory_ChallengeCategoryid(memberId, challengeCategory.getChallengeCategoryid())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_CHALLENGE_NOT_FOUND));
        titleService.incrementChallengeCostCount(memberChallenge.getMemberChallengeId(), memberId, cost);
    }
}
