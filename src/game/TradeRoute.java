package game;


public class TradeRoute {
    
    private final String origin;
    private final String destination;
    private final String commodity;
    private final float profit;

    public TradeRoute(String origin, String destination, String commodity, float profit) {
        this.origin = origin;
        this.destination = destination;
        this.commodity = commodity;
        this.profit = profit;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getCommodity() {
        return commodity;
    }

    public float getProfit() {
        return profit;
    }

    @Override
    public String toString() {
        return "TradeRoute{" + "origin=" + origin + ", destination=" + destination + ", commodity=" + commodity + ", profit=" + profit + '}';
    }
    
}
