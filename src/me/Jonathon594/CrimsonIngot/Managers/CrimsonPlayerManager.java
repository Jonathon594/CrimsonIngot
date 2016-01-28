package me.Jonathon594.CrimsonIngot.Managers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.entity.Player;

import me.Jonathon594.CrimsonIngot.CrimsonIngot;
import me.Jonathon594.CrimsonIngot.DataTypes.CrimsonPlayer;

public class CrimsonPlayerManager {
	public List<CrimsonPlayer>	mythriaPlayerList	= new ArrayList<CrimsonPlayer>();
	public Logger				log;

	public CrimsonPlayerManager(final Logger log) {
		this.log = log;
	}

	public CrimsonPlayer addPlayer(final CrimsonIngot plugin, final UUID pid, final Player player) {
		final CrimsonPlayer mp = new CrimsonPlayer(plugin, pid, player);
		mythriaPlayerList.add(mp);

		return mp;
	}

	public CrimsonPlayer getPlayerByUUID(final UUID uuid) {
		if (uuid != null) {
			for (final CrimsonPlayer mp : mythriaPlayerList)
				if (mp != null) {
					if (mp.getPlayer() != null)
						if (mp.getPlayer().getUniqueId().toString().equalsIgnoreCase(uuid.toString())) return mp;
				} else
					log.warning("Could not find MythriaPlayer - " + uuid);
		} else
			log.warning("UUID is Null in getPlayerByName(UUID);");
		return null;
	}

	public void removeAllEffects() {
		for (final CrimsonPlayer mp : mythriaPlayerList)
			mp.removeAllEffects();
	}

	public void removePlayer(final CrimsonPlayer mp) {
		mythriaPlayerList.remove(mp);
	}

	public void SavePlayers(final CrimsonIngot plugin) {
		for (final CrimsonPlayer mp : mythriaPlayerList)
			mp.SaveData();
	}
}
