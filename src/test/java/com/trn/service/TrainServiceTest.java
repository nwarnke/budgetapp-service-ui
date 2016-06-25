//package com.trn.service;
//
//import com.trn.dto.Train;
//import com.trn.dto.TrainQuery;
//import com.trn.service.TrainService;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//
//public class TrainServiceTest {
//
//    Train train1 = new Train();
//    Train train2 = new Train();
//    Train train3 = new Train();
//    Train train4 = new Train();
//
//    @Before
//    public void setUp(){
//        train1.setSymbol("HTEST");
//        train1.setDay("21");
//        train1.setArrivalLocation("NX001");
//        train1.setId("1");
//
//        train2.setSymbol("MTEST");
//        train2.setDay("23");
//        train2.setArrivalLocation("MX283");
//        train2.setId("2");
//
//        train3.setSymbol("HTEST");
//        train3.setDay("24");
//        train3.setArrivalLocation("MX283");
//        train3.setId("3");
//
//        train4.setSymbol("MTEST");
//        train4.setDay("22");
//        train4.setArrivalLocation("NX001");
//        train4.setId("4");
//    }
//
//
//    @Test public void savedTrainsAreFoundInTrainList(){
//        TrainService trainService = new TrainService();
//
//        trainService.save(train1);
//        trainService.save(train2);
//
//        List<Train> list = trainService.list();
//        assertEquals(Arrays.asList(train1, train2), list);
//    }
//
//    @Test
//    public void queryCanFindBasedOnSymbol(){
//        TrainService trainService = new TrainService();
//
//        trainService.save(train1);
//        trainService.save(train2);
//        trainService.save(train3);
//        trainService.save(train4);
//
//        List<Train> list = trainService.query(new TrainQuery.Builder().withSymbol("HTEST").build());
//        assertEquals(Arrays.asList(train1, train3), list);
//    }
//
//    @Test
//    public void queryCanFindBasedOnArrivalLocation(){
//        TrainService trainService = new TrainService();
//
//        trainService.save(train1);
//        trainService.save(train2);
//        trainService.save(train3);
//        trainService.save(train4);
//
//        List<Train> list = trainService.query(new TrainQuery.Builder().withArrivalLocation("MX283").build());
//        assertEquals(Arrays.asList(train2, train3), list);
//    }
//
//    @Test
//    public void queryCanFindBasedOnSymbolAndArrivalLocationTogether(){
//        TrainService trainService = new TrainService();
//
//        trainService.save(train1);
//        trainService.save(train2);
//        trainService.save(train3);
//        trainService.save(train4);
//
//        List<Train> list = trainService.query(new TrainQuery.Builder().withSymbol("MTEST").withArrivalLocation("NX001").build());
//        assertEquals(Arrays.asList(train4), list);
//    }
//
//    @Test
//    public void queryWithBlankParametersReturnsAllTrains(){
//        TrainService trainService = new TrainService();
//
//        trainService.save(train1);
//        trainService.save(train2);
//        trainService.save(train3);
//        trainService.save(train4);
//
//        List<Train> list = trainService.query(new TrainQuery.Builder().build());
//        assertEquals(Arrays.asList(train1, train2, train3, train4), list);
//    }
//
//    @Test
//    public void queryTreatsEmptyStringsSameAsNull(){
//        TrainService trainService = new TrainService();
//
//        trainService.save(train1);
//        trainService.save(train2);
//        trainService.save(train3);
//        trainService.save(train4);
//
//        List<Train> list = trainService.query(new TrainQuery.Builder().withSymbol("").withArrivalLocation("").build());
//        assertEquals(Arrays.asList(train1, train2, train3, train4), list);
//    }
//}
