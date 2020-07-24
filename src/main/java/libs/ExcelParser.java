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

        try {
            inputStream = new FileInputStream(fileName);
            myExcelBook = new XSSFWorkbook(inputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Sheet mySheet = myExcelBook.getSheet("DB Format");
        Iterator<Row> iteratorRow = mySheet.iterator();

        while (iteratorRow.hasNext()) {
            Row row = iteratorRow.next();
            Iterator<Cell> cells = row.iterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                int cellType = cell.getCellType();
                switch (cellType) {
                    case Cell.CELL_TYPE_STRING:
                        result += "[" + cell.getStringCellValue() + "]";
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        result += "[" + cell.getNumericCellValue() + "]";
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
