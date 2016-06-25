package com.trn.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.util.StringUtils;

import com.trn.dto.Train;
import com.trn.dto.TrainQuery;

public class TrainService
{
	private final Map<String, Train>	trains	= new HashMap<String, Train>();

	public String save(final Train train)
	{
		if (train.getId() == null) {
	      train.setId(UUID.randomUUID().toString());
		}
		trains.put(train.getId(), train);
		return train.getId();
	}

	public List<Train> list()
	{
		return new ArrayList<Train>(trains.values());
	}

	public Train get(final String trainID)
	{
		return trains.get(trainID);
	}

  public List<Train> query(TrainQuery trainQuery){
		List<Train> allTrains = list();
		List<Train> matchingTrains = new ArrayList<>();
		for(Train train : allTrains){
				if(symbolMatches(train,trainQuery) && arrivalLocationMatches(train,trainQuery)){
						matchingTrains.add(train);
				}
		}
		return matchingTrains;
  }

	private boolean symbolMatches(Train train, TrainQuery trainQuery){
		return StringUtils.isEmpty(trainQuery.getSymbol()) || train.getSymbol().equalsIgnoreCase(trainQuery.getSymbol());
	}

	private boolean arrivalLocationMatches(Train train, TrainQuery trainQuery){
		return StringUtils.isEmpty(trainQuery.getArrivalLocation()) || train.getArrivalLocation().equalsIgnoreCase(trainQuery.getArrivalLocation());
	}
}
