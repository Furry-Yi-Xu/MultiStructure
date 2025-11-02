package com.yixu.Model.StructureBlock;

public class StructureSubBlockData {
    public final int offsetX;
    public final int offsetY;
    public final int offsetZ;
    public final String material;

    public StructureSubBlockData(int offsetX, int offsetY, int offsetZ, String material) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.material = material;
    }
}
