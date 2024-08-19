package com.library_management.excel;

import com.library_management.entity.identity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class UserExcelImport {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERS = {"User ID", "Username", "First Name", "Last Name", "Dob"};
    static String SHEET = "Users";


    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }
    public static List<User> excelToDatabase(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<User> userList = new ArrayList<User>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                User user = new User();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            user.setId(currentCell.getStringCellValue());
                            break;
                        case 1:
                            user.setUsername(currentCell.getStringCellValue());
                            break;
                        case 2:
                            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
                            String password = encoder.encode(currentCell.getStringCellValue());
                            user.setPassword(password);
                            break;
                        case 3:
                            user.setFirstName(currentCell.getStringCellValue());
                            break;
                        case 4:
                            user.setLastName(currentCell.getStringCellValue());
                            break;
                        case 5:
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate dateTime = LocalDate.parse(currentCell.getStringCellValue(), formatter);
                            user.setDob(dateTime);
                            break;
                        default:
                            break;
                    }

                    cellIdx++;
                }
                userList.add(user);
            }
            workbook.close();
            return userList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
