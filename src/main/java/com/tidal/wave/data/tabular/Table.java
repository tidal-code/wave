package com.tidal.wave.data.tabular;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Table {


    private final String tableData;

    public Table(String tableData) {
        this.tableData = tableData;
    }

    public List<String> asList() {
        return new TabularDataParser().asList(tableData);
    }

    public List<Map<String, String>> asTableList(){
        return new TabularDataParser().asTableList(tableData);
    }


    static class TableHeader {
        public List<String> asList() {
            return new LinkedList<>();
        }

    }

    static class TableRow {
        public List<String> asList() {
            return new LinkedList<>();
        }
    }

    static class TableColumn {
        public List<String> asList() {
            return new LinkedList<>();
        }
    }

}
