package com.trn.dto;

import javax.validation.constraints.NotNull;

public class Train
{
	@NotNull
	private String	id;
	private String	symbol;
	private String	day;
	private Boolean	arrived;
	private String	arrivalLocation;

	public String getId()
	{
		return id;
	}

	public void setId(final String id)
	{
		this.id = id;
	}

	public String getSymbol()
	{
		return symbol;
	}

	public void setSymbol(final String symbol)
	{
		this.symbol = symbol;
	}

	public String getDay()
	{
		return day;
	}

	public void setDay(final String day)
	{
		this.day = day;
	}

	public String getArrivalLocation()
	{
		return arrivalLocation;
	}

	public void setArrivalLocation(final String arrivalLocation)
	{
		this.arrivalLocation = arrivalLocation;
	}

	public Boolean isArrived()
	{
		return arrived;
	}

	public void setArrived(final Boolean arrived)
	{
		this.arrived = arrived;
	}

	public String getTrainId()
	{
		return getSymbol() + " " + getId();
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("Train [id=").append(id).append(", symbol=").append(symbol).append(", day=").append(day).append(", arrived=").append(arrived).append(", arrivalLocation=")
		        .append(arrivalLocation).append("]");
		return builder.toString();
	}
}
