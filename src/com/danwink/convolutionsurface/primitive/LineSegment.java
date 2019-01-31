package com.danwink.convolutionsurface.primitive;

import com.danwink.convolutionsurface.Vector;

public class LineSegment implements Primitive
{
	Vector p0, p1;
	Vector v, vn;
	float l, l2;
	float s = 2;
	float s2 = s*s;
	
	public LineSegment( Vector p0, Vector p1, float s )
	{
		this.p0 = p0;
		this.p1 = p1;
		
		this.s = s;
		
		updateCachedValues();
	}
	
	public void updateCachedValues()
	{
		v = new Vector();
		v.sub( p1, p0 );
		
		l = v.length();
		l2 = l*l;
		
		vn = new Vector( v );
		vn.scale( 1.f / l );
		
		this.s2 = s*s;
	}
	
	public void normalize()
	{
		this.p1.set( p0 );
		this.p1.add( vn );
		updateCachedValues();
	}
	
	public Vector getVector()
	{
		return v;
	}
	
	public Vector getP0()
	{
		return p0;
	}
	
	public Vector getP1()
	{
		return p1;
	}
	
	public void setP0( Vector v )
	{
		this.p0 = v;
		this.updateCachedValues();
	}
	
	public void setP1( Vector v )
	{
		this.p1 = v;
		this.updateCachedValues();
	}
	
	public Vector getMin()
	{
		return new Vector( Math.min( p0.x, p1.x ), Math.min( p0.y, p1.y ), Math.min( p0.z, p1.z ) );
	}
	
	public Vector getMax()
	{
		return new Vector( Math.max( p0.x, p1.x ), Math.max( p0.y, p1.y ), Math.max( p0.z, p1.z ) );
	}
	
	public float compute( Vector p )
	{
		Vector d = new Vector( p );
		d.sub( p0 );
		
		float dl2 = d.lengthSquared();
		
		float x = d.dot( vn );
		float x2 = x*x;
		float p2 = 1 + s2 * (dl2 - x2);
		float pl = (float)Math.sqrt( p2 );
		float q2 = 1 + s2 * (dl2 + l2 - 2*l*x);
		
		float t1 = x / (2*p2 * (p2 + s2*x2));
		float t2 = (l-x)/(2*p2*q2);
		float t3 = (1/(2*s*p2*pl)) * (float)(Math.atan((s*x)/pl) + Math.atan((s*(l-x))/pl));
		
		return t1 + t2 + t3;
	}
}
