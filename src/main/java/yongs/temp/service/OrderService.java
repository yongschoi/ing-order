package yongs.temp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import yongs.temp.dao.OrderRepository;
import yongs.temp.model.Order;
import yongs.temp.vo.Delivery;

@Slf4j
@Service
public class OrderService {
	private static final String ORDER_PAY_CREATE = "order-pay-create";
	private static final String DELIVERY_ORDER_CREATE = "delivery-order-create";
	
	private static final String ORDER_CREATE_ROLLBACK = "order-create-rollback";

	private static final String DELIVERY_UPDATE_NOTICE = "delivery-update-notice";

	@Autowired
    OrderRepository repo;
	@Autowired
    KafkaTemplate<String, String> kafkaTemplate;
	
	public void init(Order order) { 
		ObjectMapper mapper = new ObjectMapper();
		try {
			// TX보장을 위해 Order(주문정보생성)->Pay(DB저장)->Delivery(DB저장)->Order(DB저장)
			long no = System.currentTimeMillis();
			order.setNo(no);
			String _tempStr = mapper.writeValueAsString(order);
			// to pay
			kafkaTemplate.send(ORDER_PAY_CREATE, _tempStr);
			log.debug("[ORDER Initialize] Order No[" + order.getNo() + "]");
		} catch (Exception e) {
			log.debug("[ORDER Fail] Order No[" + order.getNo() + "]");
		}
	}
	
	@KafkaListener(topics = DELIVERY_ORDER_CREATE)
	public void create(String orderStr, Acknowledgment ack) {	
		ObjectMapper mapper = new ObjectMapper();
		Order order = null;
		try {
			order = mapper.readValue(orderStr, Order.class);
			// 모든 프로세스가 완료되었으며, 최종 Order정보를 DB에 저장한다.
			repo.insert(order);
			// ack.acknowledge()는 rollback을 허용하지 않고 반드시 처리하겠다는 의미임.(acknowledge 수행될 때 까지 메시지는 kafka에 존재하게 됨)
			ack.acknowledge();
			log.debug("[ORDER Complete] Order No[" + order.getNo() + "]");
		} catch (Exception e) {
			kafkaTemplate.send(ORDER_CREATE_ROLLBACK, orderStr);
			log.debug("[ORDER Fail] Order No[" + order.getNo() + "]");
		}
	}
	
	@KafkaListener(topics = DELIVERY_UPDATE_NOTICE)
	public void updateAddress(String deliveryStr, Acknowledgment ack) {	
		ObjectMapper mapper = new ObjectMapper();
		Delivery delivery = null;
		try {
			delivery = mapper.readValue(deliveryStr, Delivery.class);
			Order savedOrder = repo.findByNo(delivery.getOrderNo());
			savedOrder.getDelivery().setAddress(delivery.getAddress());
			repo.save(savedOrder);
			// ack.acknowledge()는 rollback을 허용하지 않고 반드시 처리하겠다는 의미임.(acknowledge 수행될 때 까지 메시지는 kafka에 존재하게 됨)
			ack.acknowledge();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Order> findByUser(String email) {
		return repo.findByUser(email, Sort.by(Sort.Direction.DESC, "no"));
	} 
}
