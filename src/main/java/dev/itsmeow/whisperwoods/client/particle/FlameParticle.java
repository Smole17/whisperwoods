package dev.itsmeow.whisperwoods.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FlameParticle extends TextureSheetParticle {

    private FlameParticle(ClientLevel world, double x, double y, double z, double moveX, double moveY, double moveZ, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.setSize(0.05F, 0.05F);
        this.lifetime = this.random.nextInt(5) + 15;
        this.pickSprite(spriteSet);
        this.gravity = 3.0E-6F;
        this.xd = moveX;
        this.yd = moveY + (double) (this.random.nextFloat() / 500.0F);
        this.zd = moveZ;
        this.alpha = 0.8F;
    }

    @Override
    protected int getLightColor(float partialTick) {
        return 240;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if(this.age++ < this.lifetime && !(this.alpha <= 0.0F)) {
            this.xd += (double) (this.random.nextFloat() / 5000.0F * (float) (this.random.nextBoolean() ? 1 : -1));
            this.zd += (double) (this.random.nextFloat() / 5000.0F * (float) (this.random.nextBoolean() ? 1 : -1));
            this.yd -= (double) this.gravity;
            this.move(this.xd, this.yd, this.zd);
            if(this.age >= this.lifetime - 60 && this.alpha > 0.01F) {
                this.alpha -= 0.015F;
            }
        } else {
            this.remove();
        }
    }

    public ParticleRenderType getRenderType() {
        return WispParticle.PARTICLE_SHEET_TRANSLUCENT_114;
    }

    @OnlyIn(Dist.CLIENT)
    public static class FlameFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public FlameFactory(SpriteSet sprites) {
            this.spriteSet = sprites;
        }

        public Particle makeParticle(SimpleParticleType type, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new FlameParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
        }
    }
}