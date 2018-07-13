package thexfactor117.lsc.client.render.entities;

import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import thexfactor117.lsc.client.models.entities.ModelBarbarian;
import thexfactor117.lsc.entities.monsters.EntityBarbarian;
import thexfactor117.lsc.util.Reference;

/**
 * 
 * @author TheXFactor117
 *
 */
public class RenderBarbarian extends RenderBiped<EntityBarbarian>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/models/barbarian.png"); 
	
	public RenderBarbarian(RenderManager manager) 
	{
		super(manager, new ModelBarbarian(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBarbarian entity) 
	{
		return TEXTURE;
	}
}
