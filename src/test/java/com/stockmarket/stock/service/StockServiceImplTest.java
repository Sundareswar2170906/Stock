package com.stockmarket.stock.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.stockmarket.stock.models.Stock;
import com.stockmarket.stock.repository.StockRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {
	
	@InjectMocks
	private StockServiceImpl service;
	
	@Mock
	private StockRepository repo;
	
		
	
	@Test
	void addStock() {
		
		Stock stock = new Stock(1, 1,18.2,new Date("10/12/2022"));	
		
		repo.save(stock);
		verify(repo, times(1)).save(stock);

	}
	
	@Test
	void getAllLatestPrice() {
		
		List<Stock> latestPrice = new ArrayList<>(Arrays.asList(
				new Stock(1,1,18.2,new Date("10/12/2022")),
				new Stock(2,2,110.2, new Date("10/12/2022"))
				));		
		Mockito.lenient().when(repo.findAllLatestPrice()).thenReturn(latestPrice);		
		assertEquals(2, latestPrice.size());
	}
	
	@Test
	void getLatestPrice() {
		
		Stock latestPrice = new Stock(1,1,18.2,new Date("10/12/2022"));
		
		Mockito.lenient().when(repo.findLatestPrice(1)).thenReturn(latestPrice);		
		assertEquals(18.2, latestPrice.getPrice());
	}
	
	@Test
	void deleteAllStockPrice() {
		
		Mockito.lenient().when(repo.deleteByCode(1)).thenReturn((long) 1);		
		assertEquals(1, repo.deleteByCode(1));
	}
	
	@Test
	void getAllStockPrice() {
		
		List<Stock> latestPrice = new ArrayList<>(Arrays.asList(
				new Stock(1,1,18.2,new Date("10/12/2022")),
				new Stock(2,1,110.2, new Date("10/12/2022"))
				));		
		
		Mockito.lenient().when(repo.findAllByCode(1)).thenReturn(latestPrice);		
		assertEquals(2, latestPrice.size());
	}
	
	@Test
	void getStockDetailsByDate() {
		
		List<Stock> latestPrice = new ArrayList<>(Arrays.asList(
				new Stock(1,1,18.2,new Date("10/12/2022")),
				new Stock(2,1,110.2, new Date("10/12/2022"))
				));		
		
		Mockito.lenient().when(repo.findStockPriceByDate(1,new Date("10/12/2022"),new Date("10/12/2022"))).thenReturn(latestPrice);		
		assertEquals(2, latestPrice.size());
	}

}
