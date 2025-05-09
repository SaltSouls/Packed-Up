package salted.packedup.common.block.handlers;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.*;

import java.util.Collection;
import java.util.stream.Stream;

public class ShapeHandler {

    /**
     * Combines multiple VoxelShapes into a single VoxelShape using the {@link BooleanOp}.OR operation.
     * This is useful for merging multiple collision or selection shapes into one.
     *
     * @param shapes A collection of VoxelShapes to be combined
     * @return A single {@link VoxelShape} representing the union of all input shapes
     */
    public VoxelShape combineAll(Collection<VoxelShape> shapes) {
        return shapes.stream()
                .reduce(Shapes.empty(), (r, s) -> Shapes.joinUnoptimized(r, s, BooleanOp.OR))
                .optimize();
    }

    /**
     * Generates an array of rotated VoxelShapes based on the specified parameters.
     *
     * @param shape The base {@link VoxelShape} to rotate
     * @param axis If true, generates axis-based rotations; if false, generates direction-based rotations
     * @param allRotations If true, generates all possible rotations; if false, generates only horizontal rotations
     * @return An array of {@link VoxelShape} containing the rotated variants
     */
    public VoxelShape[] getRotations(VoxelShape shape, boolean axis, boolean allRotations) {
        return Stream.of(axis ?
                        (allRotations ? Direction.Axis.values() : new Direction.Axis[]{Direction.Axis.X, Direction.Axis.Z}) :
                        (allRotations ? Direction.values() : new Direction[]{Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST}))
                .map(t -> rotate(shape, t))
                .toArray(VoxelShape[]::new);
    }

    // Core rotation logic
    // ==================

    private <T> VoxelShape rotate(VoxelShape shape, T rotationType) {
        AABB bounds = getShapeBounds(shape);
        AABB rotated = rotationType instanceof Direction dir ?
                rotateForDirection(bounds, dir) :
                rotationType instanceof Direction.Axis axis ?
                        rotateForAxis(bounds, axis) :
                        bounds;

        return Shapes.create(rotated.minX, rotated.minY, rotated.minZ,
                rotated.maxX, rotated.maxY, rotated.maxZ);
    }

    private AABB rotateForDirection(AABB bounds, Direction dir) {
        return switch (dir) {
            case DOWN -> flipY(bounds);
            case UP, NORTH -> bounds;
            case SOUTH -> flipZ(bounds);
            case WEST -> swapXZ(bounds);
            case EAST -> flipX(swapXZ(bounds));
        };
    }


    private AABB rotateForAxis(AABB bounds, Direction.Axis axis) {
        return switch (axis) {
            case X -> swapYZ(bounds);
            case Y -> bounds;
            case Z -> swapXY(bounds);
        };
    }

    // transformation helpers
    // ======================

    private AABB swapXY(AABB b) {
        return new AABB(b.minY, b.minX, b.minZ, b.maxY, b.maxX, b.maxZ);
    }

    private AABB swapXZ(AABB b) {
        return new AABB(b.minZ, b.minY, b.minX, b.maxZ, b.maxY, b.maxX);
    }

    private AABB swapYZ(AABB b) {
        return new AABB(b.minX, b.minZ, b.minY, b.maxX, b.maxZ, b.maxY);
    }

    private AABB flipX(AABB b) {
        return new AABB(1 - b.maxX, b.minY, b.minZ, 1 - b.minX, b.maxY, b.maxZ);
    }

    private AABB flipY(AABB b) {
        return new AABB(b.minX, 1 - b.maxY, b.minZ, b.maxX, 1 - b.minY, b.maxZ);
    }

    private AABB flipZ(AABB b) {
        return new AABB(b.minX, b.minY, 1 - b.maxZ, b.maxX, b.maxY, 1 - b.minZ);
    }

    // Utility method
    // ==============

    private static AABB getShapeBounds(VoxelShape shape) {
        return new AABB(
                shape.min(Direction.Axis.X),
                shape.min(Direction.Axis.Y),
                shape.min(Direction.Axis.Z),
                shape.max(Direction.Axis.X),
                shape.max(Direction.Axis.Y),
                shape.max(Direction.Axis.Z)
        );
    }
}