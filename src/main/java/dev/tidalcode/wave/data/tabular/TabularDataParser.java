package dev.tidalcode.wave.data.tabular;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TabularDataParser {

    Logger logger = LoggerFactory.getLogger(TabularDataParser.class);

    public List<String> asList(String tableData) {
        List<String> data = new LinkedList<>();

        Document doc = Jsoup.parse(tableData);
        Element table = doc.select("table").first();

        if (null == table) {
            logger.info("NO TABLE IS FOUND");
        }
        Elements rows = table.select("tr");

        for (Element row : rows) {
            Elements cells = row.select("td");
            for (Element cell : cells) {
                data.add(cell.text() + "\t");
            }
        }
        return data;
    }


    public List<Map<String, String>> asTableList(String htmlTable) {
        Document doc = Jsoup.parse(htmlTable);
        Element table = doc.select("table").first();

        if (null == table) {
            logger.info("NO TABLE IS FOUND");
            return null;
        }

        Elements rows = table.select("tbody tr");

        List<Map<String, String>> tableData = new ArrayList<>();

        for (Element row : rows) {
            Elements cells = row.select("td,th");

            Hashtable<String, String> rowData = new Hashtable<>();

            for (int i = 0; i < cells.size(); i++) {
                Element cell = cells.get(i);
                String header = cell.attr("scope"); // Check if the cell has a "scope" attribute
                String columnName = header.isEmpty() ? "Column" + (i + 1) : header; // Use "ColumnX" if no scope

                rowData.put(columnName, cell.text());
            }

            // Add the hash table to the list
            tableData.add(rowData);
        }

        return tableData;
    }
}
