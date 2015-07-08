package com.osrs.suite.editors;


import com.osrs.suite.cache.AnimationSkeleton;
import com.osrs.suite.cache.AnimationSkin;
import com.osrs.suite.editors.impl.*;

/**
 * Created by Allen Kinzalow on 3/14/2015.
 */
public enum EditorDefinition {

    ANIMATION_DEFINITION("Animation Definitions", AnimationDefEditor.class),
    ITEM_DEFINITION("Item Definitions", ItemDefEditor.class),
    SPRITE_DEFINITION("Sprite Definitions", SpriteEditor.class),
    FILE_EDITOR("File Editor", FileEditor.class),
    MAP_VALUE_EDITOR("Map Value Editor", MappedValueEditor.class);
    //ANIMATION_SKELETON("Animation Skeletons", AnimationSkeletonEditor.class),
    //ANIMATION_SKIN("Animation Skins", AnimationSkinEditor.class);

    private String name;
    private Class<?> editor;

    EditorDefinition(String name, Class<?> editor) {
        this.name = name;
        this.editor = editor;
    }

    public String getName() {
        return name;
    }

    public Class<?> getEditor() {
        return editor;
    }

}
