package com.example.interview_test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.example.interview_test.DAO.Coin;
import com.example.interview_test.DTO.CoinDto;
import com.example.interview_test.repository.CoinRepository;
import com.example.interview_test.service.CoinDeskService;
import com.example.interview_test.service.CoinService;

@SpringBootTest
class InterviewTestApplicationTests {

	@Autowired
	CoinRepository coinRepository;
	
	@Autowired
	CoinDeskService coinDeskService;

	// 呼叫coindesk api
	@Test
	void testCoinDeskAPI() {
		Object coindesk = coinRepository.findAll();
		assertNotNull(coindesk);

		System.out.println("call coindesk api");
		System.out.println(coindesk);
		System.out.println("----------------------------------------------------");
	}

	// 取得轉換後的資料
	@Test
	void testTransformCoin() {
		Object coindesk = coinDeskService.getCoinDeskAPI();
		List<Coin> coins = coinRepository.saveAll(coinDeskService.analyzeAPI(coindesk));
		assertThat(coins).size().isGreaterThan(0);

		System.out.println("資料轉換");
		for (Coin c : coins) {
			System.out.println(c.toString());
		}
		System.out.println("----------------------------------------------------");
	}

	// 依幣別查詢對應的資料
	@Test
	void testGetCoinByName() {
		
		Date date = new Date(System.currentTimeMillis());
		Coin coin = new Coin();
		coin.setCoinName("NTD");
		coin.setChineseName("新台幣");
		coin.setRate(30000.123f);
		coin.setUpdateTime(date);
		Coin c = coinRepository.save(coin);
		
		Optional<Coin> coinDto = coinRepository.findById("NTD");
		assertNotNull(coinDto.get());

		System.out.println("依幣別(USD)查詢對應的資料");
		System.out.println(coinDto.get());
		System.out.println("----------------------------------------------------");
	}

	// 新增幣別資料
	@Test
	void testAddCoin() {
		Date date = new Date(System.currentTimeMillis());
		Coin coin = new Coin();
		coin.setCoinName("NTD");
		coin.setChineseName("新台幣");
		coin.setRate(30000.123f);
		coin.setUpdateTime(date);
		Coin c = coinRepository.save(coin);
		assertEquals("NTD", c.getCoinName());
	}

	// 修改幣別資料
	@Test
	void testUpdateCoin() {
		
		Date date = new Date(System.currentTimeMillis());
		Coin coin = new Coin();
		coin.setCoinName("NTD");
		coin.setChineseName("新台幣");
		coin.setRate(30000.123f);
		coin.setUpdateTime(date);
		Coin c = coinRepository.save(coin);
		
		System.out.println("幣別資料更新前");
		System.out.println(c);
		c.setChineseName("台灣人的錢幣");
		Coin c2 = coinRepository.save(c);

		System.out.println("幣別資料更新後");
		System.out.println(c2);

		System.out.println("----------------------------------------------------");

		assertEquals("台灣人的錢幣", c2.getChineseName());
	}

	// 刪除幣別資料
	@Test
	void testDeleteCoin() {
		coinRepository.deleteById("NTD");
		assertThat(coinRepository.existsById("NTD")).isFalse();
	}

}
