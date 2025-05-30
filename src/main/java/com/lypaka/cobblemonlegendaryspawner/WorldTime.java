package com.lypaka.cobblemonlegendaryspawner;

import com.google.common.collect.Lists;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.LongPredicate;

public class WorldTime {
	
	public static List<String> getCurrentTimeValues (World world) {

		List<String> current = Lists.newArrayList();
		long currentTime = world.getTime() % 24000;
		if (currentTime >= 22500 || currentTime <= 300) {

			if (!current.contains("Dawn")) {

				current.add("Dawn");

			}

		}
		if (currentTime >=22550 || currentTime <= 6000) {

			if (!current.contains("Morning")) {

				current.add("Morning");

			}

		}
		if (currentTime <= 12000) {

			if (!current.contains("Day")) {

				current.add("Day");

			}

		}
		if (currentTime >= 1200 && currentTime <= 13800) {

			if (!current.contains("Dusk")) {

				current.add("Dusk");

			}

		}
		if (currentTime >= 13450 && currentTime <= 22550) {

			if (!current.contains("Night")) {

				current.add("Night");

			}

		}
		if (currentTime >= 17500 && currentTime <= 18500) {

			if (!current.contains("Midnight")) {

				current.add("Midnight");

			}

		}

		return current;

	}

}
