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
    protected String result = "";

    public String parse(String fileName) throws IOException {

        InputStream inputStream;
        XSSFWorkbook myExcelBook;
        Row firstRow;

        inputStream = new FileInputStream(fileName);
        myExcelBook = new XSSFWorkbook(inputStream);
        Sheet mySheet = myExcelBook.getSheet("DB Format");
        Iterator<Row> iteratorRow = mySheet.iterator();
        if ((firstRow = iteratorRow.next()) == null) throw new IOException();

        while (iteratorRow.hasNext()) {
            Row row = iteratorRow.next();
            Iterator<Cell> cells = row.iterator();
            Iterator<Cell> titleCells = firstRow.iterator();
            result += "EXISTS( select * from GamesPrizes where ";

            while (titleCells.hasNext()) {
                Cell titleCell = titleCells.next();
                Cell cell;
                if (cells.hasNext()) cell = cells.next();
                else {
                    handleNoCellsCase(titleCell.getStringCellValue(), titleCells.hasNext());
                    continue;
                }
                if (isExeptionColumn(titleCell.getStringCellValue())) {
                    if (!titleCells.hasNext()) updateResultIfDropCell();
                    continue;
                }
                int cellType = cell.getCellType();
                switch (cellType) {
                    case Cell.CELL_TYPE_STRING:
                        updateResultWithStringCell(titleCell.getStringCellValue(), cell.getStringCellValue());
                        updateResultIfLastCell(titleCells.hasNext());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        updateResultWithNumericCell(titleCell.getStringCellValue(), cell.getNumericCellValue());
                        updateResultIfLastCell(titleCells.hasNext());
                        break;
                    case Cell.CELL_TYPE_BLANK:
                        updateResultWithBlankCell(titleCell.getStringCellValue());
                        updateResultIfLastCell(titleCells.hasNext());
                        break;
                    default:
                        break;
                }
            }
            if (iteratorRow.hasNext()) result += "\n AND ";
        }
        return result;
    }

    private void updateResultWithStringCell(String cellKey, String cellValue) {
        if (cellValue.trim().length() == 0) updateResultWithBlankCell(cellKey);
        else result += cellKey + " = '" + cellValue + "' ";
    }

    private void updateResultWithNumericCell(String cellKey, double cellValue) {
        if (cellKey.contains("ID") || cellKey.contains("NoOfCards"))
            result += cellKey + " = " + (int) cellValue + " ";
        else result += cellKey + " = " + cellValue + " ";
    }

    private void updateResultWithBlankCell(String cellKey) {
        result += cellKey + " = " + "'' ";
    }

    private void updateResultIfLastCell(boolean hasNext) {
        if (hasNext) result += "and ";
        else result += ")";
    }

    private void updateResultIfDropCell() {
        result = result.substring(0, result.length() - 4) + ")";
    }

    private boolean isExeptionColumn(String workCell) {
        return workCell.contains("Probability") || workCell.contains("RTPPercentage");
    }

    private void handleNoCellsCase(String workCell, boolean hasNext) {
        if (hasNext) handleNoCellIfHasMore(workCell);
        else handleNoCellIfNoMore(workCell);
    }

    private void handleNoCellIfHasMore(String workCell) {
        if (isExeptionColumn(workCell)) return;
        else {
            updateResultWithBlankCell(workCell);
            updateResultIfLastCell(true);
        }
    }
    private void handleNoCellIfNoMore(String workCell) {
        if (isExeptionColumn(workCell)) updateResultIfDropCell();
        else {
            updateResultWithBlankCell(workCell);
            updateResultIfLastCell(false);
        }
    }

}
