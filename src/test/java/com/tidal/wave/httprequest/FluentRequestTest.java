package com.tidal.wave.httprequest;

import com.tidal.utils.json.JsonReader;
import com.tidal.wave.httpRequest.FluentRequest;
import com.tidal.wave.httpRequest.ReqType;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.Test;


public class FluentRequestTest {

    @Test
    public void queryParamTest(){
        FluentRequest fluentRequest = new FluentRequest();

        Response page = fluentRequest
                .set("https://reqres.in/api/users")
                .setQueryParams("page", "2")
                .send(ReqType.GET)
                .response();

        Assert.assertEquals(JsonReader.readValue("page", fluentRequest.getResponseString()).toString(), "2");
    }

}
