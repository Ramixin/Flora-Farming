package net.ramixin.florafarming.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

import java.util.function.Supplier;

public class GrowingPlantBlock extends Block implements Fertilizable {

    private final Supplier<Block> finalStage;
    private final Supplier<Item> seedItem;
    public static final int PROMOTION_AGE = 6;
    public static final IntProperty AGE = IntProperty.of("age", 0, PROMOTION_AGE-1);
    public static final VoxelShape[] SHAPES_PER_AGE;

    static {
        SHAPES_PER_AGE = new VoxelShape[PROMOTION_AGE];
        for(int i = 0; i < SHAPES_PER_AGE.length; i++) {
            SHAPES_PER_AGE[i] = Block.createCuboidShape(5.0F, 0.0F, 5.0F, 11.0F, 2.0F * (i+1), 11.0F);
        }
    }

    public GrowingPlantBlock(Supplier<Block> finalStageSupplier, Supplier<Item> seedItemSupplier, Settings settings) {
        super(settings);
        this.finalStage = finalStageSupplier;
        this.seedItem = seedItemSupplier;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBaseLightLevel(pos, 0) < 9) return;
        int i = state.get(AGE);
        if(i >= PROMOTION_AGE) return;
        if (random.nextInt(20) == 0) {
            world.setBlockState(pos, this.withAge(i + 1), 2);
        }

    }

    @Override
    protected ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
        return new ItemStack(seedItem.get());
    }

    private BlockState withAge(int i) {
        int clamped = Math.min(i, PROMOTION_AGE);
        if(clamped == PROMOTION_AGE) return finalStage.get().getDefaultState();
        return getDefaultState().with(AGE, clamped);
    }

    @Override
    protected boolean isTransparent(BlockState state) {
        return state.getFluidState().isEmpty();
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return type == NavigationType.AIR && !this.collidable || super.canPathfindThrough(state, type);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.add(AGE));
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int age = state.get(AGE);
        world.setBlockState(pos, withAge(age + (random.nextBoolean() ? 1 : 2)), 2);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d vec3d = state.getModelOffset(pos);
        return SHAPES_PER_AGE[state.get(AGE)].offset(vec3d);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return super.canPlaceAt(state, world, pos) && world.getBlockState(pos.down()).isIn(BlockTags.DIRT);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }
}
