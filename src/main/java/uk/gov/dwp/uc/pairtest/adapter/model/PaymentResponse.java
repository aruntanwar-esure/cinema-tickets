package uk.gov.dwp.uc.pairtest.adapter.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
/**
 * You may change payment response as per adapter
 */
public class PaymentResponse {
    private String status;
    private String message;
}
