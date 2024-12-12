/*
**Lauren Auer 
**4/27/2024
** IFT 210
*/


class TransactionHistory {
    
    private String ticker;
    private String transDate;
    private String transType;
    private double qty;
    private double costBasis;

    public TransactionHistory(String ticker, String transDate, String transType, double qty, double costBasis) {
        this.ticker = ticker;
        this.transDate = transDate;
        this.transType = transType;
        this.qty = qty;
        this.costBasis = costBasis;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public void setQty(double qty){
        this.qty = qty;
    }

    public void setCostBasis(double costBasis) {
        this.costBasis = costBasis;
    }

    public String getTicker() {
        return ticker;
    }

    public String getTransDate(){
        return transDate;
    }

    public String getTransType(){
        return transType;
    }

    public double getQty() {
        return qty;
    }

    public double getCostBasis(){
        return costBasis;
    }

    @Override
    public String toString() {
        return "TransactionHistory{" +
                "ticker='" + ticker + '\'' +
                ", transDate='" + transDate + '\'' +
                ", transType='" + transType + '\'' +
                ", qty=" + qty +
                ", costBasis=" + costBasis +
                '}';
    }
}