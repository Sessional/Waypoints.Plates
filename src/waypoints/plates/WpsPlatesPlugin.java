/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package waypoints.plates;

import java.io.File;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import waypoints.WaypointPlugin;

/**
 *
 * @author Andrew
 */
public class WpsPlatesPlugin extends JavaPlugin
{
    private boolean bukkitPermissions;
    
    private WaypointPlugin plugin;
    
    @Override
    public void onEnable()
    {
        plugin = (WaypointPlugin) getServer().getPluginManager().getPlugin("Waypoints");
        
        if (plugin.getConfigManager().permissionsEnabled())
        {
            bukkitPermissions = true;
        } else
        {
            bukkitPermissions = false;
        }
        
        getServer().getPluginManager().registerEvents(new PlateListener(this), this);
    }
    
    public WaypointPlugin getPlugin()
    {
        return plugin;
    }
    
    public boolean isBukkitPermissions()
    {
        return bukkitPermissions;
    }
    
    @Override
    public void onDisable()
    {
        
    }
}
