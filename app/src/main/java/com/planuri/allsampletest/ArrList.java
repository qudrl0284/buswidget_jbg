package com.planuri.allsampletest;

/**
 * arrmsg1 : 첫 번째 도착 버스 메세지
 * arrmsg2 : 두 번째 도착 버스 메세지
 * rtNm : 버스 이름
 * traTime : 첫 번째 버스 도착 시간
 * traTime2 : 두 번째 버스 도착 시간
 */

public class ArrList {
    String msg1;
    String msg2;
    String rtNm;
    int traTime;
    int traTime2;

    public String getMsg1() {
        return msg1;
    }

    public void setMsg1(String msg) {
        this.msg1 = msg;
    }

    public String getMsg2() {
        return msg2;
    }

    public void setMsg2(String msg) {
        this.msg2 = msg;
    }

    public String getRtNmm() {
        return rtNm;
    }

    public void setRtNm(String rtNm) {
        this.rtNm = rtNm;
    }
    // 60으로 나누어 분단위로 출력
    public String getTraTime() {
        return String.valueOf(traTime / 60);
    }

    public void setTraTime(String traTime) {
        this.traTime = Integer.valueOf(traTime);
    }

    public String getTraTime2() {
        return String.valueOf(traTime2 / 60);
    }

    public void setTraTime2(String traTime2) {
        this.traTime2 = Integer.valueOf(traTime2);
    }

}