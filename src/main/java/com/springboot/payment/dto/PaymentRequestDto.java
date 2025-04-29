package com.springboot.payment.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDto {
    private int amount;         // 결제 금액
    private int riceAmount;     // 밥풀 양
}

