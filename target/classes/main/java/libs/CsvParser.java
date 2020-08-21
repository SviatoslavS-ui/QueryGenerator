package libs;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.io.IOException;

public class CsvParser {
    protected String result;

    public String parse(String filename) throws IOException {
        CSVReader csvReader = null;
        String[] nextRecord = null;

        result = "EXISTS(select * from Games where ";

        FileReader fileReader = new FileReader(filename);
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        csvReader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();
        if ((nextRecord = csvReader.readNext()) == null) throw new IOException();

        while ((nextRecord = csvReader.readNext()) != null) updateResultWithNewRecord(nextRecord[0], nextRecord[1]);
        result = result.substring(0, result.length() - 4) + ")";
        return result;
    }

    private void updateResultWithNewRecord(String recordKey, String recordValue) {
        if (!(recordValue == null || recordValue.trim().length() == 0)) {
            result += recordKey + " = '" + recordValue + "' and ";
        }
    }

}
