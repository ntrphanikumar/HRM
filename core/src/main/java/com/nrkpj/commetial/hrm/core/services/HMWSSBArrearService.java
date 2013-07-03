package com.nrkpj.commetial.hrm.core.services;

import static java.lang.Double.parseDouble;
import static org.apache.commons.lang.StringUtils.substringAfter;
import static org.apache.commons.lang.StringUtils.substringBefore;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.nrkpj.commetial.hrm.core.dos.WaterMeter;

public class HMWSSBArrearService implements ArrearService<WaterMeter> {

    private String getResponseLineForAmount(String canNumber) {
        try {
            boolean nextLineValue = false;
            String amountLine = null;
            for (String line : getResponse(canNumber)) {
                if (nextLineValue) {
                    amountLine = line;
                    break;
                }
                nextLineValue = line.contains("Total Arrears :");
            }
            return amountLine;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private List<String> getResponse(String canNumber)
            throws URISyntaxException, HttpException, IOException {
        HttpPost post = new HttpPost(
                "https://www.billdesk.com/pgidsk/pgmerc/hmwssb/HMWSSBPaymentoption.jsp?canNumber="
                        + canNumber);
        HttpResponse response = new DefaultHttpClient().execute(post);
        InputStream content = response.getEntity().getContent();
        return IOUtils.readLines(content);
    }

    public static void main(String[] args) throws URISyntaxException {
        // System.out.println(new HMWSSBArrearService().getArrears(new
        // WaterMeterOld(
        // "615684827")));
        
        HttpGet httpGet = new HttpGet("https://mea.aconex.com/api/localcache/projects/536899480/register/609524455?username=jszymoniczek bnasdas");
        System.out.println(httpGet.getMethod());
    }

    public double getArrears(WaterMeter waterMeter) {
        String amountLine = getResponseLineForAmount(waterMeter.getCanNumber());
        if (amountLine != null) {
            return parseDouble(substringBefore(
                    substringAfter(amountLine, "Rs."), "<").trim());
        }
        throw new RuntimeException("Invalid can number : "
                + waterMeter.getCanNumber());
    }
}
