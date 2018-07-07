package com.lsc.init;

import com.lsc.util.LSCTab;

import net.minecraft.creativetab.CreativeTabs;

public class ModTabs 
{
	public static CreativeTabs lscTab = new LSCTab(CreativeTabs.getNextID(), "tab_lsc");
	public static CreativeTabs lscDevTab = new LSCTab(CreativeTabs.getNextID(), "tab_lsc_dev");
}
