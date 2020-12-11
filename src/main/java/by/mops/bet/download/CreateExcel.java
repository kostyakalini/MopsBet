package by.mops.bet.download;

import by.mops.bet.model.Bet;
import by.mops.bet.model.Event;
import by.mops.bet.model.User;
import by.mops.bet.model.UserBet;
import by.mops.bet.security.MyUserDetails;
import by.mops.bet.services.*;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CreateExcel {

    @Autowired
    private static UserServiceImpl userService;

    @Autowired
    private static EventService eventService;

    @Autowired
    private static BetService betService;

    @Autowired
    private static TypeOfBetService typeOfBetService;

    @Autowired
    private static UserBetService userBetService;


    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    public static void ctreateExcelFile(MyUserDetails user, Map<Long, User> users, Map<Long, Event> events, Map<Long, Bet> bets, Map<Long, String> mapTypes, List<UserBet> userBets) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Employees sheet");


        int rownum = 0;
        Cell cell;
        Row row;
        //
        HSSFCellStyle style = createStyleForTitle(workbook);

        row = sheet.createRow(rownum);

        // EmpNo
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Username");
        cell.setCellStyle(style);
        // EmpName
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Event Id");
        cell.setCellStyle(style);
        // Salary
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Team1");
        cell.setCellStyle(style);
        // Grade
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Team2");
        cell.setCellStyle(style);
        // Bonus
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Bet details");
        cell.setCellStyle(style);

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Value of bet");
        cell.setCellStyle(style);

        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Status");
        cell.setCellStyle(style);

        // Data
        for (UserBet userBet : userBets) {
            rownum++;
            row = sheet.createRow(rownum);

            // EmpNo (A)
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(users.get(userBet.getUser_id()).getUsername());
            // EmpName (B)
            cell = row.createCell(1, CellType.NUMERIC);
            cell.setCellValue(events.get(bets.get(userBet.getBet_id()).getEvent_id()).getId());
            // Salary (C)
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(events.get(bets.get(userBet.getBet_id()).getEvent_id()).getTeam1());
            // Grade (D)
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(events.get(bets.get(userBet.getBet_id()).getEvent_id()).getTeam2());
            // Bonus (E)
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(mapTypes.get(bets.get(userBet.getBet_id()).getType_of_bet_id()) +' ' + bets.get(userBet.getBet_id()).getValue());

            cell = row.createCell(5, CellType.NUMERIC);
            cell.setCellValue(userBet.getValue());

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(userBet.getStatus());
        }
        File file = new File("C:\\Users\\User\\IdeaProjects\\MopsBet\\src\\main\\resources\\static\\TempFiles\\file.xls");
        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        System.out.println("Created file: " + file.getAbsolutePath());

    }

}
