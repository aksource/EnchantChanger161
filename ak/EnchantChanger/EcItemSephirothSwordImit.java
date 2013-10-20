package ak.EnchantChanger;

import net.minecraft.item.EnumToolMaterial;

public class EcItemSephirothSwordImit extends EcItemSword
{
	public EcItemSephirothSwordImit(int id)
	{
		super(id, EnumToolMaterial.IRON);
		this.setMaxDamage(EnumToolMaterial.IRON.getMaxUses());
        this.setTextureName(EnchantChanger.EcTextureDomain + "ImitateMasamuneBlade");
	}
}