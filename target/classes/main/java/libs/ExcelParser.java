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

    public String parse(String fileName) throws IOException {
        String result = "";
        InputStream inputStream = null;
        XSSFWorkbook myExcelBook = null;
        Row firstRow = null;
        Boolean firstRound = true;

        inputStream = new FileInputStream(fileName);
        myExcelBook = new XSSFWorkbook(inputStream);
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
            result += "EXISTS( select * from GamesPrizes where ";
            while (cells.hasNext()) {
                Cell cell = cells.next();
                Cell cell1 = titleCell.next();
                int cellType = cell.getCellType();
                switch (cellType) {
                    case Cell.CELL_TYPE_STRING:
                        result += cell1.getStringCellValue() + " = '" + cell.getStringCellValue() + "' ";
                        if (cells.hasNext()) {
                            result += "and ";
                        } else {
                            result += ")";
                        }
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        if (cell1.getStringCellValue().contains("GameID") || cell1.getStringCellValue().contains("NoOfCards")) {
                            int cutAfterDot = (int)cell.getNumericCellValue();
                            result += cell1.getStringCellValue()+" = " + cutAfterDot + " ";
                        } else {
                            result += cell1.getStringCellValue() + " = " + cell.getNumericCellValue() + " ";
                        }
                        if (cells.hasNext()) {
                            result += "and ";
                        } else {
                            result += ")";
                        }
                        break;
                    case Cell.CELL_TYPE_BLANK:
                        result += cell1.getStringCellValue()+" = " + "'' ";
                        if (cells.hasNext()) {
                            result += "and ";
                        } else {
                            result += ")";
                        }
                        break;
                    default:
                        break;
                }
            }
            if (iteratorRow.hasNext()) {
                result += "\n AND ";
            }
        }
        return result;
    }

}
