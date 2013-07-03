/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package waypoints.plates;

import java.io.File;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Andrew
 */
public class WpsPlatesPlugin extends JavaPlugin
{
    
    private static final Logger log = Logger.getLogger("Minecraft");
    private String version = "0.1.1";
    private File configFile;
    private boolean bukkitPermissions;
    
    @Override
    public void onEnable()
    {
        configFile = new File("./plugins/Waypoints.Plates/config.yml");
        if (!configFile.exists())
        {
            this.saveDefaultConfig();
        }
        
        if (this.getConfig().getBoolean("bukkitPermissions") == true)
        {
            bukkitPermissions = true;
        } else
        {
            bukkitPermissions = false;
        }
        
        getServer().getPluginManager().registerEvents(new PlateListener(this), this);
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
