package yongs.temp.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import yongs.temp.model.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
	public Order findByNo(final long no);
	public void deleteByNo(final long no);
	
	@Query("{'user.email':?0}")
	public List<Order> findByUser(String email, Sort sort);
}
