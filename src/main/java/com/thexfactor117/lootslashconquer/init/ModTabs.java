package com.thexfactor117.lootslashconquer.init;

import com.thexfactor117.lootslashconquer.tabs.LETab;

import net.minecraft.creativetab.CreativeTabs;

public class ModTabs 
{
	public static CreativeTabs tabLE = new LETab(CreativeTabs.getNextID(), "tab_le");
	public static CreativeTabs tabLETest = new LETab(CreativeTabs.getNextID(), "tab_le_test");
}
