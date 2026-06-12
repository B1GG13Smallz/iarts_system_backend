package za.gov.dpw.iarts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.gov.dpw.iarts.repository.AvailabilityRequestRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AvailabilityRequestExpiryService {
    private static final String PENDING = "PENDING";
    private static final long EXPIRY_MINUTES = 60L;

    private final AvailabilityRequestRepository availabilityRequestRepository;

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void clearExpiredUnreferencedRequests() {
        availabilityRequestRepository.deletePendingUnreferencedBefore(PENDING, LocalDateTime.now().minusMinutes(EXPIRY_MINUTES));
    }
}
