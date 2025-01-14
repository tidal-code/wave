package dev.tidalcode.wave.httprequest;

import dev.tidalcode.flow.assertions.Assert;
import com.tidal.utils.filehandlers.FileReader;
import com.tidal.utils.json.JsonReader;
import dev.tidalcode.wave.httpRequest.ReqType;
import dev.tidalcode.wave.httpRequest.Request;
import org.junit.After;
import org.junit.Test;

import static dev.tidalcode.flow.assertions.Assert.verify;

public class RequestQueryParams {

    //ignore
    @Test
    public void testQueryParams() {
        Request.set("https://reqres.in");
        Request.setQueryParams("KeyOne", "ValueOne");
        Request.setQueryParams("KeyTwo", "ValueTwo");
        Request.setHeader("One", "Two");
        Request.send(ReqType.POST);
        System.out.println(Request.getResponseString());
        Request.reset();
    }

    @After
    public void afterTest() {
        Request.reset();
    }

    @Test
    public void queryParamTest() {
        Request.set("https://reqres.in/api/users");
        Request.setQueryParams("page", "2");
        Request.send(ReqType.GET);
        Assert.verify("", JsonReader.readValue("page", Request.getResponseString()).toString()).isEqualTo("2");
    }

    @Test
    public void getTest() {
        Request.set("https://reqres.in/api/users/2");
        Request.send(ReqType.GET);
        verify("", JsonReader.readValue("data.id", Request.getResponseString()).toString()).isEqualTo("2");
    }

    @Test
    public void postTest() {
        Request.set("https://reqres.in/api/users");
        Request.setPayload(FileReader.readFileToString("reqrespost.json"));
        Request.send(ReqType.POST);
        verify("", JsonReader.readValue("name", Request.getResponseString()).toString()).isEqualTo("morpheus");
    }

    @Test
    public void putTest() {
        Request.set("https://reqres.in/api/users/2");
        Request.setPayload(FileReader.readFileToString("reqresput.json"));
        Request.send(ReqType.PUT);
        verify("", JsonReader.readValue("name", Request.getResponseString()).toString()).isEqualTo("morpheus");
    }

    @Test
    public void patchTest() {
        Request.set("https://reqres.in/api/users");
        Request.setPayload(FileReader.readFileToString("reqrespost.json"));
        Request.send(ReqType.PATCH);
//        Request.logResponse();
        verify("", JsonReader.readValue("name", Request.getResponseString()).toString()).isEqualTo("morpheus");
    }

    @Test
    public void deleteTest() {
        Request.set("https://reqres.in/api/users/2");
        Request.setPayload(FileReader.readFileToString("reqrespost.json"));
        Request.send(ReqType.DELETE);
        verify("", Request.getResponseString()).isEqualTo("");
//        verify("", Request.getStatusCode()).isEqualTo(204);
    }

    @Test
    public void bobProvisionTest() {
//        Request.set("")
    }
}
