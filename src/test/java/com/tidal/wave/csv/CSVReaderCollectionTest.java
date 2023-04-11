package com.tidal.wave.csv;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CSVReaderCollectionTest {

    @Test
    public void collectionTest() {
        List<CSVCollectionModel> model = new DataResolver<CSVCollectionModel>()
                .resolveDataAsList(CSVCollectionModel.class, "TestCSVCollection", "Example Test");

        model.forEach(m -> System.out.println(m.getAddress()));

        assertEquals("Jamie", model.get(0).getFirstName());
        assertEquals("Ross", model.get(0).getLastName());
        assertEquals("47 Street", model.get(0).getAddress());
        assertEquals("Rossie", model.get(1).getFirstName());
        assertEquals("Sherry", model.get(1).getLastName());
        assertEquals("48 Street", model.get(1).getAddress());
        assertEquals("Annie", model.get(2).getFirstName());
        assertEquals("Marie", model.get(2).getLastName());
        assertEquals("49 Street", model.get(2).getAddress());
    }
}
