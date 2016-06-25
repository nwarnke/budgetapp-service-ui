//package com.trn.controller;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.trn.dto.Train;
//import com.trn.dto.TrainQuery;
//import com.trn.service.TrainService;
//
//@Controller
///**
// * This is the main class handling the all JSON requests for this request map URL.
// * Modify the mapping above to correspond with your application URL, and then add mappings below
// * for any actions supported.
// */
//public class TrainController
//{
//	private static final Logger LOG = LoggerFactory.getLogger(TrainController.class);
//	private static final TrainQuery.Builder TRAIN_QUERY_BUILDER = new TrainQuery.Builder();
//
//	private TrainService	service;
//
//	@Autowired
//	public TrainController(TrainService trainService) {
//		service = trainService;
//	}
//
//	/**
//	 * protected setter to allow configuration test class to override
//	 * @param service
//	 */
//	protected void setTrainService(TrainService service) {
//		this.service = service;
//	}
//
//	@RequestMapping(value = "trains", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public List<Train> query(String symbol, String arrivalCirc7)
//	{
//		return service.query(TRAIN_QUERY_BUILDER.withSymbol(symbol).withArrivalLocation(arrivalCirc7).build());
//	}
//
//	@RequestMapping(value = "train", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public List<Train> list()
//	{
//		return service.list();
//	}
//
//
//	@RequestMapping(value = "train/{trainID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public Train doGet(@PathVariable final String trainID)
//	{
//		return service.get(trainID);
//	}
//
//	@RequestMapping(value = "exception", method = RequestMethod.GET)
//	@ResponseBody
//	public void exception()
//	{
//		throw new RuntimeException("Epic Exception");
//	}
//}
