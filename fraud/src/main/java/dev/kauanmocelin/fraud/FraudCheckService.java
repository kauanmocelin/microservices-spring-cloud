package dev.kauanmocelin.fraud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class FraudCheckService {

    private final FraudCheckHistoryRepository fraudCheckHistoryRepository;

    public boolean isFradulentCustomer(Integer customerId) {
        fraudCheckHistoryRepository.save(
            FraudCheckHistory.builder()
                .customerId(customerId)
                .isFraudster(false)
                .cratedAt(LocalDateTime.now())
                .build()
        );
        return false;
    }

}
