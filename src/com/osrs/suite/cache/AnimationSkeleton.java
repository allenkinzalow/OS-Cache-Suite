package com.osrs.suite.cache;

import com.alex.io.InputStream;

/**
 * Created by Allen Kinzalow on 3/14/2015.
 */
public class AnimationSkeleton {

    public static final int INDEX_ID = 0;

    public int skeletonFrameCount;
    public int[] translator_x;
    static int[] alter_x = new int[500];
    static int[] alter_y = new int[500];
    public AnimationSkin skin = null;
    static int[] instructions = new int[500];
    public int stepCount = -1;
    public int[] opCodeTable;
    public int[] translator_y;
    static int[] alter_z = new int[500];
    public int[] translator_z;
    boolean hasAlpha = false;
    public int[] settings = new int[500];

    AnimationSkeleton(byte[] data, AnimationSkin skin) {
        this.skin = skin;
        InputStream settingsBuffer = new InputStream(data);
        InputStream buffer = new InputStream(data);
        settingsBuffer.setOffset(2);
        skeletonFrameCount = settingsBuffer.readUnsignedByte();
        int var10 = -1;
        int stepCount = 0;
        buffer.setOffset(settingsBuffer.getOffset() + skeletonFrameCount);

        int skeletonFrame;
        for(skeletonFrame = 0; skeletonFrame < skeletonFrameCount; ++skeletonFrame) {
            int setting = settingsBuffer.readUnsignedByte();
            settings[skeletonFrame] = setting;
            if(setting > 0) {
                if(this.skin.transformationTypes[skeletonFrame] != 0) { // set reference point
                    for(int var4 = skeletonFrame - 1; var4 > var10; --var4) {
                        if(this.skin.transformationTypes[var4] == 0) {
                            instructions[stepCount] = var4;
                            alter_x[stepCount] = 0;
                            alter_y[stepCount] = 0;
                            alter_z[stepCount] = 0;
                            ++stepCount;
                            break;
                        }
                    }
                }

                instructions[stepCount] = skeletonFrame;
                short var11 = 0;
                if(this.skin.transformationTypes[skeletonFrame] == 3) {
                    var11 = 128;
                }

                if((setting & 1) != 0) {
                    alter_x[stepCount] = readBS(buffer);
                } else {
                    alter_x[stepCount] = var11;
                }

                if((setting & 2) != 0) {
                    alter_y[stepCount] = readBS(buffer);
                } else {
                    alter_y[stepCount] = var11;
                }

                if((setting & 4) != 0) {
                    alter_z[stepCount] = readBS(buffer);
                } else {
                    alter_z[stepCount] = var11;
                }

                var10 = skeletonFrame;
                ++stepCount;
                if(this.skin.transformationTypes[skeletonFrame] == 5) {
                    this.hasAlpha = true;
                }
            }
        }

        if((data.length - buffer.getRemaining()) != data.length) {
            throw new RuntimeException();
        } else {
            this.stepCount = stepCount;
            this.opCodeTable = new int[stepCount];
            this.translator_x = new int[stepCount];
            this.translator_y = new int[stepCount];
            this.translator_z = new int[stepCount];

            for(skeletonFrame = 0; skeletonFrame < stepCount; ++skeletonFrame) {
                this.opCodeTable[skeletonFrame] = instructions[skeletonFrame];
                this.translator_x[skeletonFrame] = alter_x[skeletonFrame];
                this.translator_y[skeletonFrame] = alter_y[skeletonFrame];
                this.translator_z[skeletonFrame] = alter_z[skeletonFrame];
            }

        }
    }

    public int readBS(InputStream stream) {
        int var2 = stream.readUnsignedByte();
        return var2 < 128 ? stream.readUnsignedByte() - 64 : stream.readUnsignedShort() - '\uc000';
    }

}
