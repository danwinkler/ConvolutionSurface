package com.danwink.convolutionsurface;
import java.util.List;
import java.util.stream.IntStream;

import com.danwink.convolutionsurface.primitive.Primitive;

public class FieldGenerator
{
	List<Primitive> primitives;
	Field f;
	
	public FieldGenerator( Vector min, Vector max, float res )
	{
		this.f = new Field( min, max, res );
	}
	
	public void addPrimitive( Primitive p )
	{
		primitives.add( p );
	}
	
	public Field generate()
	{	
		f.field = 
			IntStream.range( 0, f.zSize ).parallel()
			.mapToObj( z -> 
				IntStream.range( 0, f.ySize ).parallel()
				.mapToObj( y -> 
					IntStream.range( 0, f.xSize ).parallel()
					.mapToObj( x ->	
						(float)primitives.stream().mapToDouble( p -> {
							return fixReturn( p.compute( new Vector( x * f.res + f.min.x, y * f.res + f.min.y, z * f.res + f.min.z ) ) );
						} ).sum() 
					).toArray( Float[]::new )
				).toArray( Float[][]::new )
			).toArray( Float[][][]::new );
		
		return f;
	}
	
	private static float fixReturn( float v )
	{
		if( Float.isNaN( v ) ) return 0;
		if( Float.isInfinite( v ) ) return Float.MAX_VALUE;
		return v;
	}
	
	public static Field getField( List<Primitive> primitives, Vector min, Vector max, float res )
	{
		FieldGenerator fg = new FieldGenerator( min, max, res );
		fg.primitives = primitives;
		return fg.generate();
	}
}
