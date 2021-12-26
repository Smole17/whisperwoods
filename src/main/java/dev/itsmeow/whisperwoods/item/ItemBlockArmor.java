package dev.itsmeow.whisperwoods.item;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemBlockArmor extends ArmorItem {
    @Deprecated
    private final Block block;

    public ItemBlockArmor(Block blockIn, ArmorMaterial materialIn, EquipmentSlot slot, Item.Properties builderIn) {
        super(materialIn, slot, builderIn);
        this.block = blockIn;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult actionresulttype = this.tryPlace(new BlockPlaceContext(context));
        return !actionresulttype.consumesAction() && this.isEdible() ? this.use(context.getLevel(), context.getPlayer(), context.getHand()).getResult() : actionresulttype;
    }

    public InteractionResult tryPlace(BlockPlaceContext context) {
        if(!context.canPlace()) {
            return InteractionResult.FAIL;
        } else {
            BlockPlaceContext blockitemusecontext = this.getBlockItemUseContext(context);
            if(blockitemusecontext == null) {
                return InteractionResult.FAIL;
            } else {
                BlockState blockstate = this.getStateForPlacement(blockitemusecontext);
                if(blockstate == null) {
                    return InteractionResult.FAIL;
                } else if(!this.placeBlock(blockitemusecontext, blockstate)) {
                    return InteractionResult.FAIL;
                } else {
                    BlockPos blockpos = blockitemusecontext.getClickedPos();
                    Level world = blockitemusecontext.getLevel();
                    Player playerentity = blockitemusecontext.getPlayer();
                    ItemStack itemstack = blockitemusecontext.getItemInHand();
                    BlockState blockstate1 = world.getBlockState(blockpos);
                    Block block = blockstate1.getBlock();
                    if(block == blockstate.getBlock()) {
                        blockstate1 = ItemBlockArmor.func_219985_a(blockpos, world, itemstack, blockstate1);
                        this.onBlockPlaced(blockpos, world, playerentity, itemstack, blockstate1);
                        block.setPlacedBy(world, blockpos, blockstate1, playerentity, itemstack);
                        if(playerentity instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) playerentity, blockpos, itemstack);
                        }
                    }

                    SoundType soundtype = blockstate1.getSoundType(world, blockpos, context.getPlayer());
                    world.playSound(playerentity, blockpos, this.getPlaceSound(blockstate1, world, blockpos, context.getPlayer()), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    if(playerentity == null || !playerentity.abilities.instabuild) {
                        itemstack.shrink(1);
                    }

                    return InteractionResult.sidedSuccess(world.isClientSide);
                }
            }
        }
    }

    @Deprecated // Forge: Use more sensitive version {@link BlockItem#getPlaceSound(BlockState, IBlockReader, BlockPos, Entity) }
    protected SoundEvent getPlaceSound(BlockState state) {
        return state.getSoundType().getPlaceSound();
    }

    // Forge: Sensitive version of BlockItem#getPlaceSound
    protected SoundEvent getPlaceSound(BlockState state, Level world, BlockPos pos, Player entity) {
        return state.getSoundType(world, pos, entity).getPlaceSound();
    }

    @Nullable
    public BlockPlaceContext getBlockItemUseContext(BlockPlaceContext context) {
        return context;
    }

    protected boolean onBlockPlaced(BlockPos pos, Level worldIn, @Nullable Player player, ItemStack stack, BlockState state) {
        return BlockItem.updateCustomBlockEntityTag(worldIn, player, pos, stack);
    }

    @Nullable
    protected BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = this.getBlock().getStateForPlacement(context);
        return blockstate != null && this.canPlace(context, blockstate) ? blockstate : null;
    }

    private static BlockState func_219985_a(BlockPos p_219985_1_, Level p_219985_2_, ItemStack p_219985_3_, BlockState p_219985_4_) {
        BlockState blockstate = p_219985_4_;
        CompoundTag compoundnbt = p_219985_3_.getTag();
        if(compoundnbt != null) {
            CompoundTag compoundnbt1 = compoundnbt.getCompound("BlockStateTag");
            StateDefinition<Block, BlockState> statecontainer = p_219985_4_.getBlock().getStateDefinition();

            for(String s : compoundnbt1.getAllKeys()) {
                Property<?> property = statecontainer.getProperty(s);
                if(property != null) {
                    String s1 = compoundnbt1.get(s).getAsString();
                    blockstate = func_219988_a(blockstate, property, s1);
                }
            }
        }

        if(blockstate != p_219985_4_) {
            p_219985_2_.setBlock(p_219985_1_, blockstate, 2);
        }

        return blockstate;
    }

    private static <T extends Comparable<T>> BlockState func_219988_a(BlockState p_219988_0_, Property<T> p_219988_1_, String p_219988_2_) {
        return p_219988_1_.getValue(p_219988_2_).map((p_219986_2_) -> {
            return p_219988_0_.setValue(p_219988_1_, p_219986_2_);
        }).orElse(p_219988_0_);
    }

    protected boolean canPlace(BlockPlaceContext p_195944_1_, BlockState p_195944_2_) {
        Player playerentity = p_195944_1_.getPlayer();
        CollisionContext iselectioncontext = playerentity == null ? CollisionContext.empty() : CollisionContext.of(playerentity);
        return (!this.checkPosition() || p_195944_2_.canSurvive(p_195944_1_.getLevel(), p_195944_1_.getClickedPos())) && p_195944_1_.getLevel().isUnobstructed(p_195944_2_, p_195944_1_.getClickedPos(), iselectioncontext);
    }

    protected boolean checkPosition() {
        return true;
    }

    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        return context.getLevel().setBlock(context.getClickedPos(), state, 11);
    }

    @Override
    public String getDescriptionId() {
        return this.getBlock().getDescriptionId();
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if(this.allowdedIn(group)) {
            this.getBlock().fillItemCategory(group, items);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        this.getBlock().appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    public Block getBlock() {
        return this.getBlockRaw() == null ? null : this.getBlockRaw().delegate.get();
    }

    private Block getBlockRaw() {
        return this.block;
    }

    public void addToBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn) {
        blockToItemMap.put(this.getBlock(), itemIn);
    }

    public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn) {
        blockToItemMap.remove(this.getBlock());
    }

}
