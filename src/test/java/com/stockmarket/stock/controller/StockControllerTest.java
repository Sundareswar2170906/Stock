package com.stockmarket.stock.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.stockmarket.stock.dto.DeleteStockDTO;
import com.stockmarket.stock.dto.StockDTO;
import com.stockmarket.stock.models.Stock;
import com.stockmarket.stock.service.StockServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StockControllerTest {

	@InjectMocks
	private StockController controller;

	@Mock
	private StockServiceImpl service;

	@Test
	void addStock() {

		Stock stock = new Stock(1, 1, 18.2, new Date("10/12/2022"));

		service.addStock(stock);
		verify(service, times(1)).addStock(stock);

	}

	@Test
	void getAllLatestPrice() {

		List<StockDTO> latestPrice = new ArrayList<>(Arrays.asList(new StockDTO(1, 18.2),
				new StockDTO(2, 110.2)));
		Mockito.lenient().when(service.getAllLatestPrice()).thenReturn(latestPrice);
		assertEquals(2, latestPrice.size());
	}

	@Test
	void getLatestPrice() {

		StockDTO latestPrice = new StockDTO(1, 18.2);

		Mockito.lenient().when(service.getLatestPrice(1)).thenReturn(latestPrice);
		assertEquals(18.2, latestPrice.getPrice());
	}

	@Test
	void deleteAllStockPrice() {

		DeleteStockDTO deleteDTO = new DeleteStockDTO();
		deleteDTO.setCount(1);
		deleteDTO.setMessage("Successfully Deleted!");
		Mockito.lenient().when(service.deleteAllStockPrice(1)).thenReturn(deleteDTO);
		assertEquals(1, deleteDTO.getCount());
	}

	
	@Test
	void getStockDetailsByDate() {

		List<Stock> latestPrice = new ArrayList<>(Arrays.asList(new Stock(1, 1, 18.2, new Date("10/12/2022")),
				new Stock(2, 1, 110.2, new Date("10/12/2022"))));

		Mockito.lenient().when(service.getStockDetailsByDate(1, new Date("10/12/2022"), new Date("10/12/2022")))
				.thenReturn(latestPrice);
		assertEquals(2, latestPrice.size());
	}

}
