package com.lsc.entities.ai;

import net.minecraft.entity.ai.EntityAIBase;

/*
 * This may end up either extending a basic mob AI, or 
 * holding references to one or more of those.  Will 
 * move the knight when not in combat and may be called 
 * from combat AIs when an actual attack isn't being 
 * performed.
 */

public class AICorruptedKnightMove extends EntityAIBase {

	@Override
	public boolean shouldExecute() {
		// TODO Auto-generated method stub
		return false;
	}

}
