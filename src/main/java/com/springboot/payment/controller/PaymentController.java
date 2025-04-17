package com.springboot.payment.controller;

import com.springboot.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Tag(name = "결제 컨트롤러", description = "포인트 충전/환불/내역 관련 API")
@RestController
@RequestMapping("/payments")
@Validated
public class PaymentController {

    @Operation(summary = "포인트 충전", description = "원하는 금액만큼 포인트를 충전합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "포인트 충전 성공"),
            @ApiResponse(responseCode = "400", description = "충전 요청 오류")
    })
    @PostMapping
    public ResponseEntity chargePoints(
            @Parameter(hidden = true) @AuthenticationPrincipal Member member) {

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "포인트 환불 요청", description = "사용자가 포인트 환불을 요청합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "환불 요청 성공")
    })
    @PostMapping("/{payment-id}/refund")
    public ResponseEntity refundPoints(@PathVariable("payment-id") @Positive long paymentId,
                                       @Parameter(hidden = true) @AuthenticationPrincipal Member member) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "결제 내역 전체 조회", description = "포인트 충전/사용 내역을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "결제 내역 조회 성공")
    })
    @GetMapping("/history")
    public ResponseEntity getPaymentHistory(
            @Parameter(hidden = true) @AuthenticationPrincipal Member member) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "포인트 트랜잭션 로그 조회", description = "포인트 입출금 로그를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "포인트 로그 조회 성공")
    })
    @GetMapping("/points/logs")
    public ResponseEntity getPointLogs(
            @Parameter(hidden = true) @AuthenticationPrincipal Member member) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
