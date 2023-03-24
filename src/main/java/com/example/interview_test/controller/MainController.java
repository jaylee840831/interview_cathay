package com.example.interview_test.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.interview_test.DAO.Coin;
import com.example.interview_test.service.CoinService;

@Controller
public class MainController {
	
	@Autowired
	CoinService coinService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/")	
	public String homepage(Model model) {
		String url="http://localhost:8080/api/v1/transform/coin";
		Object[]objects=restTemplate.getForObject(url, Object[].class);
		List<Object>coins=Arrays.asList(objects);
		model.addAttribute("coins",coins);
		return "index";
	}
}
