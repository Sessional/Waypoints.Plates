/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package waypoints.plates;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import waypoints.Waypoint;
import waypoints.WaypointPlugin;

/**
 *
 * @author Andrew
 */
class PlateListener implements Listener {

    WpsPlatesPlugin plugin;

    public PlateListener(WpsPlatesPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnPlateStep(PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL) {
            return;
        }
        if (event.getClickedBlock().getType() == Material.WOOD_PLATE || event.getClickedBlock().getType() == Material.STONE_PLATE) {
            Player p = event.getPlayer();
            Location plateLocation = event.getClickedBlock().getLocation();
            int x = plateLocation.getBlockX();
            int y = plateLocation.getBlockY();
            int z = plateLocation.getBlockZ();
            World w = plateLocation.getWorld();
            Location infront = new Location(w, x + 1, y, z);
            Location behind = new Location(w, x - 1, y, z);
            Location right = new Location(w, x, y, z + 1);
            Location left = new Location(w, x, y, z - 1);
            Location above = new Location(w, x, y + 1, z);
            Location below = new Location(w, x, y - 2, z);

            if (infront.getBlock().getType() == Material.WALL_SIGN || infront.getBlock().getType() == Material.SIGN_POST) {
                Sign s = (Sign) infront.getBlock().getState();
                handleWarp(p, s);
                event.setCancelled(true);
            } else if (behind.getBlock().getType() == Material.WALL_SIGN || behind.getBlock().getType() == Material.SIGN_POST) {
                Sign s = (Sign) behind.getBlock().getState();
                handleWarp(p, s);
                event.setCancelled(true);
            } else if (right.getBlock().getType() == Material.WALL_SIGN || right.getBlock().getType() == Material.SIGN_POST) {
                Sign s = (Sign) right.getBlock().getState();
                handleWarp(p, s);
                event.setCancelled(true);
            } else if (left.getBlock().getType() == Material.WALL_SIGN || left.getBlock().getType() == Material.SIGN_POST) {
                Sign s = (Sign) left.getBlock().getState();
                handleWarp(p, s);
                event.setCancelled(true);
            } else if (above.getBlock().getType() == Material.WALL_SIGN || above.getBlock().getType() == Material.SIGN_POST) {
                Sign s = (Sign) above.getBlock().getState();
                handleWarp(p, s);
                event.setCancelled(true);
            } else if (below.getBlock().getType() == Material.WALL_SIGN || below.getBlock().getType() == Material.SIGN_POST) {
                Sign s = (Sign) below.getBlock().getState();
                handleWarp(p, s);
                event.setCancelled(true);
            }
        }
    }

    public void handleWarp(Player p, Sign s) {

        for (int i = 0; i < s.getLines().length - 1; i++) {
            if (s.getLine(i).equals("Waypoint:") || s.getLine(i).equals("§aWaypoint:")) {
                String wp = s.getLine(i + 1);
                WaypointPlugin plug = (WaypointPlugin) plugin.getServer().getPluginManager().getPlugin("Waypoints");
                if (plugin.isBukkitPermissions()) {
                    if (p.hasPermission("waypoints.go") || p.hasPermission("waypoints.plates")) {
                        if (!s.getLine(i).equals("§aWaypoint:")) {
                            s.setLine(i, "§aWaypoint:");
                        }
                        p.teleport(plug.getWaypoint(wp).getLocation());
                    } else {
                        p.sendMessage("You do not have permission to pressure plate waypoint.");
                    }
                } else {
                    if (!s.getLine(i).equals("§aWaypoint:")) {
                        s.setLine(i, "§aWaypoint:");
                        s.update();
                    }
                    p.teleport(plug.getWaypoint(wp).getLocation());
                }
            }
        }
    }
}
