package libs;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.io.IOException;

public class CsvParser {

    public String parse(String filename) throws IOException {
        CSVReader csvReader = null;
        String result = "EXISTS(select * from Games where ";
        String[] nextRecord;
        boolean isFirstNumber = true;
        boolean isEmptyNumber = true;

        FileReader fileReader = new FileReader(filename);
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        csvReader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();

        while ((nextRecord = csvReader.readNext()) != null) {
            if (isFirstNumber) {
                nextRecord = csvReader.readNext();
                isFirstNumber = false;
            }
            if (!isEmptyNumber) {
                result += "and ";
            }
            if (nextRecord[1] == null || nextRecord[1].trim().length() == 0) {
                isEmptyNumber = true;
                break;
            } else {
                result += nextRecord[0] + " = " + nextRecord[1] + " ";
                isEmptyNumber = false;
            }

        }
        result = result.substring(0, result.length() - 4);
        result += ")";
        return result;
    }
}
