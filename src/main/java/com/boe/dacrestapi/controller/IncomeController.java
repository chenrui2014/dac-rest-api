package com.boe.dacrestapi.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boe.dacrestapi.model.Income;
import com.boe.dacrestapi.model.Painting;
import com.boe.dacrestapi.model.Transactions;
import com.boe.dacrestapi.model.User;
import com.boe.dacrestapi.service.IncomeService;
import com.boe.dacrestapi.service.PaintingService;
import com.boe.dacrestapi.service.TransactionsService;
import com.boe.dacrestapi.service.UserService;
import com.boe.dacrestapi.vo.IncomeDetailVO;

@RestController
public class IncomeController {

	@Autowired
	private IncomeService incomeService;
	@Autowired
	private UserService userService;
	@Autowired
	private TransactionsService tranService;
	@Autowired
	private PaintingService paintService;

	@RequestMapping(value = "/incomes", method = RequestMethod.GET)
	public List<IncomeDetailVO> getAllPaintings() {
		List<Income> incomeList = incomeService.findAll();
		return convertPOJOList(incomeList);
	}
	
	@RequestMapping(value="/userIncomesPerPage", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getUserPaintingPerPage(@RequestParam(value = "userId")Long userId, @RequestParam(value = "page")Integer page, @RequestParam(value = "size")Integer size){
		
		Sort sort = new Sort(Direction.DESC, "tranTime");
		Pageable pageable = new PageRequest(page,size,sort);
		Page<Income> paintingPage = incomeService.findByUserId(userId, pageable);
		Map<String, Object> body = new HashMap<>();
		body.put("data",convertPOJOList(paintingPage.getContent()));
		body.put("number", paintingPage.getNumber());
		body.put("size", paintingPage.getSize());
		body.put("totalElements", paintingPage.getTotalElements());
		body.put("totalPages", paintingPage.getTotalPages());
		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	
	@RequestMapping(value="/userIncomesNoPage", method = RequestMethod.GET)
	public List<IncomeDetailVO> getUserPaintingNoPage(@RequestParam(value = "userId")Long userId){

		List<Income> incomeList = incomeService.findByUserIdNoPage(userId);
		return convertPOJOList(incomeList);
	}

	private IncomeDetailVO convertPOJO(Income income) {

		IncomeDetailVO incomeDetailVO = new IncomeDetailVO();
		incomeDetailVO.setIncome(income.getAmount());
		incomeDetailVO.setRate(income.getRate());
		incomeDetailVO.setId(income.getId());
		Transactions tran = income.getTransactions();
		if(tran != null) {
			incomeDetailVO.setTransactionHash(tran.getTranId());
			incomeDetailVO.setTransactionId(tran.getId());
			incomeDetailVO.setTranAmount(tran.getTranAmount());
		}
		User user = income.getUser();
		if(user != null) {
			incomeDetailVO.setUserId(user.getId());
			incomeDetailVO.setUserName(user.getUserName());
			incomeDetailVO.setUserHash(user.getHashString());
		}
		Painting painting = income.getIncomePainting();
		if(painting != null) {
			incomeDetailVO.setPaintingHash(painting.getTransactionId());
			incomeDetailVO.setPaintingId(painting.getId());
			incomeDetailVO.setPaintingName(painting.getPaintName());
			incomeDetailVO.setPaintingGenFlag(painting.getGenFlag());
		}
		incomeDetailVO.setIncome(income.getIncome());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = formatter.format(income.getTranTime());
		incomeDetailVO.setTranTime(formattedDate);
		
		return incomeDetailVO;
	}

	private Income convertVO(IncomeDetailVO incomeDetailVO) {

		Income income = new Income();
		income.setAmount(incomeDetailVO.getIncome());
		income.setId(incomeDetailVO.getId());
		income.setRate(incomeDetailVO.getRate());
		Optional<Transactions> transactions = tranService.findById(incomeDetailVO.getTransactionId());
		income.setTransactions(transactions.orElse(null));
		
		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
		try {   
            ts = Timestamp.valueOf(incomeDetailVO.getTranTime());     
        } catch (Exception e) {   
            e.printStackTrace();   
        }  
		income.setTranTime(ts);
		income.setIncome(incomeDetailVO.getIncome());
		Optional<User> user = userService.findOne(incomeDetailVO.getUserId());
		income.setUser(user.orElse(null));
		Optional<Painting> painting = paintService.findById(incomeDetailVO.getPaintingId());
		income.setIncomePainting(painting.orElse(null));
		return income;
	}

	private List<IncomeDetailVO> convertPOJOList(List<Income> incomeList) {

		List<IncomeDetailVO> incomeDetailVOs = new ArrayList<IncomeDetailVO>();
		for(Income income:incomeList) {
			IncomeDetailVO incomeDetailVO = convertPOJO(income);
			incomeDetailVOs.add(incomeDetailVO);
		}
		return incomeDetailVOs;
	}

	private List<Income> convertPOJO(List<IncomeDetailVO> incomeDetailVOs) {
		List<Income> incomeList = new ArrayList<Income>();
		for(IncomeDetailVO incomeDetailVO:incomeDetailVOs) {
			Income income = convertVO(incomeDetailVO);
			incomeList.add(income);
		}
		return incomeList;
	}

}
