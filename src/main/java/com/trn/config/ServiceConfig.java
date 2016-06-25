package com.trn.config;

import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.trn.dto.Train;
import com.trn.service.TrainService;

@Configuration
public class ServiceConfig
{
	private static final Random	RANDOM	= new Random();

	@PostConstruct
	public void init()
	{
		simulateData();
	}

	/**
	 * Of course, in an actual system, the data would come from some other service or database. 
	 */
	private void simulateData()
	{
		final TrainService service = trainService();

		for (int i = 0; i <= 30; i++)
		{
			final Train train = new Train();

		  //****CHANGED TO BE REAL CIRC7 LOCATIONS****
		  train.setArrivalLocation("MX2" + (83 + i % 5));
		  train.setArrived(RANDOM.nextBoolean());
		  train.setDay(Integer.toString(i));
		  //****CHANGED TO HAVE VARIETY AND CONSISTENCY AT THE SAME TIME****   '
		  if(i%3 == 0){
				train.setSymbol("MRVHK");
		  }else if(i%3 == 1){
				train.setSymbol("MHOKC");
		  }else{
				train.setSymbol("HTEST");
		  }

			service.save(train);
		}
	}

	@Bean
	public TrainService trainService()
	{
		return new TrainService();
	}
}
