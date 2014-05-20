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
public interface StatisticDataItemInterface {
   public String getDate();
   public String getCategory();
   public String getSeverity();
   public String[][] getData();
}
