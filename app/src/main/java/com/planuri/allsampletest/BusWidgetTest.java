package com.planuri.allsampletest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class BusWidgetTest extends AppWidgetProvider {

    private static int COUNT = 0;
    private static int index;
    private static final String TAG = "[TEST_WIDGET]";
    private static final int WIDGET_UPDATE_INTERVAL = 60000;
    private static PendingIntent updateSender;
    private static AlarmManager updateManager;
    private static GetStationInfo sti;
    private static ArrayList<ArrList> bus;

    // 버튼에 따른 각각의 묵시적 주소를 이용한 인텐트 생성
    private static final String filter_0 = "com.planuri.allsampletest.BUTTON_CLICKED_0";
    private static final String filter_1 = "com.planuri.allsampletest.BUTTON_CLICKED_1";
    private static final String filter_2 = "com.planuri.allsampletest.BUTTON_CLICKED_2";
    private static final String filter_3 = "com.planuri.allsampletest.BUTTON_CLICKED_3";
    private static final String filter_4 = "com.planuri.allsampletest.BUTTON_CLICKED_4";
    private static final String filter_5 = "com.planuri.allsampletest.BUTTON_CLICKED_5";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        final ComponentName provider = new ComponentName(context, this.getClass());
        String action = intent.getAction();
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bus_widget_test);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        // 위젯 업데이트 인텐트를 수신했을 때
        if(action.equals("android.appwidget.action.APPWIDGET_UPDATE")) {
            Log.d(TAG, "APPWIDGET_UPDATE (" + COUNT + ")");
            COUNT++;
            removePreviousAlarm(); // 기존의 알람을 제거

            long periodTime = System.currentTimeMillis() + WIDGET_UPDATE_INTERVAL;
            updateSender = PendingIntent.getBroadcast(context, 0, intent, 0);
            updateManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            updateManager.set(AlarmManager.RTC, periodTime, updateSender); // 새로운 알람 세팅
        }
        // 위젯 제거 인텐트를 수신했을 때
        else if(action.equals("android.appwidget.action.APPWIDGET_DISABLED")) {
            Log.d(TAG, "DISABLED!");
            removePreviousAlarm();
        }


        // 각각의 박스를 클릭시 인텐트로 구별하여 각각의 주소값에 따라 버튼을 구분
        if(action.equals(filter_0)) {
            Log.d("[CLICK TEST]", "Clicked 000000");
            showBus(context, views, index);
            appWidgetManager.updateAppWidget(provider, views);
        }
        else if(action.equals(filter_1)) {
            Log.d("[CLICK TEST]", "Clicked 111111");
            showBus(context, views, index + 1);
            appWidgetManager.updateAppWidget(provider, views);
        }
        else if(action.equals(filter_2)) {
            Log.d("[CLICK TEST]", "Clicked 222222");
            showBus(context, views, index + 2);
            appWidgetManager.updateAppWidget(provider, views);
        }
        else if(action.equals(filter_3)) {
            Log.d("[CLICK TEST]", "Clicked 333333");
            showBus(context, views, index + 3);
            appWidgetManager.updateAppWidget(provider, views);
        }
        else if(action.equals(filter_4)) {
            Log.d("[CLICK TEST]", "Clicked 444444");
            showBus(context, views, index + 4);
            appWidgetManager.updateAppWidget(provider, views);
        }
        else if(action.equals(filter_5)) {
            Log.d("[CLICK TEST]", "Clicked 555555");
            showBus(context, views, index + 5);
            appWidgetManager.updateAppWidget(provider, views);
        }


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        final int N = appWidgetIds.length;
        for(int i = 0 ; i < N ; i++)
        {
            int appWidgetId = appWidgetIds[i];
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        sti = new GetStationInfo();
        String soonMsg = new String();
        bus = new ArrayList<ArrList>();
        bus = new GetArrInfo(sti.getNearStation()).connectServer(); // 위젯을 시작할 때 찾아낸 정류장의 버스 정보를 가져온다.
        index = 0;

        Collections.sort(bus, new Comparator<ArrList>() {
            @Override
            public int compare(ArrList lhs, ArrList rhs) {
                return (lhs.traTime < rhs.traTime) ? -1 : (lhs.traTime > rhs.traTime) ? 1 : 0;
            }
        });

        for(int i = 0; i < 3; i++) {
            Log.d("SORT TEST", bus.get(i).getMsg1().toString() + bus.get(i).getTraTime().toString());
        }

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bus_widget_test);

        views.setTextViewText(R.id.titleTextView, "BUS"); // 제목
        views.setTextViewText(R.id.stationNMTv, sti.getNearStation().getStationNm()); // 가장 가까운 역이름
        views.setTextViewText(R.id.staticText, "곧 도착하는\n버스");

        for(int i = 0; i < bus.size(); i++) {
            if(Integer.valueOf(bus.get(index).getTraTime()) < 2) {
                if(bus.get(i).getMsg1().toString().equals("곧 도착")) {
                    soonMsg += bus.get(i).getRtNmm().toString() + "\n";
                    Log.d("[TEXT TEST]", bus.get(i).getRtNmm().toString());
                    index++;
                    if(index == 4) {
                        index = i;
                        break;
                    }
                }
            }
            else {
                index = i;
                break;
            }
        }
        if(soonMsg.length() != 0)
            soonMsg = soonMsg.substring(0, soonMsg.length() - 1);
        Log.d("[TEXT TEST 22222]", soonMsg);
        views.setTextViewText(R.id.arrBusText, soonMsg);

        String packName = context.getPackageName();
        String resBus, resTime;
        int busID, timeID;

        for(int i = 0; i < 6; i++) {
            resBus = "busNM" + i;
            resTime = "busTime" + i;
            busID = context.getResources().getIdentifier(resBus, "id", packName);
            timeID = context.getResources().getIdentifier(resTime, "id", packName);

            Log.d("[ID CHECK]", "busid = " + busID + " timeid = " + timeID);

            if(bus.get(i+index).getRtNmm().length() > 7) {
                views.setTextViewTextSize(busID, TypedValue.COMPLEX_UNIT_PT, 6);
            }
            else if(bus.get(i+index).getRtNmm().length() > 4) {
                views.setTextViewTextSize(busID, TypedValue.COMPLEX_UNIT_PT, 7);
            }
            else {
                views.setTextViewTextSize(busID, TypedValue.COMPLEX_UNIT_PT, 10);
            }

            views.setTextViewText(busID, bus.get(i + index).getRtNmm().toString());
            views.setTextViewText(timeID, bus.get(i + index).getTraTime().toString() + " 분");
        }

        Intent intent_0 = new Intent(filter_0);
        PendingIntent pendingIntent_0 = PendingIntent.getBroadcast(context, 0, intent_0, 0);
        views.setOnClickPendingIntent(R.id.busBox0, pendingIntent_0);

        Intent intent_1 = new Intent(filter_1);
        PendingIntent pendingIntent_1 = PendingIntent.getBroadcast(context, 0, intent_1, 0);
        views.setOnClickPendingIntent(R.id.busBox1, pendingIntent_1);

        Intent intent_2 = new Intent(filter_2);
        PendingIntent pendingIntent_2 = PendingIntent.getBroadcast(context, 0, intent_2, 0);
        views.setOnClickPendingIntent(R.id.busBox2, pendingIntent_2);

        Intent intent_3 = new Intent(filter_3);
        PendingIntent pendingIntent_3 = PendingIntent.getBroadcast(context, 0, intent_3, 0);
        views.setOnClickPendingIntent(R.id.busBox3, pendingIntent_3);

        Intent intent_4 = new Intent(filter_4);
        PendingIntent pendingIntent_4 = PendingIntent.getBroadcast(context, 0, intent_4, 0);
        views.setOnClickPendingIntent(R.id.busBox4, pendingIntent_4);

        Intent intent_5 = new Intent(filter_5);
        PendingIntent pendingIntent_5 = PendingIntent.getBroadcast(context, 0, intent_5, 0);
        views.setOnClickPendingIntent(R.id.busBox5, pendingIntent_5);

        showBus(context, views, index);

//        Log.d("[START]", "GstStation() Hello!");
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
//        GpsGetInfo gps = new GpsGetInfo(context); // 위젯을 시작할 때 현재의 위치 정보를 가져온다.
//        sti = new GetStationInfo(gps); // 위치를 시작할 때 현 위치 정보를 토대로 가장 가까운 정류장을 찾아낸다.
//        Log.d("[START]", "GstStation() END!");
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    // 예약되어 있는 알람을 취소.
    public void removePreviousAlarm()
    {
        if(updateManager != null && updateSender != null)
        {
            updateSender.cancel();
            updateManager.cancel(updateSender);
        }
    }

    // 클릭한 iconBox가 출력된 list의 인덱스를 넘겨받아 상단의 busStationFrame에 남은정거장을 가시적으로 출력
    public void showBus(Context context, RemoteViews views, int n) {

        int msg1, msg2, busID, timeID, iconID;
        String packName = context.getPackageName();
        String resName, resBus, resTime, icon;

        msg1 = Integer.valueOf(msgCut(bus.get(n).getMsg1().toString()));
        msg2 = Integer.valueOf(msgCut(bus.get(n).getMsg2().toString()));

        // 전체 버스 이미지를 invisible 하게
        for(int i = 0; i < 5; i++)
        {
            icon = "busIcon" + i;
            iconID = context.getResources().getIdentifier(icon, "id", packName);
            views.setViewVisibility(iconID, View.INVISIBLE);
        }

        // 새로 갱신된 정보로 표시되어야할 버스만 visible하게 변경하여 출력
        if(msg1 < 5) {

            resBus = "stat_NM" + msg1;
            resTime = "stat_Time" + msg1;
            icon = "busIcon" + msg1;
            busID = context.getResources().getIdentifier(resBus, "id", packName);
            timeID = context.getResources().getIdentifier(resTime, "id", packName);
            iconID = context.getResources().getIdentifier(icon, "id", packName);

            views.setViewVisibility(iconID, View.VISIBLE);
            views.setTextViewText(busID, bus.get(n).getRtNmm().toString());
            views.setTextViewText(timeID, bus.get(n).getTraTime().toString() + "  min");

        }
        if(msg2 < 5) {

            resBus = "stat_NM" + msg2;
            resTime = "stat_Time" + msg2;
            icon = "busIcon" + msg2;
            busID = context.getResources().getIdentifier(resBus, "id", packName);
            timeID = context.getResources().getIdentifier(resTime, "id", packName);
            iconID = context.getResources().getIdentifier(icon, "id", packName);

            views.setViewVisibility(iconID, View.VISIBLE);
            views.setTextViewText(busID, bus.get(n).getRtNmm().toString());
            views.setTextViewText(timeID, bus.get(n).getTraTime2().toString() + "  min");

        }
    }

    // api에서 arrmsg로 받아온 문자를 잘라 정거장이 몇개 남았는지 숫자만 잘라냄
    public String msgCut(String cut){
        if (cut.startsWith("곧") != true) {
            cut = cut.substring(cut.indexOf('[') + 1, cut.indexOf('번'));
        }
        else {
            cut = "0";
        }

        return cut;
    }
}
