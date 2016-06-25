package com.trn.dto;

public class TrainQuery {
    private final String symbol;
    private final String arrivalLocation;

    private TrainQuery(String symbol, String arrivalLocation){
        this.symbol = symbol;
        this.arrivalLocation = arrivalLocation;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public static class Builder{
        private String symbol;
        private String arrivalLocation;

        public Builder withSymbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder withArrivalLocation(String arrivalLocation) {
            this.arrivalLocation = arrivalLocation;
            return this;
        }

        public TrainQuery build() {
            return new TrainQuery(symbol, arrivalLocation);
        }
    }
}
