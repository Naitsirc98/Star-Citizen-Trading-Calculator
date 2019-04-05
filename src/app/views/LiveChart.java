package app.views;

import game.Commodity;
import game.Database;
import game.Purchase;
import game.Sale;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javafx.scene.chart.Axis;

public class LiveChart extends LineChart<Number, Number> {

    private static final int MAX_DATA_POINTS = 150;
    private int xSeriesData = 0;
    private ExecutorService executor;
    private String table;
    
    private ArrayList< XYChart.Series<Number, Number>> series;
    private ArrayList<ConcurrentLinkedQueue<Number>> data;
    private Float[] values;

    private NumberAxis xAxis;

    public LiveChart(String table) {
        super(new NumberAxis(0, MAX_DATA_POINTS, MAX_DATA_POINTS / 10), new NumberAxis());
        this.table = table;
        xAxis = (NumberAxis) this.getXAxis();
        setMinHeight(450);
        init();
    }

    private void init() {

        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);
        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);
        xAxis.setMinorTickVisible(false);

        setAnimated(false);
        setTitle("Animated Line Chart");
        setHorizontalGridLinesVisible(true);

        // Set Name for Series
        // series1.setName("Series 1");
        // series2.setName("Series 2");
        // series3.setName("Series 3");
        
        series = new ArrayList<>();
        data = new ArrayList<>();
        
        List<?> list = getValues();
        
        // TODO: generate series from database
        for(int i = 0;i < values.length;i++) {
            series.add(new Series<>());
            series.get(i).setName(list.get(i).toString());
            data.add(new ConcurrentLinkedQueue<>());
        }
        
        getData().addAll(series);

        // Add Chart Series
        // getData().addAll(series1, series2, series3);
        
        executor = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });

        AddToQueue addToQueue = new AddToQueue();
        executor.execute(addToQueue);
        //-- Prepare Timeline
        prepareTimeline();
        
    }

    private List<?> getValues() {
        
        if(table.equals("Purchasing")) {
            
            List<Purchase> ps = Database.getPurchases();
            
            values = new Float[ps.size()];
            
            for(int i = 0;i < values.length;i++) {
                values[i] = (float) ps.get(i).getPrice();
            }
            
            return ps;
            
        } else if(table.equals("Sales")) {
            
            List<Sale> ps = Database.getSales();
            
            values = new Float[ps.size()];
            
            for(int i = 0;i < values.length;i++) {
                values[i] = (float) ps.get(i).getPrice();
            }
            
            return ps;
            
        }
        
        throw new IllegalArgumentException("Unknown table " + table);
    }

    private class AddToQueue implements Runnable {

        public void run() {
            try {
                // add a item of random data to queue
                // dataQ1.add(Math.random());
                // dataQ2.add(Math.random());
                // dataQ3.add(Math.random());
                for(int i = 0;i < values.length;i++) {
                    data.get(i).add(Math.abs(values[i] 
                            += new Float(Math.random()-Math.random())*10));
                }
                
                Thread.sleep(1000);
                executor.execute(this);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    //-- Timeline gets called in the JavaFX Main thread
    private void prepareTimeline() {
        // Every frame to take any data from queue and add to chart
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                addDataToSeries();
            }
        }.start();
    }

    @Override
    protected void dataItemAdded(Series<Number, Number> series,
            int itemIndex, Data<Number, Number> item) {
        
    }

    private void addDataToSeries() {
        for (int i = 0; i < 50; i++) { //-- add 20 numbers to the plot+
            if (data.get(i).isEmpty()) {
                break;
            }
            
            for(int j = 0;j < series.size();j++) {
                series.get(j).getData().add(new XYChart.Data<>(xSeriesData++, data.get(j).remove()));
                if (series.get(j).getData().size() > MAX_DATA_POINTS) {
                    series.get(j).getData().remove(0, series.get(j).getData().size() - MAX_DATA_POINTS);
                }
            }
            
        }
        
        // update
        xAxis.setLowerBound(xSeriesData - MAX_DATA_POINTS);
        xAxis.setUpperBound(xSeriesData - 1);
        
        
        /*for (int i = 0; i < 20; i++) { //-- add 20 numbers to the plot+
            if (dataQ1.isEmpty()) {
                break;
            }
            series1.getData().add(new XYChart.Data<>(xSeriesData++, dataQ1.remove()));
            series2.getData().add(new XYChart.Data<>(xSeriesData++, dataQ2.remove()));
            series3.getData().add(new XYChart.Data<>(xSeriesData++, dataQ3.remove()));
        }
        // remove points to keep us at no more than MAX_DATA_POINTS
        if (series1.getData().size() > MAX_DATA_POINTS) {
            series1.getData().remove(0, series1.getData().size() - MAX_DATA_POINTS);
        }
        if (series2.getData().size() > MAX_DATA_POINTS) {
            series2.getData().remove(0, series2.getData().size() - MAX_DATA_POINTS);
        }
        if (series3.getData().size() > MAX_DATA_POINTS) {
            series3.getData().remove(0, series3.getData().size() - MAX_DATA_POINTS);
        }
        // update
        xAxis.setLowerBound(xSeriesData - MAX_DATA_POINTS);
        xAxis.setUpperBound(xSeriesData - 1);*/
    }

}
