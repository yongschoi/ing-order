package yongs.temp.model;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yongs.temp.vo.Delivery;
import yongs.temp.vo.Pay;
import yongs.temp.vo.Product;
import yongs.temp.vo.User;

@AllArgsConstructor 
@NoArgsConstructor
@Data
@Document(collection = "order")
@Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Order {
	@Id
	private String id;
	private long no; // currentTimeMillis();
	private int qty;
	private Product product;
	private User user;
	private Pay pay;
	private Delivery delivery;
}
