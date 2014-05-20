/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Factories;

import MenuItems.MenuItemStrategy;
import java.util.Collection;
import javafx.scene.control.Menu;

/**
 *
 * @author Max
 */
public interface MenuFactory{
    
    public Menu createMenu(String name);
    public Menu createExpandMenu(String name, Collection <MenuItemStrategy> menuItems);
    public Menu createViewMenu();
    public Menu createFileMenu();
	public Menu createToolsMenu();
}
