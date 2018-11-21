package com.planuri.allsampletest;

import android.os.Build;
import android.os.StrictMode;
import android.util.Log;
import android.util.MalformedJsonException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class GetArrInfo extends Thread {

    public String arsId;
    public ArrList busInfo;

    public GetArrInfo(StatList statList) {
        this.arsId = statList.getArsId();
        run();
    }

    public ArrayList<ArrList> connectServer() {
        ArrayList<ArrList> list = new ArrayList<ArrList>();
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
	//auth_key 정부발급 api key
            String auth_key = "key";
            String api_url = "http://ws.bus.go.kr/api/rest/stationinfo/getStationByUid?";
            URL url = new URL(api_url + "ServiceKey=" + auth_key + "&arsId=" + this.arsId);

            Log.d("[URL]", String.valueOf(url));

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                if (parserEvent == XmlPullParser.START_TAG) {

                    String tagName = parser.getName();
                    if (tagName.equals("arrmsg1")) {
                        busInfo = new ArrList();
                        busInfo.setMsg1(parser.nextText());
                        Log.d("[XML tag]", "rtNm11 tag = " + parser.getName() + " & " + busInfo.getMsg1());
                    }
                    if (tagName.equals("arrmsg2")) {
                        busInfo.setMsg2(parser.nextText());
                        Log.d("[XML tag]", "rtNm22 tag = " + parser.getName() + " & " + busInfo.getMsg2());
                    }
                    if (tagName.equals("rtNm")) {
                        busInfo.setRtNm(parser.nextText());
                        Log.d("[XML tag]", "rtNm tag = " + parser.getName() + " & " + busInfo.getRtNmm());
                    }
                    if (tagName.equals("traTime1")) {
                        busInfo.setTraTime(parser.nextText());
                        Log.d("[XML tag]", "TIME tag = " + parser.getName() + " & " + busInfo.getTraTime());
                    }
                    if (tagName.equals("traTime2")) {
                        busInfo.setTraTime2(parser.nextText());
                        Log.d("[XML tag]", "TIME2 tag = " + parser.getName() + " & " + busInfo.getTraTime2());
                        list.add(busInfo);
                    }
                }
                parserEvent = parser.next();
            }

        } catch (MalformedJsonException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void run() {
        super.run();
    }
}
