/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package individuals;

import java.util.Date;
import petnet.incident.counts.Incident;

/**
 *
 * @author Joe Licata
 */
public class IndividualIncidents {

    private Incident incident;
    private String order;
    private String date;
    private String account;
    private String stop;
    private int delMinLate;
    private int puMinLate;
    private String responsibleParty;

    public Incident getIncident() {
        return incident;
    }

    public void setIncident(int code) {
        incident = new Incident();
        incident.setCode(code);
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public int getDelMinLate() {
        return delMinLate;
    }

    public void setDelMinLate(Date actual, Date scheduled) {
        int actualHours = actual.getHours();
        int actualMinutes = actual.getMinutes();
        int scheduledHours = scheduled.getHours();
        int scheduledMinutes = scheduled.getMinutes();

        actualMinutes = (actualHours * 60) + actualMinutes;
        scheduledMinutes = (scheduledHours * 60) + scheduledMinutes;

        delMinLate = actualMinutes - scheduledMinutes;
    }

    public int getPuMinLate() {
        return puMinLate;
    }

    public void setPuMinLate(Date actual, Date scheduled) {
        int actualHours = actual.getHours();
        int actualMinutes = actual.getMinutes();
        int scheduledHours = scheduled.getHours();
        int scheduledMinutes = scheduled.getMinutes();

        actualMinutes = (actualHours * 60) + actualMinutes;
        scheduledMinutes = (scheduledHours * 60) + scheduledMinutes;

        puMinLate = actualMinutes - scheduledMinutes;
    }
    public void setResponsibleParty(String responsibleParty){
        this.responsibleParty=responsibleParty;
    }
    public String getResponsibleParty(){
        return responsibleParty;
    }

}
