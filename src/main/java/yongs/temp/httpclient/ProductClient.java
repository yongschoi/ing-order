package yongs.temp.httpclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import yongs.temp.vo.Product;

@FeignClient(name = "product", url = "${feign.product.url}")
public interface ProductClient {
	@GetMapping("/product/name/{name}")
	List<Product> findByRegexpName(@PathVariable("name") String name);
}
