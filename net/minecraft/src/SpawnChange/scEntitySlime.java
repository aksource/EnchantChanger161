package net.minecraft.src.SpawnChange;

import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.src.mod_SpawnChange;
import net.minecraft.world.World;

public class scEntitySlime extends EntitySlime
{
	private static double SpawnHeight;
	
	public scEntitySlime(World par1World)
	{
		super(par1World);
		SpawnHeight = mod_SpawnChange.SlimeSpawnHeight;
	}

	public boolean getCanSpawnHere()
	{
		return this.posY < SpawnHeight ? super.getCanSpawnHere() : false;
	}
}
