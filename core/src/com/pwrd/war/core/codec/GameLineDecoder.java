package com.pwrd.war.core.codec;



import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.common.BufferDataException;
import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class GameLineDecoder implements ProtocolDecoder {

    private static final String CONTEXT = GameLineDecoder.class.getName() + ".context";
    private final Charset charset;
    private ByteBuffer delimBuf;
    private int maxLineLength = 1024*16;
    
    public GameLineDecoder(){
    	this(Charset.defaultCharset());
    }
    
    public GameLineDecoder(Charset charset){
    	if(charset == null){
    		throw new NullPointerException("charset");
    	}
    	this.charset = charset;
    }

    public int getMaxLineLength()
    {
        return maxLineLength;
    }

    public void setMaxLineLength( int maxLineLength )
    {
        if( maxLineLength <= 0 )
        {
            throw new IllegalArgumentException( "maxLineLength: " + maxLineLength );
        }
        
        this.maxLineLength = maxLineLength;
    }
    
    public void decode(IoSession session, ByteBuffer in,
			ProtocolDecoderOutput out) throws Exception {
        Context ctx = getContext(session);
        ctx.setMatchCount(
                decodeNormal(
                        in,
                        ctx.getBuffer(),
                        ctx.getMatchCount(),
                        ctx.getDecoder(),
                        out ) );

    	
	}

    private Context getContext(IoSession session) {
        Context ctx;
        ctx = ( Context ) session.getAttribute( CONTEXT );
        if( ctx == null )
        {
            ctx = new Context();
            session.setAttribute( CONTEXT, ctx );
        }
        return ctx;
    }
    
    public void dispose( IoSession session ) throws Exception
    {
        Context ctx = ( Context ) session.getAttribute( CONTEXT );
        if( ctx != null )
        {
            ctx.getBuffer().release();
            session.removeAttribute( CONTEXT );
        }
    }

    public void finishDecode( IoSession session, ProtocolDecoderOutput out ) throws Exception
    {
    }

    private int decodeNormal( ByteBuffer in, ByteBuffer buf, int matchCount, CharsetDecoder decoder, ProtocolDecoderOutput out ) throws CharacterCodingException
    {
        // Convert delimiter to ByteBuffer if not done yet.
        if( delimBuf == null )
        {
            ByteBuffer tmp = ByteBuffer.allocate( 1 ).setAutoExpand( true );
            tmp.put((byte)0);
//            tmp.putString( delimiter.getValue(), charset.newEncoder() );
            tmp.flip();
            delimBuf = tmp;
        }
        
        // Try to find a match
        int oldPos = in.position();
        int oldLimit = in.limit();
        while( in.hasRemaining() )
        {
            byte b = in.get();
            if( delimBuf.get( matchCount ) == b )
            {
                matchCount ++;
                if( matchCount == delimBuf.limit() )
                {
                    // Found a match.
                    int pos = in.position();
                    in.limit( pos );
                    in.position( oldPos );
                    
                    buf.put( in );
                    if( buf.position() > maxLineLength )
                    {
                        throw new BufferDataException( "Line is too long: " + buf.position() );
                    }
                    buf.flip();
                    buf.limit( buf.limit() - matchCount );
                    out.write( buf.getString( decoder ) );
                    buf.clear();
                    
                    in.limit( oldLimit );
                    in.position( pos );
                    oldPos = pos;
                    matchCount = 0;
                }
            }
            else
            {
                matchCount = 0;
            }
        }
        
        // Put remainder to buf.
        in.position( oldPos );
        buf.put( in );
        
        return matchCount;
    }

    private class Context
    {
        private final CharsetDecoder decoder;
        private final ByteBuffer buf;
        private int matchCount = 0;
        
        private Context()
        {
            decoder = charset.newDecoder();
            buf = ByteBuffer.allocate( 80 ).setAutoExpand( true );
        }
        
        public CharsetDecoder getDecoder()
        {
            return decoder;
        }
        
        public ByteBuffer getBuffer()
        {
            return buf;
        }
        
        public int getMatchCount()
        {
            return matchCount;
        }
        
        public void setMatchCount( int matchCount )
        {
            this.matchCount = matchCount;
        }
    }

}
