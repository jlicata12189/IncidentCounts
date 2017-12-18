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
import java.util.Iterator;
import locations.location;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Joe Licata
 */
public class PETNETIncidentCounts {

    private static String CSVOutputFile = "output.csv";
    private static String CSVTotalCounts = "totalcounts.csv";
    private static String XLSTotalCounts = "totalCounts.xlsx";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (!new File(CSVOutputFile).exists()) {
            initCSVOutput();
        }
        location[] locations=new location[20];
        locations=initializeLocations();
        File folder = new File("C:/Users/Joe Licata/Documents/PETNET/Incident Reports Starting 12-1-2017/Emailed But Not Scanned");
        File[] listOfFiles =folder.listFiles();
        for(File file:listOfFiles){
            if(file.isFile()){
                scanNewIncidentReport(file.getAbsolutePath());
            }
        }
        
                
    }

    /**
     * Scans in incident reports from Excel spreadsheets
     * 
     * @param filename
     */
    public static void scanNewIncidentReport(String filename) {
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

    /**
     * Appends individual incidents to a CSV file.
     * 
     * @param incident
     */
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
            }else if(incident.getResponsibleParty().equals("PETNET")){
                sb.append("PETNET Exception");
            }else if(incident.getResponsibleParty().equals("Customer")){
                sb.append("Customer Exception");
            }else if (incident.getResponsibleParty().equals("Plane")){
                sb.append("Plane Exception");
            }else sb.append("Uncontrollable");
            sb.append("\n");
            pw.write(sb.toString());
            pw.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
    }
    
    /**
     * Initialize the CSV file containing each individual incident.
     */
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
    
    public static location[] initializeLocations(){
        location[] location=new location[20];
        try {
            FileInputStream file = new FileInputStream(new File(XLSTotalCounts));
            Workbook wb = new XSSFWorkbook(file);
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            String[][] input = new String[23][20];
            int i=0,j;
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cells = currentRow.iterator();
                j = 0;
                while (cells.hasNext()) {
                    Cell currentCell = cells.next();
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        input[i][j] = new DataFormatter().formatCellValue(currentCell);
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        input[i][j] = String.valueOf(new DataFormatter().formatCellValue(currentCell));
                    }
                    j++;
                }
                i++;


            }
            
            for(i=0;i<20;i++){
                location[i]=new location();
            }
            for(i=1;i<20;i++){
                String locationName = input[0][i];
                int[] incidents = new int[21];
                for (j=1;j<22;j++){
                    incidents[j-1]=Integer.parseInt(input[j][i]);
                }
                location[i-1].init(locationName, incidents);
            }
            
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
        return location;
    }
}
