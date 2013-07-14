package net.minecraft.src.SpawnChange;

import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.src.mod_SpawnChange;
import net.minecraft.world.World;

public class scBlockObsidian extends BlockObsidian
{
	private static boolean portalSpawn;
	public scBlockObsidian(int par1)
	{
		super(par1);
		portalSpawn = mod_SpawnChange.portalSpawn;
		this.func_111022_d("obsidian");
	}
	
	public boolean canCreatureSpawn(EnumCreatureType type, World world, int x, int y, int z) 
	{
		if (portalSpawn && world.getBlockId(x, y + 1, z) == Block.portal.blockID)
			return false;
		return true;
	}

}
