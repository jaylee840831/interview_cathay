package com.example.interview_test.service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.interview_test.DAO.Coin;
import com.example.interview_test.DTO.CoinDto;
import com.example.interview_test.repository.CoinRepository;

@Service
public class CoinService {

	@Autowired
	CoinRepository coinRepository;

	public List<Coin> getAllCoin() {
		return coinRepository.findAll();
	}

	public CoinDto getCoin(String name) {

		CoinDto coinDto = new CoinDto();
		Optional<Coin> coin = coinRepository.findById(name);

		if (coin.isPresent()) {
			coinDto.setChineseName(coin.get().getChineseName());
			coinDto.setCoinName(coin.get().getCoinName());
			coinDto.setRate(coin.get().getRate());
			coinDto.setUpdateTime(coin.get().getUpdateTime());
		}

		return coinDto;
	}

	public CoinDto updateCoin(Coin c) {

		OffsetDateTime odt = OffsetDateTime.now();
		Instant instant = odt.toInstant();
		Date date = Date.from(instant);
		c.setUpdateTime(date);

		CoinDto coinDto = new CoinDto();

		if (coinRepository.existsById(c.getCoinName())) {

			Coin coin = coinRepository.save(c);

			if (coin.getCoinName() != null && !coin.getCoinName().equals("")) {
				coinDto.setChineseName(coin.getChineseName());
				coinDto.setCoinName(coin.getCoinName());
				coinDto.setRate(coin.getRate());
				coinDto.setUpdateTime(coin.getUpdateTime());
			}
		}

		return coinDto;
	}

	public CoinDto saveCoin(Coin c) {

		OffsetDateTime odt = OffsetDateTime.now();
		Instant instant = odt.toInstant();
		Date date = Date.from(instant);
		c.setUpdateTime(date);

		CoinDto coinDto = new CoinDto();
		Coin coin = coinRepository.save(c);

		if (coin.getCoinName() != null && !coin.getCoinName().equals("")) {
			coinDto.setChineseName(coin.getChineseName());
			coinDto.setCoinName(coin.getCoinName());
			coinDto.setRate(coin.getRate());
			coinDto.setUpdateTime(coin.getUpdateTime());
		}

		return coinDto;
	}

	public List<Coin> saveCoins(List<Coin> coins) {
		return coinRepository.saveAll(coins);
	}

	public void deleteCoin(String name) {

		try {
			coinRepository.deleteById(name);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("deleteCoin api error: " + e);
		}
	}
}
