package com.osrs.suite.cache.definitions;

import com.alex.io.InputStream;
import com.alex.io.OutputStream;
import com.alex.store.Store;
import com.osrs.suite.cache.AnimationSkeletonSet;
import com.osrs.suite.cache.renderable.model.ModelRasterizer;

/**
 * Created by Allen Kinzalow on 3/14/2015.
 */
public class AnimationDefinition extends Definition {

    public static int INDEX_ID = 2;
    public static int ARCHIVE_ID = 12;

    public int[] subSkeletonIDs;
    public int priority = -1;
    public int[] skeletonIDs;
    public int[] skeletonLengths;
    public int[] anIntArray2126;
    public int loopDelay = -1;
    public int[] animationFlowControl;
    public boolean oneSquareAnimation = false;
    public int forcedPriority = 5;
    public int leftHandItem = -1;
    public int skeletonStep = 99;
    public int rightHandItem = -1;
    public int delayType = 2;
    public int resetWhenWalk = -1;

    public AnimationDefinition(int definitionID) {
        super(definitionID);
    }

    @Override
    public OutputStream encode(OutputStream stream) {

        return stream;
    }

    @Override
    public void decodeValues(int opcode, InputStream stream) {
        int var4;
        int index;
        if(1 == opcode) {
            int frameCount = stream.readUnsignedShort();
            this.skeletonLengths = new int[frameCount];

            for(index = 0; index < frameCount; ++index) {
                this.skeletonLengths[index] = stream.readUnsignedShort();
            }

            this.skeletonIDs = new int[frameCount];

            for(index = 0; index < frameCount; ++index) {
                this.skeletonIDs[index] = stream.readUnsignedShort();
            }

            for(index = 0; index < frameCount; ++index) {
                this.skeletonIDs[index] += stream.readUnsignedShort() << 16;
            }

        } else if(2 == opcode) {
            this.loopDelay = stream.readUnsignedShort();
        } else if(3 == opcode) {
            var4 = stream.readUnsignedByte();
            this.animationFlowControl = new int[1 + var4];

            for(index = 0; index < var4; ++index) {
                this.animationFlowControl[index] = stream.readUnsignedByte();
            }

            this.animationFlowControl[var4] = 9999999;
        } else if(opcode == 4) {
            this.oneSquareAnimation = true;
        } else if(opcode == 5) {
            this.forcedPriority = stream.readUnsignedByte();
        } else if(opcode == 6) {
            this.leftHandItem = stream.readUnsignedShort();
        } else if(opcode == 7) {
            this.rightHandItem = stream.readUnsignedShort();
        } else if(opcode == 8) {
            this.skeletonStep = stream.readUnsignedByte();
        } else if(opcode == 9) {
            this.resetWhenWalk = stream.readUnsignedByte();
        } else if(opcode == 10) {
            this.priority = stream.readUnsignedByte();
        } else if(opcode == 11) {
            this.delayType = stream.readUnsignedByte();
        } else if(opcode == 12) {
            var4 = stream.readUnsignedByte();
            this.subSkeletonIDs = new int[var4];

            for(index = 0; index < var4; ++index) {
                this.subSkeletonIDs[index] = stream.readUnsignedShort();
            }

            for(index = 0; index < var4; ++index) {
                this.subSkeletonIDs[index] += stream.readUnsignedShort() << 16;
            }

        } else if(opcode == 13) {
            var4 = stream.readUnsignedByte();
            this.anIntArray2126 = new int[var4];

            for(index = 0; index < var4; ++index) {
                this.anIntArray2126[index] = stream.read24BitInt();
            }

        } else {
            System.out.println("[AnimationDefinition] Missing OPCode: " + opcode);
        }
    }

    public ModelRasterizer method2229(Store store, ModelRasterizer var1, int frame) {
        int frameID = this.skeletonIDs[frame];
        AnimationSkeletonSet skeletonSet = AnimationSkeletonSet.getAnimationSkeletonSet(store, frameID >> 16);
        frameID &= '\uffff';
        if(null == skeletonSet) {
            return var1.method2852(true);
        } else {
            ModelRasterizer var5 = var1.method2852(!skeletonSet.isStepAlpha(frameID));
            var5.applyTransform(skeletonSet, frameID);
            return var5;
        }
    }

    public ModelRasterizer method2265(Store store, ModelRasterizer var1, int var2, AnimationDefinition var3, int var4, byte var5) {
        var2 = this.skeletonIDs[var2];
        AnimationSkeletonSet var6 = AnimationSkeletonSet.getAnimationSkeletonSet(store, var2 >> 16);
        var2 &= '\uffff';
        if(var6 == null) {
            return var3.method2229(store, var1, var4);
        } else {
            var4 = var3.skeletonIDs[var4];
            AnimationSkeletonSet var7 = AnimationSkeletonSet.getAnimationSkeletonSet(store, var4 >> 16);
            var4 &= '\uffff';
            ModelRasterizer rasterizer;
            if(var7 == null) {
                rasterizer = var1.method2852(!var6.isStepAlpha(var2));
                rasterizer.applyTransform(var6, var2);
                return rasterizer;
            } else {
                rasterizer = var1.method2852(!var6.isStepAlpha(var2) & !var7.isStepAlpha(var4));
                rasterizer.method2859(var6, var2, var7, var4, this.animationFlowControl);
                return rasterizer;
            }
        }
    }

    @Override
    public void printDefinition() {
        System.out.println("Frame Count: " + skeletonLengths.length);
        System.out.print("Delays: [");
        for(int frameLength : skeletonLengths)
            System.out.print(frameLength + ",");
        System.out.print("]");
        System.out.println();
        System.out.print("IDs: [");
        for(int frameID : skeletonIDs)
            System.out.print(frameID + ",");
        System.out.print("]");
        System.out.println();
        System.out.println("Loop Delay: " + loopDelay);
        System.out.print("Flow Control: [");
        for(int frameID : skeletonIDs)
            System.out.print(frameID + ",");
        System.out.print("]");
        System.out.println();
        System.out.println("One Square: " + oneSquareAnimation);
        System.out.println("Forced Priority: " + forcedPriority);
        System.out.println("LeftI: " + leftHandItem);
        System.out.println("RightI: " + rightHandItem);
        System.out.println("opcode8: " + skeletonStep);
        System.out.println("ResetWalk: " + resetWhenWalk);
        System.out.println("Priority: " + priority);
        System.out.println("DelayType: " + delayType);
        System.out.println("-------------------------");
    }

}
