package com.lsc.init;

import com.lsc.tabs.LETab;

import net.minecraft.creativetab.CreativeTabs;

public class ModTabs 
{
	public static CreativeTabs tabLE = new LETab(CreativeTabs.getNextID(), "tab_le");
	public static CreativeTabs tabLETest = new LETab(CreativeTabs.getNextID(), "tab_le_test");
}
