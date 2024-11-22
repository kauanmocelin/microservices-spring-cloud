package dev.kauanmocelin.customer;

import dev.kauanmocelin.clients.fraud.FraudCheckResponse;
import dev.kauanmocelin.clients.fraud.FraudClient;
import dev.kauanmocelin.clients.notification.NotificationClient;
import dev.kauanmocelin.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
            .firstName(request.firstName())
            .lastName(request.lastName())
            .email(request.email())
            .build();
        customerRepository.saveAndFlush(customer);
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
        if(fraudCheckResponse.isFraudster()) {
            throw new IllegalArgumentException("fraudster");
        }
        notificationClient.sendNotification(
            new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, you was registered!", customer.getFirstName())
            )
        );
    }
}
