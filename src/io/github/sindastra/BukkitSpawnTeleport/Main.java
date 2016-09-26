/**
 * BukkitSpawnTeleport
 * Copyright (C) 2016 Sindastra <https://github.com/sindastra>
 * All rights reserved.
 *
 * This and the above copyright notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * This software is not affiliated with Bukkit.
 * 
 * @author Sindastra
 * @copyright Copyright (C) 2016 Sindastra. All rights reserved.
 */

package io.github.sindastra.BukkitSpawnTeleport;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	private boolean useLightningEffect;
	private boolean broadcastTeleport;
	private String broadcastMessage;
	
	@Override
	public void onEnable()
	{
		saveDefaultConfig();
		
		useLightningEffect = getConfig().getBoolean("use-lightning-effect");
		broadcastTeleport = getConfig().getBoolean("broadcast-teleport");
		broadcastMessage = getConfig().getString("broadcast-message");
		
		getLogger().info("Enabled!");
	}
	
	@Override
	public void onDisable()
	{
		getLogger().info("Disabled");
	}
	
	@Override
	public boolean onCommand( CommandSender sender , Command cmd , String label , String[] args )
	{
		if( cmd.getName().equalsIgnoreCase("spawn") )
		{
			if( !(sender instanceof Player) )
			{
				sender.sendMessage("This command must be run by a player.");
			}		
			else
			{
				Player player = (Player) sender;
				if(player.getWorld().getEnvironment().equals(World.Environment.NORMAL))
				{
					Location spawnLocation = player.getWorld().getSpawnLocation();
					
					if(useLightningEffect)
						player.getWorld().strikeLightningEffect(player.getLocation());
					
					player.teleport(spawnLocation);
					
					if(useLightningEffect)
						player.getWorld().strikeLightningEffect(spawnLocation);
					
					if(broadcastTeleport)
						getServer().broadcastMessage(String.format(broadcastMessage, player.getName()));	
					
				}
				else
					player.sendMessage(ChatColor.RED + "Only in the overworld, dear!");
			}
			return true;
		}		
		return false;
	}
}