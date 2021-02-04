/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Task2;

/**
 *
 * @author ritch
 */
public class Guess_Bean implements java.io.Serializable {
    
    private int Usernumber;
    private int GuessTimes=0;
    private int Sercret;
//    private int Compare;
    
    public Guess_Bean(){
        
    }
    
    public void setUsernumber(int Usernumber){
        this.Usernumber=Usernumber;
        this.GuessTimes++;
    }
    
    public void refresh(){
        this.Sercret= 1+(int)(Math.random()*100);
        this.GuessTimes = 0;
    }
    
    public int getUsernumber(){
        return this.Usernumber;
    }
    
    public int getSercert(){
        return this.Sercret;
    }
    
    public int getGuesstimes(){
        return this.GuessTimes;
    }
    
//    public int getCompare(){
//        return this.Compare;
//    }
    
//    public void Guess(Guess_Bean gb){
//        if(gb.getUsernumber()<gb.getSercert())
//            gb.Compare=-1;
//        if(gb.getUsernumber()==gb.getSercert())
//            gb.Compare=0;
//        if(gb.getUsernumber()>gb.getSercert())
//            gb.Compare=1;
//    }
    
}