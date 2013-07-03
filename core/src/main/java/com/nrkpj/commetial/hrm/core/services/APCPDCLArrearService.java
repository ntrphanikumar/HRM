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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.nrkpj.commetial.hrm.core.dos.CurrentMeter;

public class APCPDCLArrearService implements ArrearService<CurrentMeter> {

    private static final String AMOUNT_LINE = "<td width=\"25%\" align=\"left\" nowrap class=\"style7\" BGCOLOR=\"#FFFFFF\">";

    private String getResponseLineForAmount(String circle, String ero,
            String serviceNum) throws URISyntaxException, HttpException,
            IOException {
        int lineNum = 0;
        for (String line : getResponse(circle, ero, serviceNum)) {
            if (line.contains(AMOUNT_LINE) && ++lineNum == 6) {
                return line;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private List<String> getResponse(String circle, String ero,
            String serviceNum) throws URISyntaxException, HttpException,
            IOException {
        HttpPost post = new HttpPost(
                "http://www.apcentralpower.com/Billdesk/usBillMonthPayment.jsp?circle="
                        + circle + "&cirlce1=&ero=" + ero + "&msg=&sno="
                        + serviceNum.replaceAll(" ", "%20") + "&usno=");
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(post);
        InputStream content = response.getEntity().getContent();
        return IOUtils.readLines(content);
    }

    public static void main(String[] args) throws URISyntaxException,
            HttpException, IOException {
        // System.out.println(new APCPDCLArrearService()
        // .getArrears(new ElectricityMeter("RRN", "13", "6219 01867")));
    }

    public double getArrears(CurrentMeter currentMeter) {
        try {
            if(currentMeter.isSubMeter()){
                return 0;
            }
            String amountLine = getResponseLineForAmount(currentMeter
                    .getHouse().getCircle(), currentMeter.getHouse().getEro(),
                    currentMeter.getScno());
            if (amountLine != null) {
                return parseDouble(substringBefore(
                        substringAfter(amountLine, "Rs.&nbsp;"), "<").trim());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Invalid service number : "
                + currentMeter.getScno());
    }
}
