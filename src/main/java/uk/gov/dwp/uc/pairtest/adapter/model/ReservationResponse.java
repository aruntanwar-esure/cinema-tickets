package uk.gov.dwp.uc.pairtest.adapter.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
/**
 * You may change reservation response as per adapter
 */
public class ReservationResponse {
    private String status;
    private String message;
}
