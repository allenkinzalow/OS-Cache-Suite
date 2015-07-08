package com.osrs.suite.cache;

import com.alex.io.InputStream;
import com.alex.io.OutputStream;

/**
 * Created by Allen Kinzalow on 3/14/2015.
 */
public class AnimationSkin {

    public static final int INDEX_ID = 1;

    public int count;
    public int skinId;
    public int[][] skinList;
    public int[] transformationTypes;

    AnimationSkin(int id, byte[] skinData) {
        InputStream stream = new InputStream(skinData);
        this.skinId = id;
        this.count = stream.readUnsignedByte();
        this.transformationTypes = new int[this.count];
        this.skinList = new int[this.count][];

        for(int opcode = 0; opcode < this.count; ++opcode) {
            this.transformationTypes[opcode] = stream.readUnsignedByte();
        }

        for(int skin = 0; skin < this.count; ++skin) {
            this.skinList[skin] = new int[stream.readUnsignedByte()];
        }

        for(int skin = 0; skin < this.count; ++skin) {
            for(int subSkin = 0; subSkin < this.skinList[skin].length; ++subSkin) {
                this.skinList[skin][subSkin] = stream.readUnsignedByte();
            }
        }

    }

    public OutputStream encode() {
        OutputStream buffer = new OutputStream(100000);
        buffer.writeShort(count);
        for(int tt = 0; tt < count; tt++) {
            buffer.writeShort(transformationTypes[tt]);
        }
        for(int skinCount = 0; skinCount < this.count; skinCount++) {
            buffer.writeShort(skinList[skinCount].length);
        }
        for(int skin = 0; skin < this.count; ++skin) {
            for(int subSkin = 0; subSkin < this.skinList[skin].length; ++subSkin) {
                buffer.writeShort(this.skinList[skin][subSkin]);
            }
        }
        return buffer;
    }

}
