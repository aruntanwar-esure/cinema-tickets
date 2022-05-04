package uk.gov.dwp.uc.pairtest.adapter.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
/**
 * You may have new reservation DTO for a new adapter
 */
public class ReservationDTO {
    long accountId;
    int totalSeatsToAllocate;
}
