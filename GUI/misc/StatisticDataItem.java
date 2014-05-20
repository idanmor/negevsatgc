/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package misc;

/**
 *
 * @author Max
 */
public class StatisticDataItem implements StatisticDataItemInterface{
    private String date,category,severity; 
    private String[][] data;

    
    
    public StatisticDataItem(String date, String category, String severity, String[][] data){
        this.date = date;
        this.category = category;
        this.severity = severity;
        this.data = data;
    }
    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getCategory() {
       return category;
    }

    @Override
    public String getSeverity() {
    return severity;
    }

    @Override
    public String[][] getData() {
        return data;
    }
}
