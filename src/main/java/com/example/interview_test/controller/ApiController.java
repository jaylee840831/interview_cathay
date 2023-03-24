package com.example.interview_test.controller;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.interview_test.DAO.Coin;
import com.example.interview_test.DTO.CoinDto;
import com.example.interview_test.service.CoinDeskService;
import com.example.interview_test.service.CoinService;

@RestController()
@RequestMapping("/api/v1")
public class ApiController {

	@Autowired
	CoinService coinService;

	@Autowired
	CoinDeskService coinDeskService;

	// 呼叫coindesk api
	@GetMapping("/get/coindesk")
	public ResponseEntity<Object> coindeskAPI() {
		Object coindesk = coinDeskService.getCoinDeskAPI();
		return new ResponseEntity<Object>(coindesk, HttpStatus.OK);
	}

	// 取得轉換後的資料
	@GetMapping("/get/transform")
	public ResponseEntity<List<Coin>> transformCoin() {

		Object coindesk = coinDeskService.getCoinDeskAPI();
		List<Coin> coins = coinService.saveCoins(coinDeskService.analyzeAPI(coindesk));

		return new ResponseEntity<List<Coin>>(coins, HttpStatus.OK);
	}

	// 依幣別查詢對應的資料
	@GetMapping("/get/coin/{name}")
	public ResponseEntity<CoinDto> getCoin(@PathVariable("name") String name) {
		return new ResponseEntity<CoinDto>(coinService.getCoin(name), HttpStatus.OK);
	}

	// 新增資料
	@PostMapping("/add/coin")
	public ResponseEntity<CoinDto> addCoin(@RequestBody Coin c) {
		return new ResponseEntity<CoinDto>(coinService.saveCoin(c), HttpStatus.OK);
	}

	// 修改資料
	@PostMapping("/update/coin")
	public ResponseEntity<CoinDto> updateCoin(@RequestBody Coin c) {
		return new ResponseEntity<CoinDto>(coinService.updateCoin(c), HttpStatus.OK);
	}

	// 刪除資料
	@DeleteMapping("/delete/coin/{name}")
	public ResponseEntity<Void> deleteCoin(@PathVariable("name") String name) {

		if (!name.equals("")) {
			coinService.deleteCoin(name);
		}
		return ResponseEntity.noContent().build();
	}
}
