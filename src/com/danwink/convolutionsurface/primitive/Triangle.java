package com.danwink.convolutionsurface.primitive;

import com.danwink.convolutionsurface.Vector;

public class Triangle implements Primitive
{
	public Vector a, b, c;
	
	public Triangle()
	{
		
	}
	
	public Triangle( Vector a, Vector b, Vector c )
	{
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public float compute( Vector v )
	{
		throw new UnsupportedOperationException( "Not yet implemented." );
	}
}
