package com.stockmarket.stock.repository;


import java.util.Date;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stockmarket.stock.models.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

	@Query(value="select s.id, s.code, s.date, s.price from stock s inner join (select code, max(date) as MaxDate from stock group by code) m on s.code=m.code and s.date=m.MaxDate;", nativeQuery = true)
	List<Stock> findAllLatestPrice();

	@Query(value="select s.id, s.code, s.date, s.price from stock s inner join (select code, max(date) as MaxDate from stock group by code) m on s.code=m.code and s.date=m.MaxDate where s.code=:code", nativeQuery = true)
	Stock findLatestPrice(@Param("code") int code);

	@Query
	Long deleteByCode(int code);


	@Query(value="select s.id, s.code, s.date, s.price from stock s where s.code=:code and s.date>=:startdate and s.date<=:enddate", nativeQuery = true)
	List<Stock> findStockPriceByDate(@Param("code")int companycode, @Param("startdate") @Temporal Date startdate, @Param("enddate") @Temporal Date enddate);

	@Query
	List<Stock> findAllByCode(int code);

}
