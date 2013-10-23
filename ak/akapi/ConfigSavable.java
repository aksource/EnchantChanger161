package ak.akapi;

import static net.minecraftforge.common.Property.Type.*;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class ConfigSavable extends Configuration
{
	public ConfigSavable(File file)
	{
		super(file);
	}
    public void set(String category, String key, boolean defaultValue)
    {
        set(category, key, Boolean.toString(defaultValue), BOOLEAN);
    }
	public void set(String category, String key, HashSet<Integer> set)
	{
		String[] values = new String[set.size()];
		Iterator it = set.iterator();
		int i = 0;
		while(it.hasNext())
		{
			values[i] = Integer.toString((Integer) it.next());
			i++;
		}
		set(category, key, values, INTEGER);
	}
	public void set(String category, String key, String str, Property.Type type)
	{
		ConfigCategory cat = getCategory(category);
		Map<String, Property> properties;
		Property prop = new Property(key, str, type);
		if (cat.containsKey(key))
		{
			properties = ObfuscationReflectionHelper.getPrivateValue(ConfigCategory.class, cat, "properties");
			properties.put(key, prop);
		}
	}
	public void set(String category, String key, String[] set, Property.Type type)
	{
		ConfigCategory cat = getCategory(category);
		Map<String, Property> properties;
		Property prop = new Property(key, set, type);
		if (cat.containsKey(key))
		{
			properties = ObfuscationReflectionHelper.getPrivateValue(ConfigCategory.class, cat, "properties");
			properties.put(key, prop);
		}
	}
}