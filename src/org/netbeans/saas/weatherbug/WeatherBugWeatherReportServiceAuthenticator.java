/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.saas.weatherbug;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 * @author naits
 */
public class WeatherBugWeatherReportServiceAuthenticator {
    
    private static String apiKey;
    
    private static final String PROP_FILE = WeatherBugWeatherReportServiceAuthenticator.class.getSimpleName().toLowerCase() + ".properties";
    
    static {
        try {
            Properties props = new Properties();
            props.load(WeatherBugWeatherReportServiceAuthenticator.class.getResourceAsStream(PROP_FILE));
            apiKey = props.getProperty("api_key");
        } catch (IOException ex) {
            Logger.getLogger(WeatherBugWeatherReportServiceAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String getApiKey() throws IOException {
        if (apiKey == null || apiKey.length() == 0) {
            throw new IOException("Please specify your api key in the " + PROP_FILE + " file.");
        }
        
        return apiKey;
    }
}
