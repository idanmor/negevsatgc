/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Factories;

/**
 *
 * @author Max
 */
public class FactoryManager {
    MenuFactory menuFactory;
    InternalScreenFactory statisticsScreenFactory;
  
      
    public FactoryManager(){
        menuFactory =  new DefaultMenuFactoryImpl();      
    }
    
    public FactoryManager(InternalScreenFactory statisticsScreenFactory){
      this.statisticsScreenFactory = statisticsScreenFactory;
   }
 
    public MenuFactory getMenuFactory(){
        return menuFactory;
    }
     public InternalScreenFactory getStatisticsScreenFactory(){
        return statisticsScreenFactory;
    }
     
    
}
