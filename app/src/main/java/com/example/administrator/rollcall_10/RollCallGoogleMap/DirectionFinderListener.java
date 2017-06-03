package com.example.administrator.rollcall_10.RollCallGoogleMap;

import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
