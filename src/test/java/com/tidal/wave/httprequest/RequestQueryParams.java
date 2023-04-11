package com.tidal.wave.httprequest;

import com.tidal.wave.httpRequest.ReqType;
import com.tidal.wave.httpRequest.Request;

public class RequestQueryParams {

    //ignore
    public void testQueryParams(){
        Request.set("https://reqres.in");
        Request.setQueryParams("KeyOne", "ValueOne");
        Request.setQueryParams("KeyTwo", "ValueTwo");
        Request.setHeader("One", "Two");
        Request.send(ReqType.POST);
        Request.reset();
    }
}
