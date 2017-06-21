/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testsoap;


import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 */
public class SubWithouOtp {
    
    
    public String getWeather(String msisdn,String sid) throws MalformedURLException, IOException {

//Code to make a webservice HTTP request
        String responseString = "";
        String outputString = "";
        String wsURL = "http://127.0.0.1:80";
        URL url = new URL(wsURL);
        URLConnection connection = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection) connection;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        String xmlInput
                = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:char=\"http://getMessage.WebService.jeus.net\">\n"
                + "   <soapenv:Header/>\n"
                + "   <soapenv:Body>\n"
                + "      <char:add>\n"
                + "         <!--Optional:-->\n"
                + "         <username></username>\n"
                + "         <!--Optional:-->\n"
                + "         <password></password>\n"
                + "         <!--Optional:-->\n"
                + "         <domain>hamavaran</domain>\n"
                + "         <channel>5</channel>\n"
                + "         <!--Zero or more repetitions:-->\n"
                + "         <mobilenums>" + msisdn + "</mobilenums>\n"
                + "         <!--Zero or more repetitions:-->\n"
                + "         <serviceIds>" + sid + "</serviceIds>\n"
                + "      </char:addSubscriberWithoutOTP>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>ٓ";
        byte[] buffer = new byte[xmlInput.length()];
        buffer = xmlInput.getBytes();
        bout.write(buffer);
        byte[] b = bout.toByteArray();
        String SOAPAction = "http://litwinconsulting.com/webservices/GetWeather";
        // Set the appropriate HTTP parameters.
        httpConn.setRequestProperty("Content-Length",
                String.valueOf(b.length));
        httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        httpConn.setRequestProperty("SOAPAction", SOAPAction);
        httpConn.setRequestMethod("POST");
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        OutputStream out = httpConn.getOutputStream();
        //Write the content of the request to the outputstream of the HTTP Connection.
        out.write(b);
        out.close();
        //Ready with sending the request.

        //Read the response.
        InputStreamReader isr
                = new InputStreamReader(httpConn.getInputStream());
        BufferedReader in = new BufferedReader(isr);

        //Write the SOAP message response to a String.
        while ((responseString = in.readLine()) != null) {
            outputString = outputString + responseString;
        }
        //Parse the String output to a org.w3c.dom.Document and be able to reach every node with the org.w3c.dom API.
        Document document = parseXmlFile(outputString);
        NodeList nodeLst = document.getElementsByTagName("return");
        String weatherResult = nodeLst.item(0).getTextContent();
        System.out.println("Weather: " + weatherResult);

        //Write the SOAP message formatted to the console.
        String formattedSOAPResponse = formatXML(outputString);
        System.out.println(formattedSOAPResponse);
        return weatherResult;
    }

    public String formatXML(String unformattedXml) {
        try {
            Document document = parseXmlFile(unformattedXml);
            OutputFormat format = new OutputFormat(document);
            format.setIndenting(true);
            format.setIndent(3);
            format.setOmitXMLDeclaration(true);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
