public class TradeInfo {

    String trade;
    Double firstPrice;
    Double lastPrice;
    Double priceDiff;
    Double dealsValue;

    public TradeInfo(String trade, Double firstPrice, Double lastPrice, Double priceDiff, Double dealsValue) {
        this.trade = trade;
        this.firstPrice = firstPrice;
        this.lastPrice = lastPrice;
        this.priceDiff = priceDiff;
        this.dealsValue = dealsValue;
    }

    public int compareTo(TradeInfo o) {
        return Double.compare(priceDiff, o.priceDiff);
    }

    @Override
    public String toString() {
        String sign = priceDiff > 0 ? "-" : "+";
        return trade + " " + sign + Math.abs(priceDiff) * 100 / firstPrice + "% " + dealsValue;
    }
}
