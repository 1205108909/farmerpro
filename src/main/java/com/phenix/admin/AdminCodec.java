package com.phenix.admin;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.apache.mina.filter.codec.demux.MessageEncoder;

/**
 * Created by yangfei on 2015/10/13.
 * Description: This class is a combination of Message Encoder and Decoder of Admin command.
 */
public class AdminCodec implements MessageDecoder, MessageEncoder<String>{

    private static final String EMPTY_STRING = "";
    private static final String ENCODING = "UTF-8";
    private static final String END_OF_COMMAND = "\n\r";

    /**
     * Encode string and write the string out.
     */
    @Override
    public void encode(IoSession session, String message, ProtocolEncoderOutput out)
            throws Exception {
        String value = (message == null ? EMPTY_STRING : message.toString());
        IoBuffer buf = IoBuffer.allocate(value.length()).setAutoExpand(true);
        Charset charSet = Charset.forName(ENCODING);
        CharsetEncoder encoder = charSet.newEncoder();

        buf.putString(value, encoder);
        buf.putString(END_OF_COMMAND, encoder);
        buf.flip();
        out.write(buf);
        out.flush();
    }

    @Override
    public MessageDecoderResult decodable(IoSession arg0, IoBuffer arg1) {
        if (arg1.remaining() <= 0) {
            return MessageDecoderResult.NOT_OK;
        }
        return MessageDecoderResult.OK;
    }

    @Override
    public MessageDecoderResult decode(IoSession arg0, IoBuffer ioBuffer,
                                       ProtocolDecoderOutput decoderOuput) throws Exception {
        byte[] buffer = new byte[ioBuffer.limit()];
        ioBuffer.get(buffer);
        String string = new String(buffer);
        string = string.trim();
        decoderOuput.write(string);
        return MessageDecoderResult.OK;
    }

    @Override
    public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1)
            throws Exception {
    }
}
