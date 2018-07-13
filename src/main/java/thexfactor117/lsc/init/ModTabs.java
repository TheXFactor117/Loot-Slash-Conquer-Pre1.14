package thexfactor117.lsc.init;

import net.minecraft.creativetab.CreativeTabs;
import thexfactor117.lsc.util.LSCTab;

public class ModTabs 
{
	public static CreativeTabs lscTab = new LSCTab(CreativeTabs.getNextID(), "tab_lsc");
	public static CreativeTabs lscDevTab = new LSCTab(CreativeTabs.getNextID(), "tab_lsc_dev");
}
