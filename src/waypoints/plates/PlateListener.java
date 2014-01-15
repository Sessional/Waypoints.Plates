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

/**
 *
 * @author Andrew
 */
class PlateListener implements Listener
{

    WpsPlatesPlugin plugin;

    public PlateListener(WpsPlatesPlugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnPlateStep(PlayerInteractEvent event)
    {
        if (event.getAction() != Action.PHYSICAL)
        {
            return;
        }
        if (event.getClickedBlock().getType() == Material.WOOD_PLATE || event.getClickedBlock().getType() == Material.STONE_PLATE)
        {
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

            if (infront.getBlock().getType() == Material.WALL_SIGN || infront.getBlock().getType() == Material.SIGN_POST)
            {
                Sign s = (Sign) infront.getBlock().getState();
                if (handleWarp(p, s))
                {
                    event.setCancelled(true);
                }
                return;
            }
            if (behind.getBlock().getType() == Material.WALL_SIGN || behind.getBlock().getType() == Material.SIGN_POST)
            {
                Sign s = (Sign) behind.getBlock().getState();
                if (handleWarp(p, s))
                {
                    event.setCancelled(true);
                }
                return;
            }
            if (right.getBlock().getType() == Material.WALL_SIGN || right.getBlock().getType() == Material.SIGN_POST)
            {
                Sign s = (Sign) right.getBlock().getState();
                if (handleWarp(p, s))
                {
                    event.setCancelled(true);
                }
                return;
            }
            if (left.getBlock().getType() == Material.WALL_SIGN || left.getBlock().getType() == Material.SIGN_POST)
            {
                Sign s = (Sign) left.getBlock().getState();
                if (handleWarp(p, s))
                {
                    event.setCancelled(true);
                }
                return;
            }
            if (above.getBlock().getType() == Material.WALL_SIGN || above.getBlock().getType() == Material.SIGN_POST)
            {
                Sign s = (Sign) above.getBlock().getState();
                if (handleWarp(p, s))
                {
                    event.setCancelled(true);
                }
                return;
            }
            if (below.getBlock().getType() == Material.WALL_SIGN || below.getBlock().getType() == Material.SIGN_POST)
            {
                Sign s = (Sign) below.getBlock().getState();
                if (handleWarp(p, s))
                {
                    event.setCancelled(true);
                }
                return;
            }
        }
    }

    public boolean handleWarp(Player p, Sign s)
    {

        for (int i = 0; i < s.getLines().length - 1; i++)
        {
            if (s.getLine(i).equals("Waypoint:") || s.getLine(i).equals("§aWaypoint:"))
            {
                String wp = s.getLine(i + 1);
                if (plugin.isBukkitPermissions())
                {
                    if (p.hasPermission("waypoints.go") || p.hasPermission("waypoints.plates"))
                    {
                        if (!s.getLine(i).equals("§aWaypoint:"))
                        {
                            s.setLine(i, "§aWaypoint:");
                            s.update();
                        }
                        if (plugin.getPlugin().getConfigManager().costingEnabled())
                        {
                            if (s.getLine(i + 2).equals(""))
                            {
                                plugin.getPlugin().getCommandHandler().doGo(p, wp);
                            } else
                            {
                                int cost = Integer.parseInt(s.getLine(i + 2));
                                if (p.getLevel() < cost)
                                {
                                    p.sendMessage("You do not have the required levels to do that.");
                                    return false;
                                }
                                p.setLevel(p.getLevel() - cost);
                                plugin.getPlugin().getCommandHandler().doGoNoCost(p, wp);
                            }
                        } else
                        {
                            p.teleport(plugin.getPlugin().getWaypoint(wp).getLocation());
                        }
                        return true;
                    } else
                    {
                        p.sendMessage("You do not have permission to pressure plate waypoint.");
                        return false;
                    }
                } else
                {
                    if (!s.getLine(i).equals("§aWaypoint:"))
                    {
                        s.setLine(i, "§aWaypoint:");
                        s.update();
                    }
                    if (plugin.getPlugin().getConfigManager().costingEnabled())
                    {
                        if (s.getLine(i + 2).equals(""))
                        {
                            plugin.getPlugin().getCommandHandler().doGo(p, wp);
                        } else
                        {
                            int cost = Integer.parseInt(s.getLine(i + 2));
                            if (p.getLevel() < cost)
                            {
                                p.sendMessage("You do not have the required levels to do that.");
                                return false;
                            }
                            p.setLevel(p.getLevel() - cost);
                            plugin.getPlugin().getCommandHandler().doGoNoCost(p, wp);
                        }
                    } else
                    {
                        p.teleport(plugin.getPlugin().getWaypoint(wp).getLocation());
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
