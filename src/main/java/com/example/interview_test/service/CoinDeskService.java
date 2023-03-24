package com.example.interview_test.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.interview_test.DAO.Coin;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CoinDeskService {

	private final RestTemplate restTemplate;

	private Map<String, String> compareMap = new HashMap<String, String>() {
		{
			put("USD", "美元");
			put("GBP", "英鎊");
			put("EUR", "歐元");
		}
	};

	public CoinDeskService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		messageConverters.add(converter);
		this.restTemplate.setMessageConverters(messageConverters);
	}

	public Object getCoinDeskAPI() {
		return this.restTemplate.getForObject("https://api.coindesk.com/v1/bpi/currentprice.json", Object.class);
	}

	public List<Coin> analyzeAPI(Object coindesk) {

		ObjectMapper Obj = new ObjectMapper();
		List<Coin> coins = new ArrayList<>();
		try {
			String jsonStr = Obj.writeValueAsString(coindesk);
			JSONObject json = new JSONObject(jsonStr);

			// update time
			JSONObject time = new JSONObject(json.get("time").toString());
			String time2 = time.get("updatedISO").toString();
			Date updateTime = convertDate(time2);

			// 幣別 中文幣別 匯率
			JSONObject bpis = new JSONObject(json.get("bpi").toString());

			for (Map.Entry<String, String> entry : compareMap.entrySet()) {
				coins.add(analyzeBPI(new JSONObject(bpis.get(entry.getKey()).toString()), entry, updateTime));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return coins;
	}

	public Coin analyzeBPI(JSONObject obj, Map.Entry<String, String> entry, Date updateTime) {
		Coin coin = new Coin();
		coin.setCoinName(entry.getKey());
		coin.setChineseName(entry.getValue()); 
		coin.setRate(Float.parseFloat(obj.get("rate_float").toString()));
		coin.setUpdateTime(updateTime);
//		System.out.println(coin);
		return coin;
	}

	public Date convertDate(String time) {
		OffsetDateTime odt = OffsetDateTime.parse(time);//帶有偏移量的時間儲存類型， 簡單理解為 OffsetDateTime = LocalDateTime + ZoneOffset。
		Instant instant = odt.toInstant();//秒數 (也可以是毫秒) 儲存的 UTC 瞬間
		Date date = Date.from(instant);
		return date;
	}
}
