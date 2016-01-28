package me.Jonathon594.CrimsonIngot.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.DataTypes.CrimsonPlayer;
import me.Jonathon594.CrimsonIngot.Managers.TimeManager;
import me.Jonathon594.CrimsonIngot.Util.CrimsonIngotUtil;

public class TimeListener {
	public void run(final CrimsonIngot plugin, final TimeManager timeManager) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				// Bukkit.getServer().broadcastMessage("Current Time is
				// "+Bukkit.getWorld("world").getTime());
				if (Bukkit.getWorld("world").getTime() >= 199) return;
				timeManager.NewDay();
				for (final Player p : Bukkit.getOnlinePlayers()) {
					final CrimsonPlayer mp = plugin.getMythriaPlayerManager().getPlayerByUUID(p.getUniqueId());
					if (mp == null) return;
					CrimsonIngotUtil.sendMythriaMessage(p, timeManager.getCurrentDate().GetDateString());
				}
			}
		}, 0, 200);

	}
}
