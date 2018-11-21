/*
package com.planuri.allsampletest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(getApplicationContext(), intent);
    }
}

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final int mCount = 20;
    private Context mContext;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
    }

    public void onCreate() {

    }

    public void onDestroy() {

    }

    public int getCount() {
        return mCount;
    }

    public RemoteViews getViewAt(int position) {

//        RemoteViews remoteView = new RemoteViews(mContext.getPackageName(), R.layout.bus_widget_test); // 수정 1
//        remoteView.setTextViewText(R.id.appwidget_text, "Sample" + String.valueOf(position)); // 수정 2

//        Intent fillIntent = new Intent();
//        fillIntent.setData(Uri.parse("http://www.example.com." + position));
//        remoteView.setOnClickFillInIntent(R.id.appwidget_text, fillIntent); // 수정 3

//        return remoteView;
    }

    public RemoteViews getLoadingView() {
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void onDataSetChanged() {

    }

}

*/