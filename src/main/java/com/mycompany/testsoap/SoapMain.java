/**
 * This class is main class for call soap services as a client. 
 * u can add method same as get number from databases or get messages from queue
 */
package com.mycompany.testsoap;


import java.io.IOException;

import java.net.MalformedURLException;


public class SoapMain {

 /**
  * 
  * @param args 
  */
    public static void main(String[] args) {

        SubWithouOtp otp = new  SubWithouOtp();
        try {
            otp.getWeather("989110453639","7552");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

}
