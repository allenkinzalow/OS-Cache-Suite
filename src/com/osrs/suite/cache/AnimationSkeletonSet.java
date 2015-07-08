package com.osrs.suite.cache;

import com.alex.store.Store;

import java.util.HashMap;

/**
 * Created by Allen Kinzalow on 3/14/2015.
 */
public class AnimationSkeletonSet {

    public AnimationSkeleton[] animationSkeletons;
    static HashMap<Integer,AnimationSkeletonSet> skeletonMap = new HashMap<Integer,AnimationSkeletonSet>();

    AnimationSkeletonSet(Store store, int skeletonID) {
        int skeletonCount = store.getIndexes()[AnimationSkeleton.INDEX_ID].getValidFilesCount(skeletonID);
        this.animationSkeletons = new AnimationSkeleton[skeletonCount];
        int fileIDIndex = 0;
        //System.out.println("Skeletons: " + fileList.length);
        AnimationSkin finalSkin = null;
        while (fileIDIndex < skeletonCount) {
            byte[] skeletonData = store.getIndexes()[AnimationSkeleton.INDEX_ID].getFile(skeletonID, fileIDIndex);
            AnimationSkin animSkin = null;
            int skinID = (skeletonData[AnimationSkin.INDEX_ID] & 255) << 8 | skeletonData[1] & 255;
            byte[] skinData = store.getIndexes()[1].getFile(skinID, 0);
            animSkin = new AnimationSkin(skinID, skinData);
            this.animationSkeletons[fileIDIndex] = new AnimationSkeleton(skeletonData, animSkin);
            ++fileIDIndex;

        }

    }

    public static AnimationSkeletonSet initAnimSkeletonSet(Store store, int skeletonID) {
        boolean shouldContinue = true;
        int skeletonCount = store.getIndexes()[AnimationSkeleton.INDEX_ID].getValidFilesCount(skeletonID);

        for(int frameIndex = 0; frameIndex < skeletonCount; ++frameIndex) {
            byte[] skeletonData = store.getIndexes()[AnimationSkeleton.INDEX_ID].getFile(skeletonID, frameIndex);
            if(skeletonData == null) {
                shouldContinue = false;
            } else {
                int skinID = (skeletonData[0] & 255) << 8 | skeletonData[1] & 255;
                byte[] skinData;
                skinData = store.getIndexes()[AnimationSkin.INDEX_ID].getFile(skinID, 0);
                if(null == skinData)
                    shouldContinue = false;
            }
        }

        if(!shouldContinue) {
            return null;
        } else {
            try {
                return new AnimationSkeletonSet(store, skeletonID);
            } catch (Exception var11) {
                var11.printStackTrace();
                return null;
            }
        }
    }

    public static AnimationSkeletonSet getAnimationSkeletonSet(Store store, int skeletonID) {
        skeletonID = skeletonID >> 16;
        AnimationSkeletonSet skeletonSet = skeletonMap.get(skeletonID);
        if(null != skeletonSet) {
            return skeletonSet;
        } else {
            skeletonSet = initAnimSkeletonSet(store, skeletonID);
            if(null != skeletonSet) {
                skeletonMap.put(skeletonID, skeletonSet);
            }

            return skeletonSet;
        }
    }

    public boolean isStepAlpha(int skeletonID) {
        return this.animationSkeletons[skeletonID].hasAlpha;
    }

}
