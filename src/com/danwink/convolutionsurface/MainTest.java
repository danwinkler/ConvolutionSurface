package com.danwink.convolutionsurface;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;

import com.danwink.convolutionsurface.primitive.LineSegment;
import com.danwink.convolutionsurface.primitive.Primitive;
import com.danwink.convolutionsurface.primitive.Triangle;

public class MainTest
{
	public static void main( String[] args )
	{
		ArrayList<Primitive> prims = new ArrayList<>();
		prims.add( new LineSegment( new Vector( 0, 0, 0 ), new Vector( 1, 1, 1 ), 10f ) );
		Field field = FieldGenerator.getField( prims, new Vector( -2, -2, -2 ), new Vector( 3, 3, 3 ), .03f );
		
		for( int z = 0; z < field.zSize; z++ )
		{
			for( int y = 0; y < field.ySize; y++ )
			{
				for( int x = 0; x < field.zSize; x++ )
				{
					//System.out.println( "(" + x + ", " + y + ", " + z + ") = " + field.field[z][y][x] );
				} 
			}
		}
		
		MarchingCubesPolygonizer mcp = new MarchingCubesPolygonizer();
		ArrayList<Triangle> tris = mcp.polygonize( field, .1f );
		saveTriangles( tris, "test.scad" );
	}
	
	public static void saveTriangles( ArrayList<Triangle> tris, String file )
	{
		ArrayList<Vector> points = new ArrayList<Vector>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		for( Triangle t : tris )
		{
			indices.add( points.size() );
			indices.add( points.size()+1 );
			indices.add( points.size()+2 );
			points.add( t.a );
			points.add( t.b );
			points.add( t.c );
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append( "polyhedron( points=[" );
		for( int i = 0; i < points.size(); i++ )
		{
			Vector p = points.get( i );
			sb.append( "[" + p.x + "," + p.y + "," + p.z + "]" );
			if( i+1 < points.size() )
			{
				sb.append( "," );
			}
		}
		sb.append( "],  faces=[" );
		for( int i = 0; i < indices.size(); i += 3 )
		{
			sb.append( "[" + indices.get( i ) + "," + indices.get( i+1 ) + "," + indices.get( i+2 ) + "]" );
			if( i+4 < points.size() )
			{
				sb.append( "," );
			}
		}
		sb.append( "]);" );
		
		try 
		{
			Files.write( FileSystems.getDefault().getPath( file ), sb.toString().getBytes() );
		} 
		catch( IOException e ) 
		{
			e.printStackTrace();
		}
	}
}
