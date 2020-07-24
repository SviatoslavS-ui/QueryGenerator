package com.mainclass;

import libs.ExcelParser;

public class Main {
    public static void main(String[] args) {
        ExcelParser parser = new ExcelParser();
        System.out.println(parser.parse("dbformat.xlsx"));
    }
}
