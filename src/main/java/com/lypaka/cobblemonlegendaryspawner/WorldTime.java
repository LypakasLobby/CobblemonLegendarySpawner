package com.lypaka.cobblemonlegendaryspawner;

import com.google.common.collect.Lists;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.LongPredicate;

public enum WorldTime {

	DAWN(tick -> tick >= 22500 || tick <= 300),
	MORNING(tick -> tick <= 6000 || tick >= 22550),
	DAY(tick -> tick <= 12000),
	MIDDAY(tick -> tick >= 5500 && tick <= 6500),
	AFTERNOON(tick -> tick >= 6000 && tick <= 12000),
	DUSK(tick -> tick >= 12000 && tick <= 13800),
	NIGHT(tick -> tick >= 13450 && tick <= 22550),
	MIDNIGHT(tick -> tick >= 17500 && tick <= 18500),
	;

	private static final WorldTime[] ALL_TIMES = WorldTime.values();

	private final LongPredicate tickCondition;
	
	WorldTime(LongPredicate tickCondition) {
		this.tickCondition = tickCondition;
	}

	public LongPredicate getTickCondition() {
		return this.tickCondition;
	}
	
	public static List<WorldTime> getCurrent (World world) {

		List<WorldTime> current = Lists.newArrayList();
		long currentTime = world.getTime() % 24000;
		for (WorldTime time : ALL_TIMES) {

			if (time.tickCondition.test(currentTime)) {

				current.add(time);

			}

		}

		return current;

	}

}
