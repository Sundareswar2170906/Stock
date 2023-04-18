package com.stockmarket.stock.service;

import java.util.Date;
import java.util.List;

import com.stockmarket.stock.dto.DeleteStockDTO;
import com.stockmarket.stock.dto.StockDTO;
import com.stockmarket.stock.models.Stock;


public interface IStockService {
	
	public Stock addStock(Stock stock);

	public List<StockDTO> getAllLatestPrice();

	public StockDTO getLatestPrice(int companyCode);

	public DeleteStockDTO deleteAllStockPrice(int companyCode);

	public Object getAllStockPrice(int companyCode);

	public List<Stock> getStockDetailsByDate(int companycode, Date startdate, Date enddate);
}
