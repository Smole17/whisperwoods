package dev.itsmeow.whisperwoods.entity;

import dev.itsmeow.imdlib.entity.EntityTypeContainer;
import dev.itsmeow.imdlib.entity.util.variant.EntityVariant;
import dev.itsmeow.whisperwoods.WhisperwoodsMod;
import dev.itsmeow.whisperwoods.init.ModEntities;
import dev.itsmeow.whisperwoods.init.ModSounds;
import dev.itsmeow.whisperwoods.util.IOverrideCollisions;
import net.minecraft.block.BlockState;
import net.minecraft.block.TorchBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

public class EntityHidebehind extends EntityCreatureWithSelectiveTypes implements IOverrideCollisions<EntityCreatureWithTypes> {

    public final DamageSource HIDEBEHIND = new EntityDamageSource("hidebehind", this).setDamageIsAbsolute().setDamageBypassesArmor();
    protected static final DataParameter<Integer> HIDING = EntityDataManager.createKey(EntityHidebehind.class, DataSerializers.VARINT);
    protected static final DataParameter<Boolean> OPEN = EntityDataManager.createKey(EntityHidebehind.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Integer> ATTACK_SEQUENCE_TICKS = EntityDataManager.createKey(EntityHidebehind.class, DataSerializers.VARINT);
    protected static final DataParameter<Optional<UUID>> TARGET_UUID = EntityDataManager.createKey(EntityHidebehind.class, DataSerializers.OPTIONAL_UNIQUE_ID);

    public EntityHidebehind(EntityType<? extends EntityHidebehind> type, World world) {
        super(type, world);
        this.stepHeight = 2F;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new HideFromTargetGoal(this));
        this.goalSelector.addGoal(3, new StalkTargetGoal(this, 0.5D, 35F));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 0, false, false, this::isEntityAttackable));
    }

    public boolean hasTargetUUID() {
        return this.getDataManager().get(TARGET_UUID).isPresent();
    }

    public boolean isSequenceTarget(PlayerEntity player) {
        Optional<UUID> opt = this.getDataManager().get(TARGET_UUID);
        return player != null && player.getGameProfile().getId() != null && opt.isPresent() && player.getGameProfile().getId().equals(opt.get());
    }

    public void setSequenceTarget(PlayerEntity player) {
        this.getDataManager().set(TARGET_UUID, player == null || player.getGameProfile().getId() == null ? Optional.empty() : Optional.of(player.getGameProfile().getId()));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(15D);
    }

    public int attackSequenceTicks() {
        return this.dataManager.get(ATTACK_SEQUENCE_TICKS);
    }

    public void attackSequenceTicksDecrement() {
        this.dataManager.set(ATTACK_SEQUENCE_TICKS, attackSequenceTicks() - 1);
    }

    public void setAttackSequenceTicks(int value) {
        this.dataManager.set(ATTACK_SEQUENCE_TICKS, value);
        if(value == 0) {
            this.setSequenceTarget(null);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(source.getTrueSource() == this.getAttackTarget() && this.attackSequenceTicks() > 0) {
            this.setAttackSequenceTicks(0);
        }
        if (!world.isRemote()) {
            boolean isImmediate = source.getImmediateSource() instanceof PlayerEntity;
            PlayerEntity player = isImmediate ? (PlayerEntity) source.getImmediateSource() : (source.getTrueSource() instanceof PlayerEntity ? (PlayerEntity) source.getTrueSource() : null);
            if (player != null) {
                int hiding = this.getHidingInt();
                boolean attackable = this.isEntityAttackable(player);
                if (!attackable || hiding > 0) {
                    // retaliate attacks if you can't chase due to light
                    if (!player.isCreative()) {
                        player.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 15 * (hiding == 1 ? 5 : 20), 0));
                        if (player.getDistance(this) < 3)
                            player.attackEntityFrom(HIDEBEHIND, 1F);
                    }
                    HideFromTargetGoal.doTreeTick(this);
                    return super.attackEntityFrom(source, amount * (hiding == 1 ? 0.5F : 0.25F));
                }
            }
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.getAttackTarget() != null && !this.getAttackTarget().isAlive()) {
            this.setAttackTarget(null);
        }
        if(this.isInWater()) {
            int i = 12;
            int j = 2;
            BlockPos.Mutable bp = new BlockPos.Mutable();
            BlockPos destinationBlock = null;
            for(int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
                for(int l = 0; l < i; ++l) {
                    for(int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
                        for(int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
                            bp.setPos(this.getPosition()).move(i1, k - 1, j1);
                            if(this.world.getBlockState(bp).getBlock().isIn(BlockTags.LOGS)) {
                                destinationBlock = bp.toImmutable();
                            }
                        }
                    }
                }
            }
            boolean fixed = false;
            if(destinationBlock != null) {
                for(Direction dir : Direction.values()) {
                    if(!fixed) {
                        if(this.world.isAirBlock(destinationBlock.offset(dir)) || this.world.getBlockState(destinationBlock.offset(dir)).getBlock().isIn(BlockTags.LEAVES)) {
                            destinationBlock = destinationBlock.offset(dir);
                            fixed = true;
                        }
                    }
                }
            }
            if(fixed) {
                this.setPositionAndUpdate(destinationBlock.getX(), destinationBlock.getY(), destinationBlock.getZ());
            }
        }
        if(!world.isRemote()) {
            if (world.isDaytime() && world.getLightFor(LightType.SKY, this.getPosition()) > 10) {
                this.setHiding(2);
            } else if (this.getHidingInt() == 2) {
                this.setHiding(1);
            }
            if (!this.isBeingViewed() && this.getHidingInt() == 1) {
                this.setHiding(false);
            }
        }
        float atkTicks = attackSequenceTicks();
        if(this.getAttackTarget() != null && this.getAttackTarget().getDistanceSq(this) < 5D && atkTicks == 0 && !this.getHiding() && this.isEntityAttackable(this.getAttackTarget())) {
            if(this.getAttackTarget() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) this.getAttackTarget();
                if(!this.world.isRemote && this.getRNG().nextInt(20) == 0) {
                    if(player.getHealth() > this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue()) {
                        this.attackEntityAsMob(player);
                    } else {
                        this.setAttackSequenceTicks(40);
                        this.setSequenceTarget(player);
                    }
                }
            }
        }

        if(atkTicks > 0) {
            if(!this.getOpen()) {
                this.setOpen(true);
            }
            PlayerEntity target = null;
            if(this.getAttackTarget() instanceof ServerPlayerEntity) {
                target = (ServerPlayerEntity) this.getAttackTarget();
            } else if(FMLEnvironment.dist == Dist.CLIENT) {
                Supplier<Supplier<PlayerEntity>> s = () -> ClientSafeLogic::getTargetClient;
                target = s.get().get();
            }
            if(this.isSequenceTarget(target)) {
                target.setMotion(0, 0, 0);
                double d0 = this.getPosX() - target.getPosX();
                double d1 = this.getPosZ() - target.getPosZ();
                float angle = (float) (MathHelper.atan2(d1, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
                target.setPositionAndRotation(target.getPosX(), target.getPosY(), target.getPosZ(), angle, 0);
                double e0 = target.getPosX() - this.getPosX();
                double e1 = target.getPosZ() - this.getPosZ();
                float angle1 = (float) (MathHelper.atan2(e1, e0) * (double) (180F / (float) Math.PI)) - 90.0F;
                this.setPositionAndRotation(this.getPosX(), this.getPosY(), this.getPosZ(), angle1, 0);
                this.rotationYaw = angle1;
                if (this.attackSequenceTicks() == 20) {
                    target.playSound(ModSounds.HIDEBEHIND_SOUND.get(), 2F, 1F);
                }
                this.getLookController().setLookPositionWithEntity(target, 360F, 360F);
            }
            this.attackSequenceTicksDecrement();
            if(atkTicks - 1 == 0 && !world.isRemote()) {
                this.setOpen(false);
                if(this.getAttackTarget() != null) {
                    this.attackEntityAsMob(target);
                    this.setAttackTarget(null);
                    this.getDataManager().set(TARGET_UUID, Optional.empty());
                }
            }
        }
        if(atkTicks == 0 && this.getOpen()) {
            this.setOpen(false);
        }
    }

    public static class ClientSafeLogic {
        public static PlayerEntity getTargetClient() {
            return Minecraft.getInstance().player;
        }
    }

    public boolean isEntityAttackable(LivingEntity target) {
        Item mainItem = target.getHeldItem(Hand.MAIN_HAND).getItem();
        Item offItem = target.getHeldItem(Hand.OFF_HAND).getItem();
        return world.getLight(target.getPosition()) < 8 && !(mainItem instanceof BlockItem && ((BlockItem)mainItem).getBlock() instanceof TorchBlock) && !(offItem instanceof BlockItem && ((BlockItem)offItem).getBlock() instanceof TorchBlock);
    }

    @Override
    protected float getWaterSlowDown() {
        return 1.0F;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    public boolean getHiding() {
        return this.getHidingInt() != 0;
    }

    public int getHidingInt() {
        return this.getDataManager().get(HIDING);
    }

    public void setHiding(boolean hiding) {
        this.getDataManager().set(HIDING, hiding ? 1 : 0);
    }

    public void setHiding(int hiding) {
        this.getDataManager().set(HIDING, hiding);
    }

    public boolean getOpen() {
        return this.getDataManager().get(OPEN);
    }

    public void setOpen(boolean open) {
        this.getDataManager().set(OPEN, open);
    }

    public boolean isBeingViewed() {
        return Math.abs(this.getTargetViewingAngle()) <= 50;
    }

    /**
     * @return The viewing angle of the attack target (0 when looking directly at
     *         HB, 180 if opposite). If there is no target, returns -1000
     */
    public double getTargetViewingAngle() {
        LivingEntity target = this.getAttackTarget();
        if(target == null) {
            return -1000;
        }
        return MathHelper.wrapDegrees(getRequiredViewingAngle() - MathHelper.wrapDegrees(getAttackTarget().rotationYaw));
    }

    /**
     * @return The required viewing angle of the attack target to be looking at it.
     *         If there is no target, returns -1000
     */
    public double getRequiredViewingAngle() {
        LivingEntity target = this.getAttackTarget();
        if(target == null) {
            return -1000;
        }
        return MathHelper.wrapDegrees(90D + Math.toDegrees(Math.atan2(target.getPosZ() - this.getPosZ(), target.getPosX() - this.getPosX())));
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        float f = (float) this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue();
        float f1 = (float) this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).getValue();
        if(entity instanceof LivingEntity) {
            f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((LivingEntity) entity).getCreatureAttribute());
            f1 += (float) EnchantmentHelper.getKnockbackModifier(this);
        }

        int i = EnchantmentHelper.getFireAspectModifier(this);
        if(i > 0) {
            entity.setFire(i * 4);
        }

        boolean flag = entity.attackEntityFrom(HIDEBEHIND, f);
        if(flag) {
            if(f1 > 0.0F && entity instanceof LivingEntity) {
                ((LivingEntity) entity).knockBack(this, f1 * 0.5F, MathHelper.sin(this.rotationYaw * ((float) Math.PI / 180F)), -MathHelper.cos(this.rotationYaw * ((float) Math.PI / 180F)));
                this.setMotion(this.getMotion().mul(0.6D, 1.0D, 0.6D));
            }

            if(entity instanceof PlayerEntity) {
                PlayerEntity playerentity = (PlayerEntity) entity;
                ItemStack itemstack = this.getHeldItemMainhand();
                ItemStack itemstack1 = playerentity.isHandActive() ? playerentity.getActiveItemStack() : ItemStack.EMPTY;
                if(!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.canDisableShield(itemstack1, playerentity, this) && itemstack1.isShield(playerentity)) {
                    float f2 = 0.25F + (float) EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;
                    if(this.rand.nextFloat() < f2) {
                        playerentity.getCooldownTracker().setCooldown(itemstack.getItem(), 100);
                        this.world.setEntityState(playerentity, (byte) 30);
                    }
                }
            }

            this.applyEnchantments(this, entity);
        }

        return flag;
    }

    @Override
    public boolean isEntityInsideOpaqueBlock() {
        return insideOpaque();
    }

    @Override
    public Vec3d getAllowedMovement(Vec3d vec) {
        return allowedMove(vec);
    }

    @Override
    public boolean canPassThrough(BlockState state) {
        return state.getBlock().isIn(BlockTags.LEAVES);
    }

    @Override
    public boolean preventSuffocation(BlockState state) {
        return state.getBlock().isIn(BlockTags.LOGS) || canPassThrough(state);
    }

    public static class HideFromTargetGoal extends Goal {
        private final EntityHidebehind hidebehind;

        public HideFromTargetGoal(EntityHidebehind hb) {
            this.hidebehind = hb;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            return hidebehind.getAttackTarget() != null && (hidebehind.isBeingViewed() || !hidebehind.isEntityAttackable(hidebehind.getAttackTarget())) && hidebehind.attackSequenceTicks() == 0;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return this.shouldExecute();
        }

        @Override
        public void resetTask() {
            if(hidebehind.getHidingInt() == 1)
                hidebehind.setHiding(false);
        }

        @Override
        public void startExecuting() {
            if(!hidebehind.getHiding())
                hidebehind.setHiding(true);
            this.hidebehind.getNavigator().clearPath();
        }

        @Override
        public void tick() {
            doTreeTick(hidebehind);
        }

        public static void doTreeTick(EntityHidebehind hidebehind) {
            hidebehind.getNavigator().clearPath();
            boolean nearTree = false;
            for(Direction dir : Direction.values()) {
                if(!nearTree) {
                    if(hidebehind.world.getBlockState(hidebehind.getPosition().offset(dir)).getBlock().isIn(BlockTags.LOGS)) {
                        nearTree = true;
                    }
                    if(hidebehind.world.getBlockState(hidebehind.getPosition().up(3).offset(dir)).getBlock().isIn(BlockTags.LEAVES)) {
                        nearTree = true;
                    }
                }
            }
            if(!nearTree && hidebehind.getRNG().nextInt(5) == 0) {
                int i = 12;
                int j = 2;
                BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();
                BlockPos destinationBlock = null;
                for(int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
                    for(int l = 0; l < i; ++l) {
                        for(int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
                            for(int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
                                blockpos$mutableblockpos.setPos(hidebehind.getPosition()).move(i1, k - 1, j1);
                                if(hidebehind.world.getBlockState(blockpos$mutableblockpos).getBlock().isIn(BlockTags.LOGS)) {
                                    destinationBlock = blockpos$mutableblockpos.toImmutable();
                                }
                            }
                        }
                    }
                }
                boolean fixed = false;
                if(destinationBlock != null) {
                    for(Direction dir : Direction.values()) {
                        if(!fixed) {
                            if(hidebehind.world.isAirBlock(destinationBlock.offset(dir)) || hidebehind.world.getBlockState(destinationBlock.offset(dir)).getBlock().isIn(BlockTags.LEAVES)) {
                                destinationBlock = destinationBlock.offset(dir);
                                fixed = true;
                            }
                        }
                    }
                }
                if(fixed) {
                    hidebehind.setPositionAndUpdate(destinationBlock.getX(), destinationBlock.getY(), destinationBlock.getZ());
                }
            }
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(HIDING, 0);
        this.dataManager.register(OPEN, false);
        this.dataManager.register(ATTACK_SEQUENCE_TICKS, 0);
        this.dataManager.register(TARGET_UUID, Optional.empty());
    }

    @Override
    public boolean canDespawn(double range) {
        return world.isDaytime() && super.canDespawn(range);
    }

    @Override
    protected PathNavigator createNavigator(World world) {
        return new HidebehindGroundNavigator(this, world);
    }

    public static class HidebehindGroundNavigator extends GroundPathNavigator {

        public HidebehindGroundNavigator(MobEntity entityliving, World world) {
            super(entityliving, world);
        }

        @Override
        protected PathFinder getPathFinder(int i1) {
            this.nodeProcessor = new HidebehindNodeProcessor();
            this.nodeProcessor.setCanEnterDoors(true);
            return new PathFinder(this.nodeProcessor, i1);
        }

        public static class HidebehindNodeProcessor extends WalkNodeProcessor {
            @Override
            protected PathNodeType getSpecificPathNodeType(IBlockReader reader, boolean b1, boolean b2, BlockPos pos, PathNodeType typeIn) {
                return typeIn == PathNodeType.LEAVES ? PathNodeType.OPEN : super.getSpecificPathNodeType(reader, b1, b2, pos, typeIn);
            }
        }

    }

    public static class StalkTargetGoal extends Goal {
        private final EntityHidebehind hidebehind;
        private LivingEntity target;
        private final double speed;
        private final float maxTargetDistance;

        public StalkTargetGoal(EntityHidebehind creature, double speedIn, float targetMaxDistance) {
            this.hidebehind = creature;
            this.speed = speedIn;
            this.maxTargetDistance = targetMaxDistance;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean shouldExecute() {
            this.target = this.hidebehind.getAttackTarget();
            if(this.target == null || this.target.getDistanceSq(this.hidebehind) > (double) (this.maxTargetDistance * this.maxTargetDistance)) {
                return false;
            } else {
                Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockTowards(this.hidebehind, 16, 7, new Vec3d(this.target.getPosX(), this.target.getPosY(), this.target.getPosZ()));
                return vec3d != null && hidebehind.isEntityAttackable(target) && hidebehind.attackSequenceTicks() <= 0 && !hidebehind.getHiding();
            }
        }

        @Override
        public boolean shouldContinueExecuting() {
            return !hidebehind.getHiding() && !this.hidebehind.getNavigator().noPath() && this.target.isAlive() && this.target.getDistanceSq(this.hidebehind) < (double) (this.maxTargetDistance * this.maxTargetDistance) && hidebehind.attackSequenceTicks() == 0 && hidebehind.isEntityAttackable(target);
        }

        @Override
        public void resetTask() {
            this.target = null;
            this.hidebehind.getNavigator().clearPath();
        }

        @Override
        public void startExecuting() {
            this.hidebehind.lookController.setLookPositionWithEntity(this.target, 1000, 1000);
            this.hidebehind.getNavigator().tryMoveToEntityLiving(this.hidebehind.getAttackTarget(), this.speed);
        }
    }

    @Override
    public EntityTypeContainer<EntityHidebehind> getContainer() {
        return ModEntities.HIDEBEHIND;
    }

    @Override
    public String[] getTypesFor(Biome biome, Set<BiomeDictionary.Type> types, SpawnReason reason) {
        if(biome == Biomes.GIANT_SPRUCE_TAIGA || biome == Biomes.GIANT_SPRUCE_TAIGA_HILLS || biome == Biomes.GIANT_TREE_TAIGA || biome == Biomes.GIANT_TREE_TAIGA_HILLS) {
            return new String[] { "mega_taiga", "mega_taiga", "mega_taiga", "darkforest" };
        }
        if(types.contains(BiomeDictionary.Type.CONIFEROUS)) {
            return new String[] { "coniferous", "coniferous", "coniferous", "coniferous", "black", "darkforest" };
        }
        if(types.contains(BiomeDictionary.Type.FOREST)) {
            return new String[] { "forest", "black", "darkforest" };
        }
        return new String[] { "black", "coniferous", "darkforest", "forest", "mega_taiga" };
    }

    public static class HidebehindVariant extends EntityVariant {

        private ResourceLocation openTexture;

        public HidebehindVariant(String nameTexture) {
            super(WhisperwoodsMod.MODID, nameTexture, "hidebehind_" + nameTexture);
            this.openTexture = new ResourceLocation(WhisperwoodsMod.MODID, "textures/entity/hidebehind_" + nameTexture + "_open.png");
        }

        @Override
        public ResourceLocation getTexture(Entity entity) {
            if(entity instanceof EntityHidebehind) {
                return ((EntityHidebehind) entity).getOpen() ? openTexture : texture;
            } else {
                return texture;
            }
        }
    }

}