package uk.gov.dwp.uc.pairtest.adapter.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
/**
 * You may have new payment DTO for a new adapter
 */
public class PaymentDTO {
    long accountId;
    int totalAmountToPay;
}
