package salted.packedup.common.block.handlers;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Collection;

public class ShapeHandler {

    // huge thanks to Mr.Crayfish for this method
    public VoxelShape combineAll(Collection<VoxelShape> shapes) {
        VoxelShape result = Shapes.empty();
        for(VoxelShape shape : shapes) {
            result = Shapes.joinUnoptimized(result, shape, BooleanOp.OR);
        }
        return result.optimize();
    }

    public VoxelShape[] getRotated(VoxelShape shape) {
        VoxelShape SHAPE_NORTH = rotate(shape, Direction.NORTH);
        VoxelShape SHAPE_EAST = rotate(shape, Direction.EAST);
        VoxelShape SHAPE_SOUTH = rotate(shape, Direction.SOUTH);
        VoxelShape SHAPE_WEST = rotate(shape, Direction.WEST);
        return new VoxelShape[] { SHAPE_SOUTH, SHAPE_WEST, SHAPE_NORTH, SHAPE_EAST };
    }

    public VoxelShape rotate(VoxelShape shape, Direction direction) {
        double[] adjustedValues = adjustValues(direction, shape.min(Direction.Axis.X), shape.min(Direction.Axis.Z), shape.max(Direction.Axis.X), shape.max(Direction.Axis.Z));
        return Shapes.box(adjustedValues[0], shape.min(Direction.Axis.Y), adjustedValues[1], adjustedValues[2], shape.max(Direction.Axis.Y), adjustedValues[3]);
    }

    private static double[] adjustValues(Direction direction, double x1, double z1, double x2, double z2) {
        switch (direction) {
            case WEST -> {
                double tmpX = x1;
                x1 = 1.0F - x2;
                double tmpZ = z1;
                z1 = 1.0F - z2;
                x2 = 1.0F - tmpX;
                z2 = 1.0F - tmpZ;
            }
            case NORTH -> {
                double tmpX = x1;
                x1 = z1;
                z1 = 1.0F - x2;
                x2 = z2;
                z2 = 1.0F - tmpX;
            }
            case SOUTH -> {
                double tmpX1 = x1;
                x1 = 1.0F - z2;
                double tmpZ = z1;
                z1 = tmpX1;
                double tmpX2 = x2;
                x2 = 1.0F - tmpZ;
                z2 = tmpX2;
            }
        }
        return new double[] { x1, z1, x2, z2 };
    }
}
