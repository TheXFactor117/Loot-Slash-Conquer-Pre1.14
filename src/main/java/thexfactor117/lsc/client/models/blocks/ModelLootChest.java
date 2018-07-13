package thexfactor117.lsc.client.models.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author TheXFactor117
 *
 */
@SideOnly(Side.CLIENT)
public class ModelLootChest extends ModelBase
{
	public ModelRenderer base;
	public ModelRenderer lid;

	public ModelLootChest() 
	{
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.lid = new ModelRenderer(this, 0, 0);
        this.lid.addBox(-0.5F, -5F, -14F, 15, 5, 14, 0.0F); // BASED OFF ModelChest
        this.lid.setRotationPoint(1F, 7F, 15F); // BASED OFF ModelChest
        this.base = new ModelRenderer(this, 0, 19);
        this.base.addBox(-0.5F, 0F, 0F, 15, 10, 14, 0.0F); // BASED OFF ModelChest
        this.base.setRotationPoint(1F, 6F, 1F); // BASED OFF ModelChest
    }

	public void renderAll()
	{
		this.lid.render(0.0625F);
		this.base.render(0.0625F);
	}
}