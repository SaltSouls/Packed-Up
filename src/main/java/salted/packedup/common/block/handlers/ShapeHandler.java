package salted.packedup.common.block.handlers;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Collection;

public class ShapeHandler {

    /**
     * Combines multiple VoxelShapes into a single VoxelShape using the {@link BooleanOp}.OR operation.
     * This is useful for merging multiple collision or selection shapes into one.
     *
     * @param shapes A collection of VoxelShapes to be combined.
     * @return A single {@link VoxelShape} representing the union of all input shapes.
     */
    public VoxelShape combineAll(Collection<VoxelShape> shapes) {
        return shapes.stream()
                .reduce(Shapes.empty(), (result, shape) -> Shapes.joinUnoptimized(result, shape, BooleanOp.OR))
                .optimize();
    }

    /**
     * Returns an array of VoxelShapes, each representing the input shape rotated to face a cardinal direction.
     * The order of the returned shapes is: SOUTH, WEST, NORTH, EAST.
     *
     * @param shape The {@link VoxelShape} to rotate.
     * @return An array of VoxelShapes, each rotated to face a different cardinal {@link Direction}.
     */
    public VoxelShape[] getRotated(VoxelShape shape) {
        return new VoxelShape[] {
                rotate(shape, Direction.SOUTH),
                rotate(shape, Direction.WEST),
                rotate(shape, Direction.NORTH),
                rotate(shape, Direction.EAST)
        };
    }

    /**
     * Rotates a VoxelShape to face the specified direction.
     * The rotation is performed by adjusting the X and Z coordinates of the shape.
     *
     * @param shape     The {@link VoxelShape} to rotate.
     * @param direction The {@link Direction} to rotate the shape to face.
     * @return A new {@link VoxelShape} representing the rotated shape.
     */
    public VoxelShape rotate(VoxelShape shape, Direction direction) {
        double[] adjustedValues = adjustValues(direction, shape.min(Direction.Axis.X), shape.min(Direction.Axis.Z), shape.max(Direction.Axis.X), shape.max(Direction.Axis.Z));
        return Shapes.box(adjustedValues[0], shape.min(Direction.Axis.Y), adjustedValues[1], adjustedValues[2], shape.max(Direction.Axis.Y), adjustedValues[3]);
    }

    /**
     * Adjusts the X and Z coordinates of a VoxelShape based on the specified rotation direction.
     * This is a helper method used by the `rotate` function.
     *
     * @param direction The {@link Direction} to rotate the shape to face.
     * @param x1        The minimum X coordinate of the shape.
     * @param z1        The minimum Z coordinate of the shape.
     * @param x2        The maximum X coordinate of the shape.
     * @param z2        The maximum Z coordinate of the shape.
     * @return An array of adjusted coordinates: [minX, minZ, maxX, maxZ].
     */
    private static double[] adjustValues(Direction direction, double x1, double z1, double x2, double z2) {
        return switch (direction) {
            case WEST -> new double[] { 1.0 - x2, 1.0 - z2, 1.0 - x1, 1.0 - z1 };
            case NORTH -> new double[] { z1, 1.0 - x2, z2, 1.0 - x1 };
            case SOUTH -> new double[] { 1.0 - z2, x1, 1.0 - z1, x2 };
            default -> new double[] { x1, z1, x2, z2 }; // EAST or no rotation
        };
    }
}
