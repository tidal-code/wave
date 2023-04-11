package com.tidal.wave.csv;

import com.tidal.wave.scenario.ScenarioInfo;
import org.junit.Assert;
import org.junit.Test;

import static com.tidal.wave.data.GlobalData.getObjectData;


public class CSVReadTests {

    @Test
    public void readCSVTest() {
        CSVDataStore dataStore = new CSVDataStore();
        DataResolver<CSVDataStore> dataResolver = new DataResolver<>();
        dataResolver.resolveData(dataStore, "TestCSVFile", "testCase1");
        Assert.assertEquals("Texas", dataStore.getHeader1());
    }

    @Test
    public void readCSVTestWithObject() {
        ScenarioInfo.setScenarioName("testCase1");
        DataResolver.resolveDataObject(CSVDataStore.class, "TestCSVFile");
        CSVDataStore dataStore1 = (CSVDataStore)getObjectData().get("CSVDataStore");
        Assert.assertEquals("Texas", dataStore1.getHeader1());
    }

    @Test
    public void readCSVTestWithClassInstance() {
        ScenarioInfo.setScenarioName("testCase1");
        CSVDataStore csvDataStore = new CSVDataStore();
        DataResolver.resolveDataObject(csvDataStore, "TestCSVFile");
        CSVDataStore dataStore1 = (CSVDataStore)getObjectData().get("CSVDataStore");
        Assert.assertEquals("Texas", dataStore1.getHeader1());
    }


    @Test
    public void readCSVTestWithNonExisting() {
        CSVDataStore dataStore = new CSVDataStore();
        DataResolver<CSVDataStore> dataResolver = new DataResolver<>();
        dataResolver.resolveData(dataStore, "TestCSVFile", "testCaseX");
        Assert.assertNull(dataStore.getHeader1());
    }

    @Test
    public void copyPropertiesTest(){
        CSVDataStore dataStore = new CSVDataStore();
        DataResolver<CSVDataStore> dataResolver = new DataResolver<>();
        dataResolver.resolveData(dataStore, "TestCSVFile", "testCase1");

        CSVDataStoreCopy csvDataStoreCopy = new CSVDataStoreCopy();
        DataResolver.copyData(dataStore, csvDataStoreCopy);

        Assert.assertEquals(dataStore.getHeader1(), csvDataStoreCopy.getHeader1());
        Assert.assertEquals(dataStore.getHeader2(), csvDataStoreCopy.getHeader2());
    }

    @Test
    public void copyPropertiesTestFailing(){
        CSVDataStore dataStore = new CSVDataStore();
        DataResolver<CSVDataStore> dataResolver = new DataResolver<>();
        dataResolver.resolveData(dataStore, "TestCSVFile", "testCase1");

        CSVDataStoreCopy csvDataStoreCopy = new CSVDataStoreCopy();
        DataResolver.copyData(dataStore, csvDataStoreCopy);

        Assert.assertNotEquals(dataStore.getHeader1(), csvDataStoreCopy.getHeader2());
        Assert.assertNotEquals(dataStore.getHeader2(), csvDataStoreCopy.getHeader1());
    }

}

class CSVDataStore {
    private String header1;
    private String header2;

    public String getHeader1() {
        return header1;
    }

    public void setHeader1(String header1) {
        this.header1 = header1;
    }

    public String getHeader2() {
        return header2;
    }

    public void setHeader2(String header2) {
        this.header2 = header2;
    }
}

class CSVDataStoreCopy{
    private String header1;
    private String header2;

    public String getHeader1() {
        return header1;
    }

    public void setHeader1(String header1) {
        this.header1 = header1;
    }

    public String getHeader2() {
        return header2;
    }

    public void setHeader2(String header2) {
        this.header2 = header2;
    }
}




