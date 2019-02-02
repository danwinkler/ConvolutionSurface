package com.danwink.convolutionsurface;
import java.util.List;
import java.util.stream.IntStream;

import com.danwink.convolutionsurface.primitive.Primitive;

public class FieldGenerator
{
	List<Primitive> primitives;
	Field f;
	
	int progress;
	
	public FieldGenerator( Vector min, Vector max, float res )
	{
		this.f = new Field( min, max, res );
	}
	
	public void addPrimitive( Primitive p )
	{
		primitives.add( p );
	}
	
	public void setPrimitives( List<Primitive> primitives )
	{
		this.primitives = primitives;
	}
	
	public Field generate()
	{	
		f.field = 
			IntStream.range( 0, f.zSize ).parallel()
			.mapToObj( z -> {
				Float[][] r = IntStream.range( 0, f.ySize ).parallel()
				.mapToObj( y -> 
					IntStream.range( 0, f.xSize ).parallel()
					.mapToObj( x ->	
						(float)primitives.stream().mapToDouble( p -> {
							return fixReturn( p.compute( new Vector( x * f.res + f.min.x, y * f.res + f.min.y, z * f.res + f.min.z ) ) );
						} ).sum() 
					).toArray( Float[]::new )
				).toArray( Float[][]::new );
				progress++;
				return r;
			}).toArray( Float[][][]::new );
		
		return f;
	}
	
	private static float fixReturn( float v )
	{
		if( Float.isNaN( v ) ) return 0;
		if( Float.isInfinite( v ) ) return Float.MAX_VALUE;
		return v;
	}
	
	public float getProgress()
	{
		return (float)progress/(float)f.zSize;
	}
	
	public static Field getField( List<Primitive> primitives, Vector min, Vector max, float res )
	{
		FieldGenerator fg = new FieldGenerator( min, max, res );
		fg.setPrimitives( primitives );
		return fg.generate();
	}
}
