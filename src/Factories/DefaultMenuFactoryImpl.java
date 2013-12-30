/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Factories;

import MenuItems.MenuItemCommand;
import MenuItems.MenuItemExit;
import MenuItems.MenuItemSchedule;
import MenuItems.MenuItemStatistics;
import MenuItems.MenuItemStrategy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 *
 * @author Max
 */
public class DefaultMenuFactoryImpl implements MenuFactory{

    @Override
    public Menu createMenu(String name) {
        return new Menu(name);
      }

    @Override
    public Menu createExpandMenu(String name, Collection<MenuItemStrategy> menuItems) {
        Menu menuEffect = new Menu(name);
       // final ToggleGroup groupEffect = new ToggleGroup();
        for (MenuItem childMenu : menuItems) {
            menuEffect.getItems().add(childMenu);
            }
    return menuEffect;
    }

    @Override
    public Menu createViewMenu() {
         // --- Menu View
        Menu menuView = new Menu("View");
        List<MenuItemStrategy> listOfMenuItems = new ArrayList();
        listOfMenuItems.add(new MenuItemCommand());
        listOfMenuItems.add(new MenuItemStatistics());
        Menu menuSceens = this.createExpandMenu("Screens", listOfMenuItems);
        menuView.getItems().addAll(menuSceens, new MenuItemSchedule());
        return menuView;
    }

    @Override
    public Menu createFileMenu() {
     Menu menuFile = this.createMenu("File");
     menuFile.getItems().add(new MenuItemExit());
     return menuFile;
    }
    
    
    
}
