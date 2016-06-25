//package com.trn.controller;
//
//import static org.fest.assertions.Assertions.assertThat;
//import static org.hamcrest.Matchers.*;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import java.util.*;
//
//import org.hamcrest.Matchers;
//import org.junit.*;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentCaptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.*;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import com.trn.config.MainConfig;
//import com.trn.dto.*;
//import com.trn.service.TrainService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {MainConfig.class})
//@WebAppConfiguration
//public class TrainControllerConfigurationTest {
//
//
//	static {
//        System.setProperty("uprr.implementation.environment", "dev");
//        System.setProperty("jbs.name", "localhost");
//    }
//
//	@Autowired
//    private WebApplicationContext webApplicationContext;
//
//	@Autowired
//    private TrainController controller;
//
//	private MockMvc mockMvc;
//
//	@Before
//	public void setUp() {
//		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//	}
//
//	@Test
//	public void testSampleGet() throws Exception {
//		controller.setTrainService(createMockTrainService());
//
//		MvcResult result = this.mockMvc.perform(get("/train")
//					.accept(MediaType.APPLICATION_JSON)               //requests a JSON Response
//				)
//				.andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//
//				.andExpect(jsonPath("$", hasSize(2)))  //see: http://www.petrikainulainen.net/programming/spring-framework/integration-testing-of-spring-mvc-applications-write-clean-assertions-with-jsonpath/
//
//				.andExpect(jsonPath("$[0].id",is("1234")))
//				.andExpect(jsonPath("$[0].symbol",is("ZTEST")))
//				.andExpect(jsonPath("$[0].day",is("01")))
//				.andExpect(jsonPath("$[0].arrivalLocation",is("WH123")))
//				.andExpect(jsonPath("$[0].arrived",is(false)))
//
//				.andExpect(jsonPath("$[1].id",is("2234")))
//				.andExpect(jsonPath("$[1].symbol",is("XTEST")))
//				.andExpect(jsonPath("$[1].day",is("28")))
//				.andExpect(jsonPath("$[1].arrivalLocation",is("WH234")))
//				.andExpect(jsonPath("$[1].arrived",is(true)))
//
//				.andReturn();
//		System.out.println("GET result: "+result.getResponse().getContentAsString());
//	}
//
//	private TrainService createMockTrainService() {
//		TrainService mockService = mock(TrainService.class);
//		List<Train> trainList = new ArrayList<>();
//		trainList.add(createTestTrain("1234", "ZTEST", "01", "WH123", false));
//		trainList.add(createTestTrain("2234", "XTEST", "28", "WH234", true));
//		when(mockService.list()).thenReturn(trainList);
//		return mockService;
//	}
//
//	private Train createTestTrain(String id, String symbol, String day,
//			String arrivalLocation, boolean arrived) {
//		Train train = new Train();
//		train.setId(id);
//		train.setSymbol(symbol);
//		train.setDay(day);
//		train.setArrivalLocation(arrivalLocation);
//		train.setArrived(arrived);
//		return train;
//	}
//
//
//	@Test
//    public void testQuery() throws Exception {
//	    TrainService mockTrainService = createMockTrainService();
//        String trainSymbol = "ZLAMN";
//        String arrivalCirc7 = "LA001";
//
//        ArgumentCaptor<TrainQuery> trainQueryCaptor = ArgumentCaptor.forClass(TrainQuery.class);
//	    List<Train> trainList = createTrainList();
//	    when(mockTrainService.query(trainQueryCaptor.capture())).thenReturn(trainList);
//
//        controller.setTrainService(mockTrainService);
//        MvcResult result = this.mockMvc.perform(get("/trains?symbol="+trainSymbol +"&arrivalCirc7="+arrivalCirc7)
//                    .accept(MediaType.APPLICATION_JSON)               //requests a JSON Response
//                ).andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//
//            .andExpect(jsonPath("$", hasSize(2)))  //see: http://www.petrikainulainen.net/programming/spring-framework/integration-testing-of-spring-mvc-applications-write-clean-assertions-with-jsonpath/
//
//            .andExpect(jsonPath("$[0].id",is("1234")))
//            .andExpect(jsonPath("$[0].symbol",is("ZLAMN1")))
//            .andExpect(jsonPath("$[0].day",is("01")))
//            .andExpect(jsonPath("$[0].arrivalLocation",is("LA001")))
//            .andExpect(jsonPath("$[0].arrived",is(true)))
//
//            .andExpect(jsonPath("$[1].id",is("1237")))
//            .andExpect(jsonPath("$[1].symbol",is("ZLAMN2")))
//            .andExpect(jsonPath("$[1].day",is("02")))
//            .andExpect(jsonPath("$[1].arrivalLocation",is("LA001")))
//            .andExpect(jsonPath("$[1].arrived",is(false)))
//            .andReturn();
//
//        TrainQuery trainQuery = trainQueryCaptor.getValue(); //check the request is correct
//        assertThat(trainQuery.getSymbol()).isEqualTo("ZLAMN");
//        assertThat(trainQuery.getArrivalLocation()).isEqualTo("LA001");
//
//        System.out.println("GET result: "+result.getResponse().getContentAsString());
//    }
//
//    private List<Train> createTrainList() {
//        List<Train> trainList = new ArrayList<Train>();
//	    Train t = new Train();
//	    t.setId("1234");
//	    t.setSymbol("ZLAMN1");
//	    t.setArrivalLocation("LA001");
//	    t.setArrived(true);
//	    t.setDay("01");
//	    trainList.add(t);
//	    t = new Train();
//        t.setId("1237");
//        t.setSymbol("ZLAMN2");
//        t.setArrivalLocation("LA001");
//        t.setArrived(false);
//        t.setDay("02");
//        trainList.add(t);
//        return trainList;
//    }
//
//
//	/**
//	 * Here we create an anonymous "stub" of the TrainService class
//	 * that just uses a HashMap instead of referencing the actual "database". ;)
//	 * @return
//	 */
//	private TrainService createStubTrainService() {
//		return new TrainService() {  //anonymous stub extends the TrainService class and overrides these methods
//			private final Map<String, Train>	trains	= new HashMap<>();
//
//			@Override
//            public String save(final Train train)
//			{
//				train.setId(STUB_TRAIN_ID);
//				trains.put(train.getId(), train);
//				return train.getId();
//			}
//
//			@Override
//            public Train get(final String trainID)
//			{
//				return trains.get(trainID);
//			}
//		};
//	}
//	private static final String STUB_TRAIN_ID = "53be34b1-96e4-4863-a244-4d6351dd10ab";
//	private static final String TRAIN_POST_DATA =
//            "{\"symbol\":\"ZLAMN6\","
//            + "\"day\":\"6\","
//            + "\"arrived\":true,"
//            + "\"arrivalLocation\":\"LA001\"}";
//
//	/**
//	 * Tests the ControllerAdviceDefault class
//	 * @throws Exception
//	 */
//	@Test
//	public void testThrowsException() throws Exception {
//		MvcResult result = this.mockMvc.perform(get("/exception")
//				.accept(MediaType.APPLICATION_JSON)               //requests a JSON Response
//			)
//			.andExpect(status().is(500))
//			.andExpect(content().string(equalTo("{\"title\":\"System Error\",\"message\":\"System error has occurred.\"}")))
//			.andReturn();
//	System.out.println("Exception GET result: "+result.getResponse().getContentAsString());
//	}
//
//	@Test
//	public void testThrowsExceptionMX() throws Exception {
//		MvcResult result = this.mockMvc.perform(get("/exception")
//				.locale(new Locale("es","MX"))
//				.accept(MediaType.APPLICATION_JSON)               //requests a JSON Response
//			)
//			.andExpect(status().is(500))
//			.andExpect(content().string(equalTo("{\"title\":\"Error del sistema\",\"message\":\"Error del sistema se ha producido.\"}")))
//			.andReturn();
//	System.out.println("Exception GET result: "+result.getResponse().getContentAsString());
//	}
//}
