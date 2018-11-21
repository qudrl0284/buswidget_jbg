package com.planuri.allsampletest;

/**
 * Created by OnDar on 2017-02-02.
 * arsId : 정류소 고유번호
 * stationNm : 정류소 이름
 * stationTp : 정류소 타입
 */

public class StatList {

    String arsId;
    String stationNm;
    String stationTp;

    public String getStationNm() {
        return stationNm;
    }

    public void setStationNm(String stationNm) {
        this.stationNm = stationNm;
    }

    public String getArsId() {
        return arsId;
    }

    public void setArsId(String arsId) {
        this.arsId = arsId;
    }

    public String getStationTp() {
        return stationTp;
    }

    public void setStationTp(String stationTp) {
        this.stationTp = stationTp;
    }
}
