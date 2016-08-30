
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Josu
 */
public class Card {
    
    private String hardness,sexual_dev,text;
    private ArrayList<String> options;
    
    public Card (String pHardness, String pSexual_dev, ArrayList<String> pOptions,String pText) {
        hardness = pHardness;
        sexual_dev = pSexual_dev;
        options = pOptions;
        text = pText;
    }
    
    private Iterator<String> getIterator(){
        return options.iterator();
    }
    
    public String getHardness(){return hardness;}
    
    public String getSex_dev(){return sexual_dev;}
    
    public ArrayList<String> getOptions(){return options;}

    public String getText(){return text;}
    
    public void setHardness(String pHardness){
        hardness = pHardness;
    }
    
    public void setSexual_dev(String pSexual_dev){
        sexual_dev = pSexual_dev;
    }
    
    public void addOptionsItem(String pOption){
        options.add(pOption);
        showOptions();
    }
    
    public void removeOptionsItem(String pOption){
        String s1;
        Iterator<String> itr = getIterator();
        boolean finded = false;
        while (itr.hasNext() && !finded){
            s1 = itr.next();
            if (s1.equalsIgnoreCase(pOption)){
                options.remove(s1);
                finded = true;
            }
        }
    }
    
    public void showOptions(){
        System.out.println("Hardness: "+hardness);
        System.out.println("Sex_dev: "+sexual_dev);
        System.out.println("");
        Iterator<String> itr = getIterator();
        while (itr.hasNext()){
            String s1 = itr.next();
            System.out.println(s1);
        }
        System.out.println("\n"+text);
        System.out.println("-------------------------------");
    }
    
    
    
}
