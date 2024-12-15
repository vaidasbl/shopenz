package lt.shopenz.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import lt.shopenz.service.CartService;

@Slf4j
@Component
public class EventListener
{
    private final CartService cartService;

    public EventListener(final CartService pService)
    {
        cartService = pService;
    }

    @Transactional
    @KafkaListener(topics = "${kafka.topic.user-created}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenUserCreated(Long userId)
    {
        log.info("Cart service received a 'user-created' message. UserId = {}", userId);

        cartService.createCartForUserId(userId);
    }

    @Transactional
    @KafkaListener(topics = "${kafka.topic.user-deleted}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenUserDeleted(Long userId)
    {
        log.info("Cart service received a 'user-deleted' message. UserId = {}", userId);

        cartService.deleteCartByUserId(userId);
    }
}
