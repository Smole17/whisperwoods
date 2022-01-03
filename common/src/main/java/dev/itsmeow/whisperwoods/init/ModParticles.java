package dev.itsmeow.whisperwoods.init;

import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.itsmeow.whisperwoods.WhisperwoodsMod;
import dev.itsmeow.whisperwoods.particle.WispParticleData;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Supplier;

public class ModParticles {

    private static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(WhisperwoodsMod.MODID, Registry.PARTICLE_TYPE_REGISTRY);

    public static final RegistrySupplier<ParticleType<WispParticleData>> WISP = r("wisp", () -> new ParticleType<WispParticleData>(false, WispParticleData.DESERIALIZER) {
        @Override
        public Codec<WispParticleData> codec() {
            return WispParticleData.CODEC;
        }
    });
    public static final RegistrySupplier<ParticleType<SimpleParticleType>> FLAME = r("flame", () -> new SimpleParticleType(false));

    private static <T extends ParticleOptions> RegistrySupplier<ParticleType<T>> r(String name, Supplier<ParticleType<T>> b) {
        return PARTICLES.register(name, b);
    }

    public static void init() {
        PARTICLES.register();
    }

}
