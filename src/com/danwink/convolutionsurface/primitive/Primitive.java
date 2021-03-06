package com.danwink.convolutionsurface.primitive;

import com.danwink.convolutionsurface.Vector;

public interface Primitive
{
	public float compute( Vector v );
	
	public Vector getMin();
	public Vector getMax();
}
