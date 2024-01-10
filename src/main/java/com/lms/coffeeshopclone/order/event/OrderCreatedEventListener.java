package com.lms.coffeeshopclone.order.event;


import com.lms.coffeeshopclone.common.KafkaMessagePublisher;
import com.lms.coffeeshopclone.common.KafkaTopic;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderCreatedEventListener {

    KafkaMessagePublisher kafkaMessagePublisher;

    @TransactionalEventListener(phase= TransactionPhase.AFTER_COMMIT)
    public void handleEvent(OrderCreatedEvent orderCreatedEvent){
        kafkaMessagePublisher.publish(
                KafkaTopic.ORDER_CREATED,
                orderCreatedEvent.getOrderDto().getOrderId().toString(),
                orderCreatedEvent.getOrderDto()
        );
    }

}
