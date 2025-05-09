package salted.packedup.common.block.handlers;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Collection;

public class ShapeHandler {

    // Rotation Methods
    // ================

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

    public VoxelShape[] getRotations(VoxelShape shape, boolean axis, boolean allRotations) {
        if (axis && allRotations) { return getShapes(shape, Direction.Axis.values()); }
        else if (axis) { return getShapes(shape, new Direction.Axis[] { Direction.Axis.X, Direction.Axis.Z }); }
        else if (allRotations) { return getShapes(shape, Direction.values()); }
        return getShapes(shape, new Direction[] { Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST });
    }

    // Rotation Logic
    // ==============

    public <T> VoxelShape rotate(VoxelShape shape, T type) {
        double[] bounds = getShapeBounds(shape);
        double[] adjusted;

        if (type instanceof Direction.Axis axis) { adjusted = adjustCoordinates(bounds, axis); }
        else if (type instanceof Direction dir) { adjusted = adjustCoordinates(bounds, dir); }
        else { return shape; }

        return Shapes.create(adjusted[0], adjusted[1], adjusted[2],
                adjusted[3], adjusted[4], adjusted[5]);
    }

    // Helpers Methods
    // ===============

    private <T> VoxelShape[] getShapes(VoxelShape shape, T[] array) {
        VoxelShape[] results = new VoxelShape[array.length];
        for (int i = 0; i < array.length; i++) {
            if (array instanceof Direction.Axis[] axes) {
                results[i] = rotate(shape, axes[i]);
            }
            else if (array instanceof Direction[] dirs) {
                results[i] = rotate(shape, dirs[i]);
            }
        }
        return results;
    }

    private static double[] getShapeBounds(VoxelShape shape) {
        return new double[] {
                shape.min(Direction.Axis.X), shape.min(Direction.Axis.Y), shape.min(Direction.Axis.Z),
                shape.max(Direction.Axis.X), shape.max(Direction.Axis.Y), shape.max(Direction.Axis.Z)
        };
    }

    private static <T> double[] adjustCoordinates(double[] bounds, T type) {
        double x1 = bounds[0], y1 = bounds[1], z1 = bounds[2];
        double x2 = bounds[3], y2 = bounds[4], z2 = bounds[5];

        if (type instanceof Direction dir) {
            return switch (dir) {
                case DOWN ->  new double[]{x1, 1-z2, y1, x2, 1-z1, y2};
                case UP ->    new double[]{x1, z1, y1, x2, z2, y2};
                case NORTH -> new double[]{z1, y1, 1-x2, z2, y2, 1-x1};
                case SOUTH -> new double[]{1-z2, y1, x1, 1-z1, y2, x2};
                case WEST ->  new double[]{1-x2, y1, 1-z2, 1-x1, y2, 1-z1};
                case EAST ->  new double[]{x1, y1, z1, x2, y2, z2};
            };
        } else if (type instanceof Direction.Axis axis) {
            return switch (axis) {
                case X -> new double[]{y1, z1, x1, y2, z2, x2};
                case Y -> new double[]{x1, z1, y1, x2, z2, y2};
                case Z -> new double[]{x1, y1, z1, x2, y2, z2};
            };
        }
        return bounds;
    }
}