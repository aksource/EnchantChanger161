package net.minecraft.src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Locale;
import net.minecraft.client.resources.ReloadableResourceManager;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cpw.mods.fml.common.registry.LanguageRegistry;

public class mod_IDwakander extends BaseMod
{
	public String getVersion()
	{
		return "1.6.2-2";
	}

	public String getPriorities()
	{
		return "after:*;";
	}

	private final String crlf = System.getProperty("line.separator");
	private final int blockListSize = 4096;
	private final int itemListSize = 32000;
	private ArrayList<String> IDs = new ArrayList<String>();
	private ArrayList<String> metaNames = new ArrayList<String>();
	private long start,end;
	private String ext;
	private Minecraft minecraft = Minecraft.getMinecraft();//ModLoader.getMinecraftInstance();
//	private StringTranslate trans = StringTranslate.getInstance();
	private boolean isForge = true;

	@MLProp(info="csv形式で出力する。")
	public static boolean csvFormat = false;
	@MLProp(info="ブロック/アイテムの空きIDも出力する。")
	public static boolean nullItemID = false;
	@MLProp(info="ブロック/アイテムのメタデータも取得する。")
	public static boolean getMetadata = true;
	@MLProp(info="ファイル出力フォルダ。.minecraft以下に作成される。")
	public static String directory = "IDwakander";
	@MLProp(info="出力ファイルの文字コード。通常は変更する必要はない。")
	public static String charset = "UTF-8";
	@MLProp(info="翻訳ファイルを出力する。")
	public static boolean outputLanguageFile = true;
	@MLProp(info="EntityIDリストを出力する。")
	public static boolean outputEntityIDs = true;
	@MLProp(info="メタデータを持つアイテムの基本名も出力する。")
	public static boolean outputMetadataNull = true;
	@MLProp(info="バニラの翻訳データも出力する。")
	public static boolean outputVanillaLanguage = false;
	@MLProp(info="メタデータを詳細に取得するアイテムの基本名。 ,（カンマ）区切りで複数指定可能")
	public static String outputMetadataDetailNames = "tile.rpwire";
	@MLProp(info="ブロック/アイテムの空きメタデータも出力する。")
	public static boolean nullItemMetadata = false;

	@MLProp(info="エラーレポートを出力する。")
	public static boolean outputErrorLogs = false;
	private ArrayList<String> errorLog = new ArrayList<String>();
	private boolean printOut = false;
	
	public void load()
	{
		ext = csvFormat ? ".csv" : ".txt";
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void modsLoaded()
	{
		if(outputLanguageFile) printLanguage();
		printItemIDs();
		if(outputEntityIDs) printEntityIDs();
	}
	@ForgeSubscribe
	public void printInGame(EntityJoinWorldEvent event)
	{
		if(!printOut && event.entity instanceof EntityPlayer)
		{
			if(outputLanguageFile) printLanguage();
			printItemIDs();
			if(outputEntityIDs) printEntityIDs();
			printOut = true;
		}
	}
	private void printItemIDs()
	{
//		LanguageRegistry.reloadLanguageTable();
		start = System.currentTimeMillis();
		int s1,s2,s3;
		s1 = checkItems(1, 256);
		s2 = checkItems(256, blockListSize);
		s3 = checkItems(blockListSize, itemListSize);

		addData(String.format("\nEmpty ID count is    1-255   (mapGenerateBlockArea)   : %d ids free",s1));
		addData(String.format(  "Empty ID count is  256-4095  (ExtraBlock and ItemArea): %d ids free",s2));
		addData(String.format(  "Empty ID count is 4096-31999 (ItemOnlyArea)           : %d ids free",s3));

		printList("blockitemIDs"+ext);
		if(outputErrorLogs && !errorLog.isEmpty()) printList("IDwakanderError.log", errorLog, false);
	}

	private int checkItems(int min, int max)
	{
		int slot = 0;
		Item item;
		ItemStack itemstack;
		boolean nullFlag = false;
		int maxDamage;
		
		for(int id=min; id<max; id++)
		{
			item = Item.itemsList[id];
			if(item == null)
			{
				slot++;
				if(nullItemID)
				{
					addData(id);
					continue;
				}
				if(nullFlag) continue;
				nullFlag = true;
				addData(0);
				continue;
			}
			nullFlag = false;
			
			if(getMetadata && item.getHasSubtypes())
			{
//				if(item instanceof ItemMultiTextureTile)
//				{
//					ItemMultiTextureTile imtt = (ItemMultiTextureTile)item;
//					String[] tileNames;//"field_82804_b"
//					tileNames = ModLoader.getPrivateValue(ItemMultiTextureTile.class, imtt, 1);
//					maxDamage = Array.getLength(tileNames)-1;
//				}else{
					maxDamage = 15;
//				}
				if(outputMetadataNull) addData(item);
			}else{
				maxDamage = 0;
			}
			
			String str[] = outputMetadataDetailNames.split(",");
			ArrayList<String> detailList = new ArrayList<String>(str.length);
			for(int i = 0; i < str.length; i++){ detailList.add(str[i].trim()); }
			
			if(getMetadata && detailList.contains(item.getUnlocalizedName())) maxDamage = 32767;

			metaNames.clear();
			for(int meta=0; meta<=maxDamage; meta++)
			{
				String name = "";
				itemstack = new ItemStack(id, 1, meta);
				if(itemstack == null)
				{
					continue;
				}
				try
				{
					name = itemstack.getItem().getUnlocalizedName(itemstack);
					if(name!=null && !("".equals(name)))
					{
						if(!metaNames.contains(name) || nullItemMetadata)
						{
							metaNames.add(name);
							addData(itemstack);
						}
					}
//					if(!metaNames.contains(name) || maxDamage>16 )
//					{
//						metaNames.add(name);
//						addData(itemstack);
//					}
				}
				catch (IndexOutOfBoundsException e)
				{
					continue;
				}
				catch (Exception e)
				{
					if(outputErrorLogs) addErrorLogs(e, id, meta);
					continue;
				}
			}
			metaNames.clear();
		}
		return slot;
	}

	private void printEntityIDs()
	{
		start = System.currentTimeMillis();
		int slot = 0;
		for(Iterator i = EntityList.IDtoClassMapping.keySet().iterator(); i.hasNext();)
		{
			Integer id = (Integer)i.next();
			String name = EntityList.getStringFromID(id.intValue());
			addData(id.intValue(), name);
			if(id < 128){ slot++; }
		}
		Collections.sort(IDs);

		addData(String.format("\nEmpty Safety MOB ID count is 1-127 : %d ids free",127-slot));
		printList("entityIDs"+ext);
	}

	private void printLanguage()
	{
		Set<String> lang;
		Properties prop = new Properties();
		Map map = Maps.newHashMap();
		start = System.currentTimeMillis();
		if(outputVanillaLanguage || !isForge)
		{
//			trans.setLanguage(minecraft.gameSettings.language, false);
//			prop = trans.translateTable;
			loadVanillaLangData(map);
			LanguageRegistry.instance().loadLanguageTable(map, minecraft.gameSettings.language);
		}else{
//			LanguageRegistry.instance().loadLanguageTable(prop, minecraft.gameSettings.language);
			LanguageRegistry.instance().loadLanguageTable(map, minecraft.gameSettings.language);
//			StringTranslate.func_135063_a(map);
		}
//		lang = prop.stringPropertyNames();
		lang = map.keySet();

		for(Iterator i = lang.iterator(); i.hasNext();)
		{
			String key = (String)i.next();
//			IDs.add(key+"="+prop.getProperty(key)+crlf);
			IDs.add(key+"="+map.get(key)+crlf);
		}
		Collections.sort(IDs);
		printList(minecraft.gameSettings.language+".lang", false);
	}
	private Map loadVanillaLangData(Map map)
	{
		Locale locale = new Locale();
		ReloadableResourceManager RRM = ModLoader.getPrivateValue(Minecraft.class, minecraft, 61);
		ArrayList list = Lists.newArrayList(new String[] {minecraft.gameSettings.language});
		locale.func_135022_a(RRM, list);
		Map mapLocale = ModLoader.getPrivateValue(Locale.class, locale, 2);
		map.putAll(mapLocale);
		return map;
	}
	private void addData(ItemStack itemstack)
	{
		String name = itemstack.getItem().getUnlocalizedName(itemstack);
		//不具合：MODのブロック・アイテムの翻訳がこの時点でUnlocalizedNameに紐付けされていない．langファイルは正常に出るなぞ．．
//		String transname = itemstack.getItem().getItemStackDisplayName(itemstack);
		String transname = I18n.func_135053_a(itemstack.getItem().getUnlocalizedName(itemstack) + ".name");//アプローチは違うが同じ結果
		String meta = (getMetadata && itemstack.getHasSubtypes() && !itemstack.isItemStackDamageable()) ? itemstack.getItemDamage()+"" : "";
		String record;
		if(itemstack.getItem() instanceof ItemRecord)
		{
			transname += " " + ((ItemRecord)itemstack.getItem()).getRecordTitle();
		}
		try
		{
			String format;
			int id = itemstack.itemID;
			if(id > 255)
			{
				if(csvFormat)
				{
					format = "%d,%d,%s,%s,%s"+crlf;
				}else{
					format = "%d(%d)%s = %s (%s)"+crlf;
				}
				IDs.add(String.format(format, id, id-256, meta, name, transname));
			}else{
				if(csvFormat)
				{
					format = "%d,%s,%s,%s"+crlf;
				}else{
					format = "%d%s = %s (%s)"+crlf;
				}
				IDs.add(String.format(format, id, meta, name, transname));
			}
//			if(csvFormat)
//			{
//				format = "%d,%s,%s,%s"+crlf;
//			}else{
//				format = "%d:%s = %s (%s)"+crlf;
//			}
//			IDs.add(String.format(format, itemstack.itemID, meta, name, transname));
		}
		catch (IndexOutOfBoundsException e)
		{
		}
	}

	private void addData(Item item)
	{
		try
		{
			int id = item.itemID;
			if(id > 255)
			{
				if(csvFormat)
				{
					IDs.add(String.format("%d,%d,,%s,"+crlf, id, id-256, item.getUnlocalizedName()));
				}else{
					IDs.add(String.format("%d(%d): = %s"+crlf, id, id-256, item.getUnlocalizedName()));
				}
			}else{
				if(csvFormat)
				{
					IDs.add(String.format("%d,,%s,"+crlf, id, item.getUnlocalizedName()));
				}else{
					IDs.add(String.format("%d: = %s"+crlf, id, item.getUnlocalizedName()));
				}
			}
//			if(csvFormat)
//			{
//				IDs.add(String.format("%d,,%s,"+crlf, item.itemID, item.getUnlocalizedName()));
//			}else{
//				IDs.add(String.format("%d = %s"+crlf, item.itemID, item.getUnlocalizedName()));
//			}
		}
		catch (IndexOutOfBoundsException e)
		{
		}
	}

	private void addData(int id)
	{
		if(id==0)
		{
			IDs.add(crlf);
		}
		else if(csvFormat)
		{
			String str = id > 255 ? "," + (id-256): "";
			IDs.add(id + str + ",,空きID" + crlf);
		}
		else
		{
			String str = id > 255 ? "(" + (id-256)+")": "";
			IDs.add(id + str + " = 空きID" + crlf);
		}
	}

	private void addData(int id, String name)
	{
		if(csvFormat)
		{
			IDs.add(String.format("%3d,%s,%s"+crlf, id, name, StatCollector.translateToLocal("entity."+name+".name")));
		}
		else
		{
			IDs.add(String.format("%3d = %s (%s)"+crlf, id, name, StatCollector.translateToLocal("entity."+name+".name")));
		}
	}

	private void addData(String str)
	{
		if(str.length() > 0) IDs.add(str+crlf);
	}
	
	private void addErrorLogs(Exception e, int id, int meta)
	{
		Item item = Item.itemsList[id];
		errorLog.add(String.format("%s: %d-%d: %s"+crlf, e.toString(), id, meta, item.getUnlocalizedName()));
	}
	
	private void printList(String filename)
	{
		printList(filename, IDs, true);
	}
	private void printList(String filename, boolean flag)
	{
		printList(filename, IDs, flag);
	}
	private void printList(String filename, Collection col)
	{
		printList(filename, col, true);
	}
	private void printList(String filename, Collection col, boolean flag)
	{
		File dir = new File(minecraft.mcDataDir, directory);
		if(!dir.exists()) dir.mkdir();
		File file = new File(dir, filename);
		try
		{
			OutputStream stream = new FileOutputStream(file);
			BufferedWriter src = new BufferedWriter(new OutputStreamWriter(stream, charset));
			for(Iterator i = col.iterator(); i.hasNext();)
			{
				src.write((String)i.next());
			}
			end = System.currentTimeMillis();
			long time = end - start;
			if(flag) src.write("#output time is "+String.format("%d", time)+" ms.\n");
			src.flush();
			src.close();
			col.clear();
		}
		catch (IOException e)
		{
			ModLoader.throwException(String.format("IDwakander: %s に書き込みできません。", file.getName()), e);
		}
	}

}
