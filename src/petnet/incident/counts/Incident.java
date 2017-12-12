/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petnet.incident.counts;

/**
 *
 * @author Joe Licata
 */
public class Incident {

    private int code;
    private String description;
    private boolean controllable;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
        setDescription(code);
    }

    public String getDescription() {
        return description;
    }
    
    public boolean getControllable(){
        return controllable;
    }

    private void setDescription(int code) {
        switch (code) {
            case 1:
                this.description = "Doses released late.";
                this.controllable = false;
                break;
            case 2:
                this.description = "Traffic.";
                this.controllable = false;
                break;
            case 3:
                this.description = "Weather.";
                this.controllable = false;
                break;
            case 4:
                this.description = "Customer Not On Site/Not Ready.";
                this.controllable = false;
                break;
            case 5:
                this.description = "Picked Up From Alternate Site.";
                this.controllable = false;
                break;
            case 6:
                this.description = "Timeframe Too Tight.";
                this.controllable = false;
                break;
            case 7:
                this.description = "Airplane Late/Canceled";
                this.controllable = false;
                break;
            case 8:
                this.description = "Waiting On Security/Escort.";
                this.controllable = false;
                break;
            case 9:
                this.description = "Incorrect Route Information Sent To Courier.";
                this.controllable = false;
                break;
            case 10:
                this.description = "Driver Involved In Accident.";
                this.controllable = false;
                break;
            case 11:
                this.description = "Last-Minute Add-On Delivery.";
                this.controllable = false;
                break;
            case 12:
                this.description = "Other.";
                this.controllable = false;
                break;
            case 30:
                this.description = "Driver was late to pickup.";
                this.controllable = true;
                break;
            case 31:
                this.description = "Misdelivery.";
                this.controllable = true;
                break;
            case 32:
                this.description = "Driver lost.";
                this.controllable = true;
                break;
            case 33:
                this.description = "Driver does not have keys.";
                this.controllable = true;
                break;
            case 34:
                this.description = "Driver did not arrive for route.";
                this.controllable = true;
                break;
            case 35:
                this.description = "Vehicle breakdown.";
                this.controllable = true;
                break;
            case 36:
                this.description = "Dispatch communication issue.";
                this.controllable = true;
                break;
            case 37:
                this.description = "DOT Compliance issue.";
                this.controllable = true;
                break;
            case 38:
                this.description = "Driver delivered late.";
                this.controllable = true;
                break;
            case 39:
                this.description = "Other.";
                this.controllable = true;
                break;
            default:
                this.description = "Error with OTP code.";
                this.controllable = true;
                break;

        }
    }

}
