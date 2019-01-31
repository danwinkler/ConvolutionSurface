package com.danwink.convolutionsurface;

import java.util.List;

import com.danwink.convolutionsurface.primitive.Primitive;

public class Bounds
{
	public Vector min = new Vector();
	public Vector max = new Vector();
	
	private Bounds()
	{
		
	}
	
	public static Bounds generateFromPrimitiveList( List<Primitive> prims )
	{
		Bounds b = new Bounds();
		
		for( Primitive p : prims )
		{
			Vector min = p.getMin();
			Vector max = p.getMax();
			
			b.min.x = Math.min( min.x, b.min.x );
			b.min.y = Math.min( min.y, b.min.y );
			b.min.z = Math.min( min.z, b.min.z );
			
			b.max.x = Math.max( max.x, b.max.x );
			b.max.y = Math.max( max.y, b.max.y );
			b.max.z = Math.max( max.z, b.max.z );
		}
		
		return b;
	}
}
