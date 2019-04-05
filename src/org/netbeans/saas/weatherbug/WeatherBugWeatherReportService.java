/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.saas.weatherbug;

import java.io.IOException;
import org.netbeans.saas.RestConnection;
import org.netbeans.saas.RestResponse;

/**
 * WeatherBugWeatherReportService Service
 *
 * @author naits
 */
public class WeatherBugWeatherReportService {

    /**
     * Creates a new instance of WeatherBugWeatherReportService
     */
    public WeatherBugWeatherReportService() {
    }
    
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Throwable th) {
        }
    }

    /**
     *
     * @param zipCode
     * @param cityCode
     * @param stationId
     * @param latitude
     * @param longitude
     * @param unitType
     * @param cityType
     * @return an instance of RestResponse
     */
    public static RestResponse getLiveCompactWeatherRSS(String zipCode, String cityCode, String stationId, String latitude, String longitude, String unitType, String cityType) throws IOException {
        String apiKey = WeatherBugWeatherReportServiceAuthenticator.getApiKey();
        String[][] pathParams = new String[][]{};
        String[][] queryParams = new String[][]{{"ACode", "" + apiKey + ""}, {"ZipCode", zipCode}, {"CityCode", cityCode}, {"StationId", stationId}, {"Latitude", latitude}, {"Longitude", longitude}, {"UnitType", unitType}, {"CityType", cityType}};
        RestConnection conn = new RestConnection("http://api.wxbug.net/getLiveCompactWeatherRSS.aspx", pathParams, queryParams);
        sleep(1000);
        return conn.get(null);
    }
}
