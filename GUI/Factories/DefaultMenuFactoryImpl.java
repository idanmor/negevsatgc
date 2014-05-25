/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Factories;

import MenuItems.MenuItemCommand;
import MenuItems.MenuItemExit;
import MenuItems.MenuItemSatteliteStatus;
import MenuItems.MenuItemSchedule;
import MenuItems.MenuItemStatistics;
import MenuItems.MenuItemStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.geometry.Orientation;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;

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
        for (MenuItem childMenu : menuItems) {
            menuEffect.getItems().add(childMenu);
            }
    return menuEffect;
    }

    @Override
    public Menu createViewMenu() {
         // --- Menu View
        Menu menuView = new Menu("View");
        List<MenuItemStrategy> listOfMenuItems = new ArrayList<>();
        listOfMenuItems.add(new MenuItemCommand());
        listOfMenuItems.add(new MenuItemStatistics());
        listOfMenuItems.add(new MenuItemSatteliteStatus());
        Menu menuSceens = this.createExpandMenu("Screens", listOfMenuItems);
        List<MenuItemStrategy> listOfTablesItems = new ArrayList<>();
        //TODO
        Menu tableScreens = this.createExpandMenu("View Tables", listOfTablesItems);
        menuView.getItems().addAll(menuSceens,tableScreens, new MenuItemSchedule());
        return menuView;
    }

    @Override
    public Menu createFileMenu() {
     Menu menuFile = this.createMenu("File");
     menuFile.getItems().add(new MenuItemExit());
     return menuFile;
    }

	@Override
	public Menu createToolsMenu() {
		Menu tools = this.createMenu("Tools");
		return tools;
	}
    
	public Separator getSeparator(){
		return new Separator(Orientation.VERTICAL);
	}
    
    
}
