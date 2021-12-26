package dev.itsmeow.whisperwoods.particle;

import java.util.Locale;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import dev.itsmeow.whisperwoods.init.ModParticles;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WispParticleData implements ParticleOptions {
    public static final Codec<WispParticleData> CODEC = RecordCodecBuilder.create((group) -> {
        return group.group(Codec.FLOAT.fieldOf("r").forGetter((in) -> {
            return in.getRed();
        }), Codec.FLOAT.fieldOf("g").forGetter((in) -> {
            return in.getGreen();
        }), Codec.FLOAT.fieldOf("b").forGetter((in) -> {
            return in.getBlue();
        }), Codec.FLOAT.fieldOf("scale").forGetter((in) -> {
            return in.getScale();
        })).apply(group, WispParticleData::new);
    });
    @SuppressWarnings("deprecation")
    public static final ParticleOptions.Deserializer<WispParticleData> DESERIALIZER = new ParticleOptions.Deserializer<WispParticleData>() {
        public WispParticleData fromCommand(ParticleType<WispParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float f = (float) reader.readDouble();
            reader.expect(' ');
            float f1 = (float) reader.readDouble();
            reader.expect(' ');
            float f2 = (float) reader.readDouble();
            reader.expect(' ');
            float f3 = (float) reader.readDouble();
            return new WispParticleData(f, f1, f2, f3);
        }

        public WispParticleData fromNetwork(ParticleType<WispParticleData> particleTypeIn, FriendlyByteBuf buffer) {
            return new WispParticleData(buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
        }
    };
    private final float red;
    private final float green;
    private final float blue;
    private final float scale;

    public WispParticleData(float r, float g, float b, float scale) {
        this.red = r;
        this.green = g;
        this.blue = b;
        this.scale = scale;
    }

    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeFloat(this.red);
        buffer.writeFloat(this.green);
        buffer.writeFloat(this.blue);
        buffer.writeFloat(this.scale);
    }

    @SuppressWarnings("deprecation")
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", Registry.PARTICLE_TYPE.getKey(this.getType()), this.red, this.green, this.blue, this.scale);
    }

    public ParticleType<WispParticleData> getType() {
        return ModParticles.WISP.get();
    }

    @OnlyIn(Dist.CLIENT)
    public float getRed() {
        return this.red;
    }

    @OnlyIn(Dist.CLIENT)
    public float getGreen() {
        return this.green;
    }

    @OnlyIn(Dist.CLIENT)
    public float getBlue() {
        return this.blue;
    }

    @OnlyIn(Dist.CLIENT)
    public float getScale() {
        return this.scale;
    }

}
