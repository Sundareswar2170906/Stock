package com.stockmarket.stock.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stockmarket.stock.dto.DeleteStockDTO;
import com.stockmarket.stock.dto.StockDTO;
import com.stockmarket.stock.models.Stock;
import com.stockmarket.stock.service.IStockService;

@RestController
@CrossOrigin(origins = "*")
public class StockController {

	@Autowired
	private IStockService stockService;

	// As a user I am be able to add stock price for any company
	@PostMapping("/api/v1.0/market/stock/add/{companyCode}")
	public ResponseEntity<Stock> addStockPrice(@PathVariable("companyCode") int companyCode, @RequestBody Stock stock) {
		stock.setCode(companyCode);
		Stock a = stockService.addStock(stock);
		return new ResponseEntity<Stock>(a, HttpStatus.CREATED);
	}

	// If all company details are demanded it show list of all companies with their
	// latest stock price
	@GetMapping("/api/v1.0/market/stock/latest/price")
	public ResponseEntity<List<StockDTO>> getStockPrice() {
		List<StockDTO> all = stockService.getAllLatestPrice();		
		return new ResponseEntity<List<StockDTO>>(all, HttpStatus.OK);
	}

	// If a single company, details are demanded it show complete details of company
	// along with latest stock price.
	@GetMapping("/api/v1.0/market/stock/latest/price/{companyCode}")
	public ResponseEntity<StockDTO> getStockPrice(@PathVariable int companyCode) {
		StockDTO stockInfo = stockService.getLatestPrice(companyCode);
		return new ResponseEntity<StockDTO>(stockInfo, HttpStatus.OK);
	}

	// If company is deleted, it also deletes all stock price data associated with
	// the company.
	@DeleteMapping("/api/v1.0/market/stock/delete/{companyCode}")
	public ResponseEntity<DeleteStockDTO> deleteAllStockPrice(@PathVariable int companyCode) {
		DeleteStockDTO deleteStatus = stockService.deleteAllStockPrice(companyCode);
		return new ResponseEntity<DeleteStockDTO>(deleteStatus, HttpStatus.OK);
	}

	// List of stock price of a company is viewed based on start date and end date.
	@GetMapping("/api/v/1.0/market/stock/get/{companycode}")
	public ResponseEntity<List<Stock>> getStockDetails(@PathVariable int companycode) {
		Date startdate = new Date();
		startdate.setDate(31);
		startdate.setMonth(1);
		startdate.setYear(0);
		Date enddate = new Date();
		List<Stock> stlist = stockService.getStockDetailsByDate(companycode, startdate, enddate);
		return new ResponseEntity<List<Stock>>(stlist, HttpStatus.OK);
	}

	// List of stock price of a company is viewed based on start date and end date.
	@GetMapping("/api/v/1.0/market/stock/get/{companycode}/{startdate}")
	public ResponseEntity<List<Stock>> getStockDetailsByStartDate(@PathVariable int companycode,
			@PathVariable(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startdate) {
		Date enddate = new Date();
		List<Stock> stlist = stockService.getStockDetailsByDate(companycode, startdate, enddate);
		return new ResponseEntity<List<Stock>>(stlist, HttpStatus.OK);
	}

	// List of stock price of a company is viewed based on start date and end date.
	@GetMapping("/api/v/1.0/market/stock/get/{companycode}//{enddate}")
	public ResponseEntity<List<Stock>> getStockDetailsByEndDate(@PathVariable int companycode,
			@PathVariable(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date enddate) {
		Date startdate = new Date();
		startdate.setDate(31);
		startdate.setMonth(1);
		startdate.setYear(0);
		List<Stock> stlist = stockService.getStockDetailsByDate(companycode, startdate, enddate);
		return new ResponseEntity<List<Stock>>(stlist, HttpStatus.OK);
	}

	// List of stock price of a company is viewed based on start date and end date.
	@GetMapping("/api/v/1.0/market/stock/get/{companycode}/{startdate}/{enddate}")
	public ResponseEntity<List<Stock>> getStockDetailsByDate(@PathVariable int companycode,
			@PathVariable(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startdate,
			@PathVariable(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date enddate) {
		List<Stock> stlist = stockService.getStockDetailsByDate(companycode, startdate, enddate);
		return new ResponseEntity<List<Stock>>(stlist, HttpStatus.OK);
	}
	
	@GetMapping("/ping")
	public ResponseEntity<Stock> getPing() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
