package com.osrs.suite.cache.renderable.model;

/**
 * Created by Allen Kinzalow on 3/16/2015.
 */
public class VertexNormal {

    int z;
    int y;
    int magnitude;
    int x;

    VertexNormal(VertexNormal var1) {
        this.x = var1.x * 1;
        this.y = var1.y * 1;
        this.z = var1.z * 1;
        this.magnitude = var1.magnitude * 1;
    }

    VertexNormal() {}

}
