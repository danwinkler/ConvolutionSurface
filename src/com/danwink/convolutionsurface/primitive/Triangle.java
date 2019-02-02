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
	
	public Vector getNormal()
	{
		Vector d1 = new Vector( b );
		d1.sub( a );
		d1.normalize();
		
		Vector d2 = new Vector( c );
		d1.sub( a );
		d2.normalize();
		
		return d1.cross( d2 );
	}

	public float compute( Vector v )
	{
		throw new UnsupportedOperationException( "Not yet implemented." );
	}

	public Vector getMin()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Vector getMax()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
