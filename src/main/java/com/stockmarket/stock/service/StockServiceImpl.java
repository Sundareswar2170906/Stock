package com.stockmarket.stock.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.stockmarket.stock.dto.DeleteStockDTO;
import com.stockmarket.stock.dto.StockDTO;
import com.stockmarket.stock.models.Stock;
import com.stockmarket.stock.repository.StockRepository;

@Service
public class StockServiceImpl implements IStockService {
	
	private String kafkaTopic= "stock-logs";

	@Autowired
	private KafkaTemplate<String, String> template;

	@Autowired
	private StockRepository stockRepo;

	@Override
	public Stock addStock(Stock stock) {
		//template.send(kafkaTopic, "Add Stock Price - Company Code : " + stock.getCode());
		return stockRepo.save(stock);
	}

	@Override
	@Cacheable(cacheNames = "AllStockPrices")
	public List<StockDTO> getAllLatestPrice() {
		//template.send(kafkaTopic,"Get Stock Price - All");
		System.out.println("No Cache");
		List<Stock> all = stockRepo.findAllLatestPrice();
		List<StockDTO> sdtolist = new ArrayList<>();
		for (Stock a : all) {
			StockDTO sdto = new StockDTO();
			sdto.setCode(a.getCode());
			sdto.setPrice(a.getPrice());
			sdtolist.add(sdto);
		}
		return sdtolist;
	}

	@Override
	@Cacheable(cacheNames = "getLatestPrice", key="#p0")
	public StockDTO getLatestPrice(int companyCode) {
		//template.send(kafkaTopic,"Get latest Stock Price - Company Code : " + companyCode);
		Stock stockInfo = null;
		stockInfo =stockRepo.findLatestPrice(companyCode);
		StockDTO sdto = new StockDTO();
		sdto.setCode(stockInfo.getCode());
		sdto.setPrice(stockInfo.getPrice());
		return sdto;
	}


	@Transactional
	@Override
	public DeleteStockDTO deleteAllStockPrice(int companyCode) {
		//template.send(kafkaTopic,"Delete All Stocks - Company Code : " + companyCode);
		Long stockInfo =stockRepo.deleteByCode(companyCode);
		DeleteStockDTO deleteDTO = new DeleteStockDTO();
		if (stockInfo > 0) {
			deleteDTO.setCount(stockInfo);
			deleteDTO.setMessage("Deletion Successful!");
		} else {
			deleteDTO.setCount(0);
			deleteDTO.setMessage("Deletion Unsuccessful!");
		}
		return deleteDTO;
	}

	@Override
	public List<Stock> getAllStockPrice(int companyCode) {
		//template.send(kafkaTopic,"Get All Stock Price - Company Code : " + companyCode);
		return stockRepo.findAllByCode(companyCode);
	}
	

	
	@Override
	public List<Stock> getStockDetailsByDate(int companycode, Date startdate, Date enddate) {
		//template.send(kafkaTopic,"Get All Stock Price within "+startdate+" and "+enddate+"- Company Code : " + companycode);
		enddate.setDate(enddate.getDate() + 1);
		return stockRepo.findStockPriceByDate(companycode, startdate, enddate);
	}
}
