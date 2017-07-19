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
		
		v = new Vector();
		v.sub( p1, p0 );
		
		l = v.length();
		l2 = l*l;
		
		vn = new Vector( v );
		vn.scale( 1.f / l );
		
		this.s = s;
		this.s2 = s*s;
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
