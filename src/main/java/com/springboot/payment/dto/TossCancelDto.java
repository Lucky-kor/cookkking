package com.springboot.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TossCancelDto {
    private String paymentKey;
    private String cancelReason; // 취소 사유
}
