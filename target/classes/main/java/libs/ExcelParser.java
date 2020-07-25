package libs;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelParser {
    public static String parse(String fileName) {
        String result = "";
        InputStream inputStream = null;
        XSSFWorkbook myExcelBook = null;
        Row firstRow = null;
        Boolean firstRound = true;

        try {
            inputStream = new FileInputStream(fileName);
            myExcelBook = new XSSFWorkbook(inputStream);
            Sheet mySheet = myExcelBook.getSheet("DB Format");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Sheet mySheet = myExcelBook.getSheet("DB Format");
        Iterator<Row> iteratorRow = mySheet.iterator();

        while (iteratorRow.hasNext()) {
            Row row = iteratorRow.next();
            if (firstRound) {
                firstRow = row;
                firstRound = false;
                row = iteratorRow.next();
            }
            Iterator<Cell> cells = row.iterator();
            Iterator<Cell> titleCell = firstRow.iterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                Cell cell1 = titleCell.next();
                int cellType = cell.getCellType();
                switch (cellType) {
                    case Cell.CELL_TYPE_STRING:
                        result += cell1.getStringCellValue()+" = '" + cell.getStringCellValue() + "' ";
                        if (cells.hasNext()) {
                            result += "and ";
                        }
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        result += cell1.getStringCellValue()+" = " + cell.getNumericCellValue() + " ";
                        if (cells.hasNext()) {
                            result += "and ";
                        }
                        break;
                    default:
                        result += "|";
                        break;
                }
            }
            result += "\n";
        }
        return result;
    }
}
