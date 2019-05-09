package com.liujian.order;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="order/")
public class OrderController {

	
	@GetMapping("test")
	public String test() {
		return "Order Controller Test";
	}
	
}
