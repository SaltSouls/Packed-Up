package salted.packedup.common.block.handlers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;

public class ConnectionHandler {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;

    /**
     * Represents a pair of neighboring block states along an axis matching the current blocks state.
     *
     * @param first  The neighboring {@link BlockState}, or null if not present
     * @param second The neighboring {@link BlockState}, or null if not present
     */
    public record BlockNeighbors(BlockState first, BlockState second) {}

    /**
     * Enum representing different possible connection parts for blocks.
     * Used to determine how blocks should connect to their neighbors.
     */
    public enum Part {
        // Basic connections
        SINGLE, TOP, BOTTOM, LEFT, RIGHT,

        // Center connections
        CENTER, MIDDLE,

        // Combined connections
        TOP_MIDDLE, BOTTOM_MIDDLE,

        // Corner connections
        CORNER_LEFT, CORNER_RIGHT,
        CORNER_TOP_LEFT, CORNER_TOP_RIGHT,
        CORNER_BOTTOM_LEFT, CORNER_BOTTOM_RIGHT,
        CORNER_CENTER
    }

    // Axis Connections
    // ================

    /**
     * Determines the connection type of a block along its axis by checking neighboring blocks.
     * @see #findNeighborsAlongAxis for neighbor checking implementation
     */
    public Part getAlongAxis(@NotNull LevelAccessor world, @NotNull BlockPos pos, @NotNull BlockState state) {
        return determineConnectionType(findNeighborsAlongAxis(world, pos, state), state.getValue(AXIS));
    }

    /**
     * Determines the connection type of a block along its axis by checking for specific block class.
     * @see #findNeighborsAlongAxis for neighbor checking implementation
     */
    public Part getAlongAxis(@NotNull LevelAccessor world, @NotNull BlockPos pos, @NotNull BlockState state, Class<?> clazz) {
        return determineConnectionType(findNeighborsAlongAxis(world, pos, state, clazz), state.getValue(AXIS));
    }

    /**
     * Retrieves neighboring blocks along the axis.
     * @see BlockNeighbors for return type details
     */
    public BlockNeighbors getBlockAlongAxis(@NotNull LevelAccessor world, @NotNull BlockPos pos, @NotNull BlockState state) {
        return findNeighborsAlongAxis(world, pos, state);
    }

    /**
     * Retrieves neighboring blocks of specific class along the axis.
     * @see BlockNeighbors for return type details
     */
    public BlockNeighbors getBlockAlongAxis(@NotNull LevelAccessor world, @NotNull BlockPos pos, @NotNull BlockState state, Class<?> clazz) {
        return findNeighborsAlongAxis(world, pos, state, clazz);
    }

    // Facing Connections
    // ==================

    /**
     * Determines connection type based on facing direction.
     * @see #findNeighborsAlongFacing for neighbor checking implementation
     */
    public Part getAlongFacing(LevelAccessor world, BlockPos pos, @NotNull BlockState state) {
        return determineConnectionType(findNeighborsAlongFacing(world, pos, state), state.getValue(FACING).getAxis());
    }

    /**
     * Determines connection type based on facing direction for specific block class.
     * @see #findNeighborsAlongFacing for neighbor checking implementation
     */
    public Part getAlongFacing(LevelAccessor world, BlockPos pos, @NotNull BlockState state, Class<?> clazz) {
        return determineConnectionType(findNeighborsAlongFacing(world, pos, state, clazz), state.getValue(FACING).getAxis());
    }

    /**
     * Retrieves neighboring blocks along facing direction.
     * @see BlockNeighbors for return type details
     */
    public BlockNeighbors getBlockAlongFacing(@NotNull LevelAccessor world, @NotNull BlockPos pos, @NotNull BlockState state) {
        return findNeighborsAlongFacing(world, pos, state);
    }

    /**
     * Retrieves neighboring blocks of specific class along facing direction.
     * @see BlockNeighbors for return type details
     */
    public BlockNeighbors getBlockAlongFacing(@NotNull LevelAccessor world, @NotNull BlockPos pos, @NotNull BlockState state, Class<?> clazz) {
        return findNeighborsAlongFacing(world, pos, state, clazz);
    }

    // Core Logic
    // ==========

    private BlockNeighbors findNeighborsAlongAxis(LevelAccessor world, BlockPos pos, BlockState state) {
        return findNeighborsAlongAxis(world, pos, state, null);
    }

    private BlockNeighbors findNeighborsAlongAxis(LevelAccessor world, BlockPos pos, BlockState state, Class<?> clazz) {
        Direction.Axis axis = state.getValue(AXIS);
        Direction[] directions = getDirectionsAlongAxis(axis);

        BlockState neighbor1 = world.getBlockState(pos.relative(directions[0]));
        BlockState neighbor2 = world.getBlockState(pos.relative(directions[1]));

        boolean matches1 = clazz != null
                ? isMatchingInstance(neighbor1, clazz, axis)
                : isMatchingBlock(neighbor1, state.getBlock(), axis);

        boolean matches2 = clazz != null
                ? isMatchingInstance(neighbor2, clazz, axis)
                : isMatchingBlock(neighbor2, state.getBlock(), axis);

        return new BlockNeighbors(matches1 ? neighbor1 : null, matches2 ? neighbor2 : null);
    }

    private BlockNeighbors findNeighborsAlongFacing(LevelAccessor world, BlockPos pos, BlockState state) {
        return findNeighborsAlongFacing(world, pos, state, null);
    }

    private BlockNeighbors findNeighborsAlongFacing(LevelAccessor world, BlockPos pos, BlockState state, Class<?> clazz) {
        Direction facing = state.getValue(FACING);
        Direction.Axis checkAxis = getCheckAxisForFacing(facing);
        boolean shouldInvert = shouldInvertDirection(facing);

        BlockState neighbor1 = world.getBlockState(pos.relative(checkAxis, shouldInvert ? 1 : -1));
        BlockState neighbor2 = world.getBlockState(pos.relative(checkAxis, shouldInvert ? -1 : 1));

        boolean matches1 = clazz != null
                ? isMatchingInstance(neighbor1, clazz, facing)
                : isMatchingBlock(neighbor1, state.getBlock(), facing);

        boolean matches2 = clazz != null
                ? isMatchingInstance(neighbor2, clazz, facing)
                : isMatchingBlock(neighbor2, state.getBlock(), facing);

        return new BlockNeighbors(matches1 ? neighbor1 : null, matches2 ? neighbor2 : null);
    }

    // Helper Methods
    // ==============

    private Part determineConnectionType(BlockNeighbors neighbors, Direction.Axis axis) {
        boolean hasFirst = neighbors.first() != null;
        boolean hasSecond = neighbors.second() != null;

        if (hasFirst && hasSecond) return Part.MIDDLE;
        if (hasFirst) return axis == Direction.Axis.Y ? Part.BOTTOM : Part.RIGHT;
        if (hasSecond) return axis == Direction.Axis.Y ? Part.TOP : Part.LEFT;
        return Part.SINGLE;
    }

    private boolean isThisBlock(@NotNull BlockState state, Block block) {
        return state.getBlock() == block;
    }

    private boolean isInstanceOfBlock(@NotNull BlockState state, Class<?> clazz) {
        return clazz.isInstance(state.getBlock());
    }

    private boolean isMatchingBlock(BlockState state, Block block, Direction.Axis axis) {
        return isThisBlock(state, block) && state.getValue(AXIS) == axis;
    }

    private boolean isMatchingBlock(BlockState state, Block block, Direction facing) {
        return isThisBlock(state, block) && state.getValue(FACING) == facing;
    }

    private boolean isMatchingInstance(BlockState state, Class<?> clazz, Direction.Axis axis) {
        return isInstanceOfBlock(state, clazz) && state.getValue(AXIS) == axis;
    }

    private boolean isMatchingInstance(BlockState state, Class<?> clazz, Direction facing) {
        return isInstanceOfBlock(state, clazz) && state.getValue(FACING) == facing;
    }

    private Direction.Axis getCheckAxisForFacing(Direction facing) {
        return switch (facing) {
            case NORTH, SOUTH -> Direction.Axis.X;
            case EAST, WEST -> Direction.Axis.Z;
            case UP, DOWN -> Direction.Axis.Y;
        };
    }

    private boolean shouldInvertDirection(Direction facing) {
        return facing == Direction.SOUTH || facing == Direction.WEST || facing == Direction.DOWN;
    }

    private Direction @NotNull [] getDirectionsAlongAxis(Direction.Axis axis) {
        return axis == Direction.Axis.Y
                ? new Direction[]{Direction.UP, Direction.DOWN}
                : new Direction[]{
                Direction.fromAxisAndDirection(axis, Direction.AxisDirection.NEGATIVE),
                Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE)
        };
    }
}