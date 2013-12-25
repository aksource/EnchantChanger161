package VRGenerator;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CEItemLavaUpdater extends Item
{
	private static int LavaUpdateArea = VisibleRayGenerator.LavaUpdateArea;

	CEItemLavaUpdater(int id)
	{
		super(id);
		this.setMaxDamage(132);
		this.setMaxStackSize(1);
	}
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		int px = (int)player.posX;
		int py = (int)player.posY;
		int pz = (int)player.posZ;

		//mod_CompactEngine.addChat("start lava update %d,%d,%d %d", px, py, pz, LavaUpdateArea);
		for(int y = py + LavaUpdateArea; y >= py - LavaUpdateArea; y--){
			for(int z = pz - LavaUpdateArea; z <= pz + LavaUpdateArea; z++){
				for(int x = px - LavaUpdateArea; x <= px + LavaUpdateArea; x++)
				{
					int meta = world.getBlockMetadata(x, y, z);
					if (world.getBlockMaterial(x, y, z) == Material.lava && meta != 0)
					{
						//mod_CompactEngine.addChat("update lava %d,%d,%d", x, y, z);
						int id = world.getBlockId(x, y, z);
						if(meta < 7)
						{
							world.setBlockMetadataWithNotify(x, y, z, id, meta + 1);
							world.scheduleBlockUpdate(x, y, z, id, 1);
						}else{
							world.setBlock(x, y, z, 0);
						}
					}
				}
			}
		}
		//mod_CompactEngine.addChat("end lava update");
		stack.damageItem(1, player);
		return stack;
	}
}