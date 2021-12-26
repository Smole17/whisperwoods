package dev.itsmeow.whisperwoods.client.renderer.entity;

import java.util.UUID;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.itsmeow.whisperwoods.entity.EntityWisp;
import dev.itsmeow.whisperwoods.particle.WispParticleData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidHeadModel;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public class RenderWisp extends LivingEntityRenderer<EntityWisp, EntityModel<EntityWisp>> {

    SkullModel head = new HumanoidHeadModel();

    public RenderWisp(EntityRenderDispatcher mgr) {
        super(mgr, null, 0F);
    }

    @Override
    public void render(EntityWisp entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        int color = entity.getWispColor().getColor();
        float r = (color >> 16) & 0xFF;
        float g = (color >> 8) & 0xFF;
        float b = color & 0xFF;
        float scale = 1;
        if(entity.hasSoul()) {
            UUID target = UUID.fromString(entity.getEntityData().get(EntityWisp.TARGET_ID));
            String name = entity.getEntityData().get(EntityWisp.TARGET_NAME);
            if(target != null && name != null && !name.equals("")) {
                stack.pushPose();
                {
                    stack.translate(0F, 0.8F, 0F);
                    //stack.translate(entity.getPosX(), entity.getPosY() + 0.8F, entity.getPosZ());
                    VertexConsumer vertex = bufferIn.getBuffer(RenderType.entityTranslucent(entity.getTargetTexture()));
                    head.setupAnim(0F, -entity.yHeadRot, 180F + entity.xRot);
                    head.renderToBuffer(stack, vertex, packedLightIn, OverlayTexture.NO_OVERLAY, r / 255F, g / 255F, b / 255F, 0.6F);
                    stack.translate(0F, 0.4F, 0F);
                    this.renderNameTag(entity, new TextComponent(name + "'s soul"), stack, bufferIn, packedLightIn);
                }
                stack.popPose();
            }
        }
        if(!Minecraft.getInstance().isPaused()) {
            if(System.nanoTime() - entity.lastSpawn >= 100_000_000L) {
                entity.lastSpawn = System.nanoTime();
                if(entity.getEntityData().get(EntityWisp.ATTACK_STATE) > 0) {
                    scale = ((400F - ((float) entity.getEntityData().get(EntityWisp.ATTACK_STATE))) / 400F) * 5F;
                } else {
                    scale = entity.getEntityData().get(EntityWisp.PASSIVE_SCALE);
                }
                // spawn bottom particles
                for(int i = 0; i < 2; i++) {
                    double xO = (entity.getRandom().nextFloat() * 2F - 1F) / 3.5;
                    double yO = (entity.getRandom().nextFloat() * 2F - 1F) / 6 + 0.8F;
                    double zO = (entity.getRandom().nextFloat() * 2F - 1F) / 3.5;
                    entity.level.addParticle(new WispParticleData(r, g, b, scale),
                    entity.getX() + xO,
                    entity.getY() + yO,
                    entity.getZ() + zO, 0, 0.005F, 0);
                }
                // spawn upper particle
                entity.level.addParticle(new WispParticleData(r, g, b, scale),
                entity.getX() + (entity.getRandom().nextFloat() * 2F - 1F) / 10,
                entity.getY() + (entity.getRandom().nextFloat() * 2F - 1F) / 5 + 1.1F,
                entity.getZ() + (entity.getRandom().nextFloat() * 2F - 1F) / 10, 0, 0.02F, 0);
            }
        }
    }

    @Override
    public ResourceLocation getEntityTexture(EntityWisp entity) {
        return null;
    }

    @Override
    protected boolean canRenderName(EntityWisp entity) {
        return false;
    }
}
