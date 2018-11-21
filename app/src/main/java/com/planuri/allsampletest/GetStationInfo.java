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


public class GetStationInfo extends Thread {

    public String latitude;
    public String longitude;
    public StatList station;
    public StatList nearStation;
    public ArrayList<StatList> statLists;

    /* gps정보를 얻어올대 사용하는 생성자 BusWidgetTest의 gps 생성자도 주석을 풀어야함
    public GetStationInfo (GpsGetInfo gpsGetInfo) {
        this.latitude = String.valueOf(gpsGetInfo.getLatitude());
        this.longitude = String.valueOf(gpsGetInfo.getLongitude());
        Log.d("[TEST]", "la : " + latitude + "lo : " + longitude);
        run();
    }
    밑의 생성자는 고정된 gps주소를 사용 (홍제역)*/

    public GetStationInfo () {
        this.latitude = String.valueOf(37.588787);
        this.longitude = String.valueOf(126.944103);

        run();
    }

    public void connectServer() {

        statLists = new ArrayList<StatList>();

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
	// auth_key에 정부 발급 인증key 입력
            String auth_key = "key";
            String api_url = "http://ws.bus.go.kr/api/rest/stationinfo/getStationByPos?";
            URL url = new URL(api_url + "ServiceKey=" + auth_key + "&tmX=" + longitude + "&tmY=" + latitude + "&radius=500");
            // api 입력변수에 longitude -> latitude 순 radius는 검색 반경
            Log.d("[URL]", String.valueOf(url));


            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                if (parserEvent == XmlPullParser.START_TAG) {

                    String tagName = parser.getName();

                    if (tagName.equals("arsId")) {
                        station = new StatList();
                        station.setArsId(parser.nextText());
                        Log.d("[XML tag]", "IDtag = " + parser.getName() + " & " + station.getArsId());
                    }
                    if (tagName.equals("stationNm")) {
                        station.setStationNm(parser.nextText());
                        Log.d("[XML tag]", "NMtag = " + parser.getName() + " & " + station.getStationNm());
                    }
                    if (tagName.equals("stationTp")) {
                        station.setStationTp(parser.nextText());
                        Log.d("[XML tag]", "TMtag = " + parser.getName() + " & " + station.getStationTp());
                        statLists.add(station);
                    }
                }
                parserEvent = parser.next();
            }

            Log.d("[DEBUG]", "statList size = " + statLists.size());

        } catch (MalformedJsonException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //정류소 종류별로 type3의 버스중앙정거장을 우선으로 검색한다. -> type1,0의 경우 일반정거장과 마을버스정거장이 각각 표시되는경우가 많음
    public void selectStation() {
        for(int stp = 3; stp > 0; stp--) {
            for (int i = 0; i < statLists.size(); i++) {
                if (statLists.get(i).getStationTp().toString().equals(String.valueOf(stp))) {
                    nearStation = new StatList();
                    nearStation.setArsId(statLists.get(i).getArsId());
                    nearStation.setStationNm(statLists.get(i).getStationNm());
                    break;
                }
            }
            if(nearStation != null) { break; }
        }
        Log.d("[ST TAG]", "near id = " + nearStation.getArsId() + " & " + nearStation.getStationNm());
    }

    @Override
    public void run() {
        super.run();
        connectServer();
        selectStation();
    }

    public StatList getNearStation () {
        return nearStation;
    }
}