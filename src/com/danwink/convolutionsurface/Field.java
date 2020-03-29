package com.danwink.convolutionsurface;
public class Field
{
	public Vector min, max;
	public float res;
	public Float[][][] field;
	public int xSize, ySize, zSize;
	
	public Field( Vector min, Vector max, float res )
	{
		this.min = min;
		this.max = max;
		this.res = res;
		
		this.xSize = (int)(( max.x - min.x ) / res) + 1;
		this.ySize = (int)(( max.y - min.y ) / res) + 1;
		this.zSize = (int)(( max.z - min.z ) / res) + 1;
	}
}
