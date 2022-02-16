package yongs.temp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import yongs.temp.httpclient.ProductClient;
import yongs.temp.httpclient.UserClient;
import yongs.temp.model.Order;
import yongs.temp.service.OrderService;
import yongs.temp.vo.Product;
import yongs.temp.vo.User;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {	
	@Autowired
	UserClient userClient;
	@Autowired
	ProductClient productClient;
	@Autowired
	OrderService orderService;
	
	@GetMapping("/useremail/{useremail}")
	public User findByUserEmail(@PathVariable("useremail") String useremail) throws Exception {
		log.debug("OrderController.findByUserEmail()");
		return userClient.findByEmail(useremail);
	}
	@GetMapping("/productname/{productname}")
	public List<Product> findByRegexpName(@PathVariable("productname") String productname) throws Exception {
		log.debug("OrderController.findByRegexpName()");
		return productClient.findByRegexpName(productname);
	}

	@PostMapping("/create") 
    public void create(@RequestBody Order order) throws Exception {
		log.debug("OrderController.create()", order);
		orderService.init(order);
    }

	@GetMapping("/all/{useremail}")
	public List<Order> findByUser(@PathVariable("useremail") String useremail) throws Exception {
		log.debug("OrderController.findAllByUser()");
		return orderService.findByUser(useremail);
	}
}
