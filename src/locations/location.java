/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locations;

/**
 *
 * @author Joe Licata
 */
public class location {
    private String name;
    private int[] incidents;
    
    public void setName(String name){
        this.name=name;
    }
    
    public String getName(){
        return name;
    }
    
    public void addIncident(int code){
        if(code<=12&&code>0){
            incidents[code-1]++;
        }else if(code>30&&code<=39){
            incidents[code-18]++;
        }else System.out.println("Invalid code: "+code);
    }
    
    public int[] getIncidents(){
        return incidents;
    }
    
    public void init(String name, int[] incident){
        this.name=name;
        this.incidents=new int[21];
        System.arraycopy(incident, 0, incidents, 0, 21);
    }
    
}
