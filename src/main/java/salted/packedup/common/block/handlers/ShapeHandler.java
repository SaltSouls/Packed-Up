package salted.packedup.common.block.handlers;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Collection;

public class ShapeHandler {

    // huge thanks to Mr. Crayfish for this method
    public static VoxelShape combineAll(Collection<VoxelShape> shapes) {
        VoxelShape result = Shapes.empty();
        for(VoxelShape shape : shapes) {
            result = Shapes.joinUnoptimized(result, shape, BooleanOp.OR);
        }
        return result.optimize();
    }

    public static VoxelShape[] getRotated(VoxelShape shape) {
        VoxelShape SHAPE_NORTH = rotate(shape, Direction.NORTH);
        VoxelShape SHAPE_EAST = rotate(shape, Direction.EAST);
        VoxelShape SHAPE_SOUTH = rotate(shape, Direction.SOUTH);
        VoxelShape SHAPE_WEST = rotate(shape, Direction.WEST);
        return new VoxelShape[] { SHAPE_SOUTH, SHAPE_WEST, SHAPE_NORTH, SHAPE_EAST };
    }

    public static VoxelShape rotate(VoxelShape shape, Direction direction) {
        double[] adjustedValues = adjustValues(direction, shape.min(Direction.Axis.X), shape.min(Direction.Axis.Z), shape.max(Direction.Axis.X), shape.max(Direction.Axis.Z));
        return Shapes.box(adjustedValues[0], shape.min(Direction.Axis.Y), adjustedValues[1], adjustedValues[2], shape.max(Direction.Axis.Y), adjustedValues[3]);
    }

    private static double[] adjustValues(Direction direction, double var1, double var2, double var3, double var4) {
        switch (direction) {
            case WEST -> {
                double var_temp_1 = var1;
                var1 = 1.0F - var3;
                double var_temp_2 = var2;
                var2 = 1.0F - var4;
                var3 = 1.0F - var_temp_1;
                var4 = 1.0F - var_temp_2;
            }
            case NORTH -> {
                double var_temp_3 = var1;
                var1 = var2;
                var2 = 1.0F - var3;
                var3 = var4;
                var4 = 1.0F - var_temp_3;
            }
            case SOUTH -> {
                double var_temp_4 = var1;
                var1 = 1.0F - var4;
                double var_temp_5 = var2;
                var2 = var_temp_4;
                double var_temp_6 = var3;
                var3 = 1.0F - var_temp_5;
                var4 = var_temp_6;
            }
            default -> {
            }
        }
        return new double[]{var1, var2, var3, var4};
    }
}
