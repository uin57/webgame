package com.pwrd.war.core.codec;


import java.nio.charset.Charset;

import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class GameLineCodecFactory implements ProtocolCodecFactory {

    private final GameLineEncoder encoder;
    private final GameLineDecoder decoder;

    public GameLineCodecFactory()
    {
        this( Charset.defaultCharset() );
    }
    
    /**
     * Creates a new instance with the specified {@link Charset}.
     */
    public GameLineCodecFactory( Charset charset )
    {
        encoder = new GameLineEncoder( charset );
        decoder = new GameLineDecoder( charset );
    }

    public ProtocolDecoder getDecoder() throws Exception {
        return decoder;
	}

	public ProtocolEncoder getEncoder() throws Exception {
        return encoder;
	}

    public int getEncoderMaxLineLength()
    {
        return encoder.getMaxLineLength();
    }
    
    public void setEncoderMaxLineLength( int maxLineLength )
    {
        encoder.setMaxLineLength( maxLineLength );
    }
    
    public int getDecoderMaxLineLength()
    {
        return decoder.getMaxLineLength();
    }
    
    public void setDecoderMaxLineLength( int maxLineLength )
    {
        decoder.setMaxLineLength( maxLineLength );
    }

}
