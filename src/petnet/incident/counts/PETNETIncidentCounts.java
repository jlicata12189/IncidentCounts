/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petnet.incident.counts;

import individuals.IndividualIncidents;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Joe Licata
 */
public class PETNETIncidentCounts {

    private static String CSVOutputFile = "output.csv";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (!new File(CSVOutputFile).exists()) {
            initCSVOutput();
        }
        testScan("MW 12-11-2017.xlsx");
    }

    public static void testScan(String filename) {
        try {
            FileInputStream file = new FileInputStream(new File(filename));
            Workbook wb = new XSSFWorkbook(file);
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            String[] input;
            int i;
            if (iterator.hasNext()) {
                iterator.next();
            }
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cells = currentRow.iterator();
                input = new String[14];
                i = 0;
                while (cells.hasNext()) {
                    Cell currentCell = cells.next();
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        input[i] = new DataFormatter().formatCellValue(currentCell);
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        input[i] = String.valueOf(new DataFormatter().formatCellValue(currentCell));
                    }
                    i++;
                }
                if (!input[13].equals("N/A")) {
                    IndividualIncidents incident = new IndividualIncidents();
                    incident.setAccount(input[0].substring(7));
                    incident.setDate(input[1]);
                    incident.setOrder(input[2]);
                    incident.setStop(input[6]);
                    SimpleDateFormat minutes = new SimpleDateFormat("MM/dd/YY HH:mm");
                    incident.setPuMinLate(minutes.parse(input[5]), minutes.parse(input[4]));
                    incident.setDelMinLate(minutes.parse(input[9]), minutes.parse(input[8]));
                    incident.setResponsibleParty(input[11]);
                    incident.setIncident(Integer.parseInt(input[13]));
                    writeToCSVFile(incident);
                }

            }
        } catch (FileNotFoundException ex) {
        } catch (IOException | ParseException ex) {
        }
    }

    public static void writeToCSVFile(IndividualIncidents incident) {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(CSVOutputFile,true)));
            StringBuilder sb = new StringBuilder();
            sb.append(incident.getAccount());
            sb.append(",");
            sb.append(incident.getDate());
            sb.append(",");
            sb.append(incident.getOrder());
            sb.append(",");
            sb.append(incident.getStop());
            sb.append(",");
            sb.append(incident.getPuMinLate());
            sb.append(",");
            sb.append(incident.getDelMinLate());
            sb.append(",");
            sb.append(incident.getIncident().getCode());
            sb.append(",");
            sb.append(incident.getIncident().getDescription());
            sb.append(",");
            sb.append(incident.getResponsibleParty());
            sb.append(",");
            if(incident.getIncident().getControllable()){
                sb.append("Controllable");
            }else sb.append("Uncontrollable");
            sb.append("\n");
            pw.write(sb.toString());
            pw.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
    }

    public static void initCSVOutput() {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(CSVOutputFile,true)));
            StringBuilder sb = new StringBuilder();
            sb.append("Account,Date,Order Number,Stop Name,Minutes Late (Pickup),Minutes Late (Delivery),");
            sb.append("Incident Code,Description,Responsible Party,Conrollable\n");
            pw.write(sb.toString());
            pw.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
    }
}
