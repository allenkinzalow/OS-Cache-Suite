package com.osrs.suite.cache;

import com.alex.io.InputStream;
import com.alex.store.Store;
import com.osrs.suite.cache.renderable.Rasterizer2D;
import com.osrs.suite.cache.renderable.Rasterizer3D;
import com.osrs.suite.utilities.StringUtilities;

/**
 * Created by Allen Kinzalow on 3/22/2015.
 */
public class Widget {

    static boolean[] aBoolArray2909 = new boolean[100];
    static boolean[] aBoolArray2910 = new boolean[100];
    static int[] componentRasterizeX = new int[100];
    static int[] componentRasterizeY = new int[100];
    static int[] componentRasterizeWidth = new int[100];
    static int[] componentRasterizeHeight = new int[100];

    public int[] spritesY;
    public static boolean[] interfacesLoadedArray;
    public int widgetItemPaddingX = 0;
    public static boolean mediaUnavailable = false;
    public int mediaRotationX = 0;
    public int[] conditionTypes;
    public int anInt1772 = 941549721;
    public Object[] anObjectArray1773;
    public int anInt1774;
    public int anInt1775;
    public int xPosition = 0;
    public int[] conditionValues;
    public int anInt1778 = 0;
    public int anInt1779 = 0;
    public int anInt1780;
    public Object[] anObjectArray1781;
    public int hoverPopup = -2102936727;
    public boolean hidden = false;
    public int anInt1784 = 0;
    public int scrollPosition = 0;
    public int anInt1786 = 0;
    public int anInt1787 = 0;
    public int componentColor = 0;
    public Object[] anObjectArray1789;
    public int height = 0;
    public int actionType = 0;
    public boolean filled = false;
    public int alpha = 0;
    public Object[] anObjectArray1794;
    public Object[] anObjectArray1795;
    public int anInt1796 = 409060445;
    public int anInt1797 = 0;
    public boolean aBool1798 = false;
    public int anInt1799 = 0;
    public int anInt1800 = 0;
    public boolean flipImageHorizontal;
    public Object[] anObjectArray1802;
    public int mediaType = -1011789675;
    public int mediaID = -1321013799;
    public int anInt1805;
    int activeMediaID = -2076994841;
    public int mediaAnimID = -596245015;
    public int componentType;
    public int anInt1809 = 0;
    public int anInt1810 = 0;
    public int anInt1811 = -2000475851;
    public int mediaRotationY = 0;
    public int anInt1813 = 0;
    public Object[] anObjectArray1814;
    public int activeMediaAnimID = -1869860855;
    public boolean aBool1816 = false;
    public int fontID = -343759393;
    public String componentString = "";
    public int anInt1819;
    public int anInt1820 = 0;
    public String aString1821 = "";
    public Object[] anObjectArray1822;
    public boolean textCentered = false;
    public int yPosition = 0;
    public int widgetItemPaddingY = 0;
    public boolean aBool1826;
    public int[] spritesX;
    public int mouseOverActiveColor = 0;
    public String[] interfaceActions;
    public int anInt1830 = 0;
    public boolean flipImageVertical;
    public String[] aStringArray1832;
    public Widget aClass108_Sub17_1833 = null;
    public int anInt1834 = 0;
    public int anInt1835 = 0;
    public boolean aBool1836 = false;
    public boolean hasScript = false;
    public int interfaceHash = -1;
    public int mouseOverColor = 0;
    public Object[] anObjectArray1841;
    public Object[] anObjectArray1842;
    public Object[] anObjectArray1843;
    public String activeComponentString = "";
    public Object[] anObjectArray1845;
    public int anInt1846 = -785411763;
    public Object[] anObjectArray1847;
    public Object[] anObjectArray1848;
    public Object[] anObjectArray1849;
    public int width = 0;
    public Object[] onConfigTrigger;
    public int[] configChangeTriggers;
    public Object[] onItemUpdateTrigger;
    public int[] itemUpdateTriggers;
    public boolean aBool1855 = false;
    public int[] anIntArray1856;
    public Object[] anObjectArray1857;
    public static Widget[][] interface_cache;
    public Object[] mouseWheelTrigger;
    public int anInt1860 = 0;
    public Object[] anObjectArray1861;
    public int anInt1862 = 0;
    public int anInt1863 = 0;
    public Object[] anObjectArray1864;
    public Object[] anObjectArray1865;
    public int componentActiveColor = 0;
    public int[][] opcodes;
    public Object[] onSkillUpdateTrigger;
    public int[] sprites;
    public int anInt1870 = 149205505;
    public String spellName = "";
    public String tooltip;
    public int[] widgetItems;
    public int[] widgetItemAmounts;
    public int itemID;
    public Object[] anObjectArray1876;
    public int mediaFrameID;
    public int mediaZoom = 1060870108;
    public Widget[] aClass108_Sub17Array1879;
    public boolean aBool1880;
    public boolean aBool1881;
    public Object[] anObjectArray1882;
    public int anInt1883;
    public String selectedActionName = "";
    public int anInt1885;
    public int anInt1886 = 0;
    public int cycle;
    int activeMediaType = -1227663423;

    public final static int INDEX_ID = 3;

    public static Widget getInterfaceComponentForHash(Store store, int hash) {
        int interfaceID = hash >> 16;
        int componentID = hash & '\uffff';
        if(null == interface_cache[interfaceID] || null == interface_cache[interfaceID][componentID]) {
            boolean var4 = loadInterface(store, interfaceID);
            if(!var4) {
                return null;
            }
        }

        return interface_cache[interfaceID][componentID];
    }

    public int getComponentID() {
        return (interfaceHash) & 0xff;
    }

    public int getParentID() {
        return (interfaceHash) >> 16;
    }

    public static boolean loadInterface(Store store, int interfaceID) {
        //System.out.println("Load: " + interfaceID + " max: " + interfacesLoadedArray.length);
        if(interfacesLoadedArray[interfaceID]) {
            return true;
        } else if(!store.getIndexes()[INDEX_ID].archiveExists(interfaceID)) {
            return false;
        } else {
            int componentCount = store.getIndexes()[INDEX_ID].getValidFilesCount(interfaceID);
            if(componentCount == 0) {
                interfacesLoadedArray[interfaceID] = true;
                return true;
            } else {
                if(null == interface_cache[interfaceID]) {
                    interface_cache[interfaceID] = new Widget[componentCount];
                }

                for(int component = 0; component < componentCount; ++component) {
                    if(null == interface_cache[interfaceID][component]) {
                        byte[] componentData = store.getIndexes()[INDEX_ID].getFile(interfaceID, component);
                        if(null != componentData) {
                            interface_cache[interfaceID][component] = new Widget();
                            interface_cache[interfaceID][component].interfaceHash = (component + (interfaceID << 16)) * -585455939;
                            if(componentData[0] == -1) {
                                interface_cache[interfaceID][component].decodeActiveInterface(new InputStream(componentData));
                            } else {
                                interface_cache[interfaceID][component].decodeInterface(new InputStream(componentData));
                            }
                        }
                    }
                }

                interfacesLoadedArray[interfaceID] = true;
                return true;
            }
        }
    }

    void decodeInterface(InputStream buffer) {
        this.aBool1855 = false;
        this.componentType = buffer.readUnsignedByte();
        this.actionType = buffer.readUnsignedByte();
        this.anInt1886 = buffer.readUnsignedShort();
        this.anInt1778 = (this.xPosition = buffer.readShort());
        this.anInt1779 = (this.yPosition = buffer.readShort());
        this.width = buffer.readUnsignedShort();
        this.height = buffer.readUnsignedShort();
        this.alpha = buffer.readUnsignedByte();
        this.hoverPopup = buffer.readUnsignedShort();
        if('\uffff' == this.hoverPopup) {
            this.hoverPopup = -1;
        } else {
            this.hoverPopup = ((this.interfaceHash & -65536) + this.hoverPopup);
        }

        this.anInt1846 = buffer.readUnsignedShort();
        if(this.anInt1846 == '\uffff') {
            this.anInt1846 = -1;
        }

        int conditionCount = buffer.readUnsignedByte();
        int condition;
        if(conditionCount > 0) {
            this.conditionTypes = new int[conditionCount];
            this.conditionValues = new int[conditionCount];

            for(condition = 0; condition < conditionCount; ++condition) {
                this.conditionTypes[condition] = buffer.readUnsignedByte();
                this.conditionValues[condition] = buffer.readUnsignedShort();
            }
        }

        int opcodeCount = buffer.readUnsignedByte();
        int opcode;
        int subOpCode;
        int subOpCodeCount;
        if(opcodeCount > 0) {
            this.opcodes = new int[opcodeCount][];

            for(opcode = 0; opcode < opcodeCount; ++opcode) {
                subOpCodeCount = buffer.readUnsignedShort();
                this.opcodes[opcode] = new int[subOpCodeCount];

                for(subOpCode = 0; subOpCode < subOpCodeCount; ++subOpCode) {
                    this.opcodes[opcode][subOpCode] = buffer.readUnsignedShort();
                    if(this.opcodes[opcode][subOpCode] == '\uffff') {
                        this.opcodes[opcode][subOpCode] = -1;
                    }
                }
            }
        }

        if(0 == this.componentType) {
            this.anInt1787 = buffer.readUnsignedShort();
            this.hidden = buffer.readUnsignedByte() == 1;
        }

        if(1 == this.componentType) {
            buffer.readUnsignedShort();
            buffer.readUnsignedByte();
        }

        if(this.componentType == 2) {
            this.widgetItems = new int[this.height * this.width];
            this.widgetItemAmounts = new int[this.height * this.width];
            int var6 = buffer.readUnsignedByte();
            if(var6 == 1) {
                this.anInt1830 = (this.anInt1830 | 268435456);
            }

            int tempVal = buffer.readUnsignedByte();
            if(1 == tempVal) {
                this.anInt1830 = (this.anInt1830 | 1073741824);
            }

            int var7 = buffer.readUnsignedByte();
            if(1 == var7) {
                this.anInt1830 = (this.anInt1830 | Integer.MIN_VALUE);
            }

            int var3 = buffer.readUnsignedByte();
            if(var3 == 1) {
                this.anInt1830 = (this.anInt1830 * 956161607 | 536870912);
            }

            this.widgetItemPaddingX = buffer.readUnsignedByte();
            this.widgetItemPaddingY = buffer.readUnsignedByte();
            this.spritesX = new int[20];
            this.spritesY = new int[20];
            this.sprites = new int[20];

            int index;
            for(index = 0; index < 20; ++index) {
                int hasSprite = buffer.readUnsignedByte();
                if(1 == hasSprite) {
                    this.spritesX[index] = buffer.readShort();
                    this.spritesY[index] = buffer.readShort();
                    this.sprites[index] = buffer.readInt();
                } else {
                    this.sprites[index] = -1;
                }
            }

            this.interfaceActions = new String[5];

            for(index = 0; index < 5; ++index) {
                String action = StringUtilities.readString_2(buffer);
                if(action.length() > 0) {
                    this.interfaceActions[index] = action;
                    this.anInt1830 = (this.anInt1830 | 1 << 23 + index);
                }
            }
        }

        if(this.componentType == 3) {
            this.filled = buffer.readUnsignedByte() == 1;
        }

        if(4 == this.componentType || 1 == this.componentType) {
            this.anInt1863 = buffer.readUnsignedByte();
            this.anInt1862 = buffer.readUnsignedByte();
            this.anInt1820 = buffer.readUnsignedByte();
            this.fontID = buffer.readUnsignedShort();
            if(this.fontID == '\uffff') {
                this.fontID = -1;
            }

            this.textCentered = buffer.readUnsignedByte() == 1;
        }

        if(4 == this.componentType) {
            this.componentString = StringUtilities.readString_2(buffer);
            this.activeComponentString = StringUtilities.readString_2(buffer);
        }

        if(1 == this.componentType || this.componentType == 3 || 4 == this.componentType) {
            this.componentColor = buffer.readInt();
        }

        if(this.componentType == 3 || this.componentType == 4) {
            this.componentActiveColor = buffer.readInt();
            this.mouseOverColor = buffer.readInt();
            this.mouseOverActiveColor = buffer.readInt();
        }

        if(this.componentType == 5) { // sprite
            this.anInt1870 = buffer.readInt();
            this.anInt1796 = buffer.readInt();
        }

        if(6 == this.componentType) {
            this.mediaType = -1011789675;
            this.mediaID = buffer.readUnsignedShort();
            if(this.mediaID == '\uffff') {
                this.mediaID = -1;
            }

            this.activeMediaType = -1227663423;
            this.activeMediaID = buffer.readUnsignedShort();
            if(this.activeMediaID == '\uffff') {
                this.activeMediaID = -1;
            }

            this.mediaAnimID = buffer.readUnsignedShort();
            if(this.mediaAnimID == '\uffff') {
                this.mediaAnimID = -1;
            }

            this.activeMediaAnimID = buffer.readUnsignedShort();
            if(this.activeMediaAnimID == '\uffff') {
                this.activeMediaAnimID = -1;
            }

            this.mediaZoom = buffer.readUnsignedShort();
            this.mediaRotationX = buffer.readUnsignedShort();
            this.mediaRotationY = buffer.readUnsignedShort();
        }

        if(7 == this.componentType * 942877543) {
            this.widgetItems = new int[this.height * this.width];
            this.widgetItemAmounts = new int[this.height * this.width];
            this.anInt1863 = buffer.readUnsignedByte();
            this.fontID = buffer.readUnsignedShort();
            if(this.fontID == '\uffff') {
                this.fontID = -1;
            }

            this.textCentered = buffer.readUnsignedByte() == 1;
            this.componentColor = buffer.readInt() * 1601296361;
            this.widgetItemPaddingX = buffer.readShort();
            this.widgetItemPaddingY = buffer.readShort();
            opcode = buffer.readUnsignedByte();
            if(1 == opcode) {
                this.anInt1830 = (this.anInt1830 * 956161607 | 1073741824) * -1506120841;
            }

            this.interfaceActions = new String[5];

            for(int actionIndex = 0; actionIndex < 5; ++actionIndex) {
                String action = StringUtilities.readString_2(buffer);
                if(action.length() > 0) {
                    this.interfaceActions[actionIndex] = action;
                    this.anInt1830 = (this.anInt1830 * 956161607 | 1 << 23 + actionIndex) * -1506120841;
                }
            }
        }

        if(this.componentType * 942877543 == 8) {
            this.componentString = StringUtilities.readString_2(buffer);
        }

        if(2 == this.actionType * -2005807019 || 2 == this.componentType * 942877543) {
            this.selectedActionName = StringUtilities.readString_2(buffer);
            this.spellName = StringUtilities.readString_2(buffer);
            opcode = buffer.readUnsignedShort() & 63;
            this.anInt1830 = (this.anInt1830 * 956161607 | opcode << 11) * -1506120841;
        }

        if(this.actionType * -2005807019 == 1 || 4 == this.actionType * -2005807019 || 5 == this.actionType * -2005807019 || 6 == this.actionType * -2005807019) {
            this.tooltip = StringUtilities.readString_2(buffer);
            if(this.tooltip.length() == 0) {
                if(this.actionType * -2005807019 == 1) {
                    this.tooltip = "Ok";
                }

                if(4 == this.actionType * -2005807019) {
                    this.tooltip = "Select";
                }

                if(this.actionType * -2005807019 == 5) {
                    this.tooltip = "Select";
                }

                if(this.actionType * -2005807019 == 6) {
                    this.tooltip = "Continue";
                }
            }
        }

        if(this.actionType * -2005807019 == 1 || 4 == this.actionType * -2005807019 || 5 == this.actionType * -2005807019) {
            this.anInt1830 = (this.anInt1830 * 956161607 | 4194304) * -1506120841;
        }

        if(this.actionType * -2005807019 == 6) {
            this.anInt1830 = (this.anInt1830 * 956161607 | 1) * -1506120841;
        }
    }

    void decodeActiveInterface(InputStream buffer) {
        buffer.readUnsignedByte();
        this.aBool1855 = true;
        this.componentType = buffer.readUnsignedByte();
        this.anInt1886 = buffer.readUnsignedShort();
        this.anInt1778 = (this.xPosition = buffer.readShort());
        this.anInt1779 = (this.yPosition = buffer.readShort());
        this.width = buffer.readUnsignedShort();
        if(9 == this.componentType) {
            this.height = buffer.readShort();
        } else {
            this.height = buffer.readUnsignedShort();
        }

        this.hoverPopup = buffer.readUnsignedShort();
        if(this.hoverPopup == '\uffff') {
            this.hoverPopup = -1;
        } else {
            this.hoverPopup = (this.hoverPopup + (this.interfaceHash & -65536));
        }

        this.hidden = buffer.readUnsignedByte() == 1;
        if(0 == this.componentType) {
            this.anInt1786 = buffer.readUnsignedShort();
            this.anInt1787 = buffer.readUnsignedShort();
        }

        if(this.componentType == 5) {
            this.anInt1870 = buffer.readInt();
            this.anInt1797 = buffer.readUnsignedShort();
            this.aBool1798 = buffer.readUnsignedByte() == 1;
            this.alpha = buffer.readUnsignedByte();
            this.anInt1799 = buffer.readUnsignedByte();
            this.anInt1800 = buffer.readInt();
            this.flipImageHorizontal = buffer.readUnsignedByte() == 1;
            this.flipImageVertical = buffer.readUnsignedByte() == 1;
        }

        if(6 == this.componentType) {
            this.mediaType = -1011789675;
            this.mediaID = buffer.readUnsignedShort();
            if(this.mediaID == '\uffff') {
                this.mediaID = -1;
            }

            this.anInt1809 = buffer.readShort();
            this.anInt1810 = buffer.readShort();
            this.mediaRotationX = buffer.readUnsignedShort();
            this.mediaRotationY = buffer.readUnsignedShort();
            this.anInt1813 = buffer.readUnsignedShort();
            this.mediaZoom = buffer.readUnsignedShort();
            this.mediaAnimID = buffer.readUnsignedShort();
            if(this.mediaAnimID == '\uffff') {
                this.mediaAnimID = -1;
            }

            this.aBool1816 = buffer.readUnsignedByte() == 1;
        }

        if(4 == this.componentType) {
            this.fontID = buffer.readUnsignedShort();
            if('\uffff' == this.fontID) {
                this.fontID = -1;
            }

            this.componentString = StringUtilities.readString_2(buffer);
            this.anInt1820 = buffer.readUnsignedByte();
            this.anInt1863 = buffer.readUnsignedByte();
            this.anInt1862 = buffer.readUnsignedByte();
            this.textCentered = buffer.readUnsignedByte() == 1;
            this.componentColor = buffer.readInt();
        }

        if(this.componentType * 942877543 == 3) {
            this.componentColor = buffer.readInt();
            this.filled = buffer.readUnsignedByte() == 1;
            this.alpha = buffer.readUnsignedByte();
        }

        if(this.componentType * 942877543 == 9) {
            this.anInt1811 = buffer.readUnsignedByte();
            this.componentColor = buffer.readInt();
        }

        this.anInt1830 = buffer.read24BitInt();
        this.aString1821 = StringUtilities.readString_2(buffer);
        int var4 = buffer.readUnsignedByte();
        if(var4 > 0) {
            this.aStringArray1832 = new String[var4];

            for(int var3 = 0; var3 < var4; ++var3) {
                this.aStringArray1832[var3] = StringUtilities.readString_2(buffer);
            }
        }

        this.anInt1834 = buffer.readUnsignedByte();
        this.anInt1835 = buffer.readUnsignedByte();
        this.aBool1836 = buffer.readUnsignedByte() == 1;
        this.selectedActionName = StringUtilities.readString_2(buffer);

        this.anObjectArray1861 = this.readScriptParameters(buffer);
        this.anObjectArray1802 = this.readScriptParameters(buffer);
        this.anObjectArray1876 = this.readScriptParameters(buffer);
        this.anObjectArray1773 = this.readScriptParameters(buffer);
        this.anObjectArray1849 = this.readScriptParameters(buffer);
        this.onConfigTrigger = this.readScriptParameters(buffer);
        this.onItemUpdateTrigger = this.readScriptParameters(buffer);
        this.onSkillUpdateTrigger = this.readScriptParameters(buffer);
        this.anObjectArray1857 = this.readScriptParameters(buffer);
        this.anObjectArray1795 = this.readScriptParameters(buffer);
        this.anObjectArray1845 = this.readScriptParameters(buffer);
        this.anObjectArray1794 = this.readScriptParameters(buffer);
        this.anObjectArray1841 = this.readScriptParameters(buffer);
        this.anObjectArray1842 = this.readScriptParameters(buffer);
        this.anObjectArray1843 = this.readScriptParameters(buffer);
        this.anObjectArray1847 = this.readScriptParameters(buffer);
        this.anObjectArray1848 = this.readScriptParameters(buffer);
        this.mouseWheelTrigger = this.readScriptParameters(buffer);
        this.configChangeTriggers = this.readTriggers(buffer);
        this.itemUpdateTriggers = this.readTriggers(buffer);
        this.anIntArray1856 = this.readTriggers(buffer);
    }

    Object[] readScriptParameters(InputStream buffer) {
        int length = buffer.readUnsignedByte();
        if(length == 0) {
            return null;
        } else {
            Object[] params = new Object[length];

            for(int paramIndex = 0; paramIndex < length; ++paramIndex) {
                int var4 = buffer.readUnsignedByte();
                if(0 == var4) {
                    params[paramIndex] = new Integer(buffer.readInt());
                } else if(var4 == 1) {
                    params[paramIndex] = StringUtilities.readString_2(buffer);
                }
            }

            this.hasScript = true;
            return params;
        }
    }

    int[] readTriggers(InputStream var1) {
        int triggerCount = var1.readUnsignedByte();
        if(0 == triggerCount) {
            return null;
        } else {
            int[] triggers = new int[triggerCount];

            for(int triggerIndex = 0; triggerIndex < triggerCount; ++triggerIndex) {
                triggers[triggerIndex] = var1.readInt();
            }

            return triggers;
        }
    }

    /*static final void renderInterface(Store store, int interfaceID, int x, int y, int width, int height, int var5, int var6, int var7, int var8, boolean subcomp) {
        if (loadInterface(store, interfaceID)) {
            renderInterfaceComponents(interface_cache[interfaceID], -1, x, y, width, height, var5, var6, var7, 2123082435, subcomp);
        } else if (var7 != -1) {
            aBoolArray2909[var7] = true;
        } else {
            for (int var9 = 0; var9 < 100; ++var9) {
                aBoolArray2909[var9] = true;
            }

        }
    }*/

    /*static final void renderInterfaceComponents(Widget[] components, int var1, int xRender, int yRender, int renderWidth, int renderHeight, int xOffset, int yOffset, int var8, int var9, boolean subcomp) {
        Rasterizer2D.setRasterizationRect(xRender, yRender, renderWidth, renderHeight);
        Rasterizer3D.method2970();

        int parentID = components[0].getParentID();
        int start = (parentID == 548 && !subcomp) ? -1 : 0;
        for (int componentIndex = start; componentIndex < components.length; ++componentIndex) {
            if(componentIndex == 104 && parentID == 548 && !subcomp)
                continue;
            if(componentIndex == -1 && parentID == 548 && !subcomp)
                componentIndex = 104;

            Widget component = components[componentIndex];
            if (null != component && (var1 == component.hoverPopup || var1 == -1412584499)) {
                int var19;
                if (-1 == var8) {
                    componentRasterizeX[Client.anInt2907 * -792079877] = component.xPosition + xOffset;
                    componentRasterizeY[Client.anInt2907 * -792079877] = yOffset + component.yPosition;
                    componentRasterizeWidth[Client.anInt2907 * -792079877] = component.width;
                    componentRasterizeHeight[Client.anInt2907 * -792079877] = component.height;
                    var19 = (Client.anInt2907 += 1557434675) * -792079877 - 1;
                } else {
                    var19 = var8;
                }

                component.anInt1780 = var19 * 1057491055;
                //component.cycle = Client.cycle * 1800650659;

                //if(component.getComponentID() == 116)
                //System.out.println("116: " + component.aBool1855 + " " + RSInterface.isComponentHidden(component, (byte) 82));
                if (!component.aBool1855 || !component.hidden) {
                    int var21;
                    if (component.anInt1886 * -917776085 > 0) {
                        int mediaRenderType = component.anInt1886 * -917776085; // this is how the media is displayed
                        if (324 == mediaRenderType) {
                            if (-1 == Client.anInt2960 * 598950917) {
                                Client.anInt2960 = component.anInt1870 * -1668832973;
                                Client.anInt2961 = component.anInt1796 * 227063943;
                            }

                            if (Client.aClass93_2926.isFemale) {
                                component.anInt1870 = Client.anInt2960 * 2056077819;
                            } else {
                                component.anInt1870 = Client.anInt2961 * -1353870173;
                            }
                        } else if (325 == mediaRenderType) {
                            if (-1 == Client.anInt2960 * 598950917) {
                                Client.anInt2960 = component.anInt1870 * -1668832973;
                                Client.anInt2961 = component.anInt1796 * 227063943;
                            }

                            if (Client.aClass93_2926.isFemale) {
                                component.anInt1870 = Client.anInt2961 * -1353870173;
                            } else {
                                component.anInt1870 = Client.anInt2960 * 2056077819;
                            }
                        } else if (mediaRenderType == 327) {
                            component.mediaRotationX = -771359726;
                            component.mediaRotationY = ((int) (Math.sin((double) (Client.cycle * -637317861) / 40.0D) * 256.0D) & 2047) * 40361315;
                            component.mediaType = -763981079;
                            component.mediaID = 0;
                        } else if (mediaRenderType == 328) {
                            component.mediaRotationX = -771359726;
                            component.mediaRotationY = ((int) (Math.sin((double) (Client.cycle * -637317861) / 40.0D) * 256.0D) & 2047) * 40361315;
                            component.mediaType = -763981079;
                            component.mediaID = 1321013799;
                        }
                    }

                    int componentXPos = xOffset + component.xPosition * 985647797;
                    int componentYPos = yOffset + component.yPosition * 1730176157;
                    int transparency = component.alpha * 1076754521;
                    int var24;
                    int var29;
                    if (component == Client.aClass108_Sub17_2877) {
                        if (var1 != -1412584499 && !component.aBool1836) {
                            Client.aClass108_Sub17Array2963 = components;
                            URLSession.anInt619 = xOffset * 1934415775;
                            EquipmentKit.anInt1344 = yOffset * -1922107287;
                            continue;
                        }

                        if (Client.aBool2888 && Client.aBool2882) {
                            var24 = MouseInputHandler.mouseX * -367052265;
                            var29 = MouseInputHandler.mouseY * 1533395117;
                            var24 -= Client.anInt2879 * 1247450239;
                            var29 -= Client.anInt2880 * 781803909;
                            if (var24 < Client.anInt2883 * -651460611) {
                                var24 = Client.anInt2883 * -651460611;
                            }

                            if (var24 + component.width * -1281443035 > Client.anInt2883 * -651460611 + Client.aClass108_Sub17_2878.width * -1281443035) {
                                var24 = Client.aClass108_Sub17_2878.width * -1281443035 + Client.anInt2883 * -651460611 - component.width * -1281443035;
                            }

                            if (var29 < Client.anInt2884 * 1677738499) {
                                var29 = Client.anInt2884 * 1677738499;
                            }

                            if (var29 + component.height * 334099177 > Client.anInt2884 * 1677738499 + Client.aClass108_Sub17_2878.height * 334099177) {
                                var29 = Client.anInt2884 * 1677738499 + Client.aClass108_Sub17_2878.height * 334099177 - component.height * 334099177;
                            }

                            componentXPos = var24;
                            componentYPos = var29;
                        }

                        if (!component.aBool1836) {
                            transparency = 128;
                        }
                    }

                    int compID = component.getComponentID();
                    if(!subcomp && (component.getParentID() == 548 && compID == 73 || compID == 74 || compID == 70 || compID == 66 || compID == 68 || compID == 45  || compID == 70 || compID == 100 ||
                            compID == 128 || compID == 127 || compID == 125 || compID == 102 || compID == 28 || compID == 35 || compID == 64)) {
                        componentXPos = componentXPos + extendedWidth;
                        Client.aBoolArray2831[var19] = true;
                        Client.aBoolArray2910[var19] = true;
                    }
                    if(!subcomp && (component.getParentID() == 548 && compID == 116 || compID == 0 || compID == 62 || compID == 66 || compID == 45 || compID == 62 || compID == 28 || compID == 125 || compID == 100 || compID == 102)) {
                        componentYPos = componentYPos + extendedHeight;
                        Client.aBoolArray2831[var19] = true;
                        Client.aBoolArray2910[var19] = true;
                    }

                    if((component.getParentID() == 548 && compID == 106)) {
                        componentXPos += extendedWidth / 2;
                        if(extendedWidth > 0)
                            componentXPos += 0;
                        componentYPos += extendedHeight;
                        if(extendedHeight > 0)
                            componentYPos -= ((component.height * 334099177) / 2) - yOffset;
                        xRender = componentXPos;
                        yRender = componentYPos;
                        renderWidth = componentXPos + (component.width * -1281443035);
                        renderHeight = componentYPos + (component.height * 334099177);
                        openIntX = componentXPos;
                        openIntY = componentYPos;
                        openIntWidth = component.width * -1281443035;
                        openIntHeight = component.height * 334099177;
                        //System.out.println("RW: " + renderWidth + " X: " + componentXPos + " W: " + (component.width * -1281443035));
                    }

                    if(component.getParentID() == 548)
                        MouseInputHandler.method775(component, -16054773);

                    int var15;
                    int var20;
                    int var22;
                    int var25;
                    int var28;
                    int var30;
                    if (component.componentType * 942877543 == 2) {
                        var24 = xRender;
                        var29 = yRender;
                        var28 = renderWidth;
                        var25 = renderHeight;
                    } else if (component.componentType * 942877543 == 9) {
                        var22 = componentXPos;
                        var30 = componentYPos;
                        var15 = componentXPos + component.width * -1281443035;
                        var20 = component.height * 334099177 + componentYPos;
                        if (var15 < componentXPos) {
                            var22 = var15;
                            var15 = componentXPos;
                        }

                        if (var20 < componentYPos) {
                            var30 = var20;
                            var20 = componentYPos;
                        }

                        ++var15;
                        ++var20;
                        var24 = var22 > xRender ? var22 : xRender;
                        var29 = var30 > yRender ? var30 : yRender;
                        var28 = var15 < renderWidth ? var15 : renderWidth;
                        var25 = var20 < renderHeight ? var20 : renderHeight;
                    } else {
                        var22 = componentXPos + component.width * -1281443035;
                        var30 = componentYPos + component.height * 334099177;
                        var24 = componentXPos > xRender ? componentXPos : xRender;
                        var29 = componentYPos > yRender ? componentYPos : yRender;
                        var28 = var22 < renderWidth ? var22 : renderWidth;
                        var25 = var30 < renderHeight ? var30 : renderHeight;
                    }

                    if (!component.aBool1855 || var24 < var28 && var29 < var25) {
                        if (0 != component.anInt1886 * -917776085) {
                            if (component.anInt1886 * -917776085 == 1337) { // render the scene
                                Client.anInt2735 = componentXPos * 735852373;
                                Client.anInt2820 = componentYPos * -1949788625;
                                int sceneWidth = component.width * -1281443035;
                                int sceneHeight = component.height * 334099177;
                                //System.out.println("Compindex: " + componentIndex + " parent: " + component.getParentID() + " test: " + test + " sub:");
                                if(extendedWidth > 0) {
                                    sceneWidth = ProducingGraphicsBuffer.clientWidth * 1080367531;
                                    renderWidth = ProducingGraphicsBuffer.clientWidth * 1080367531;
                                }
                                if(extendedHeight > 0) {
                                    sceneHeight = ProducingGraphicsBuffer.clientHeight * -2030626229;
                                    renderWidth = ProducingGraphicsBuffer.clientWidth * 1080367531;
                                }
                                NPC.renderScene(componentXPos, componentYPos, sceneWidth, sceneHeight, 2061404552);
                                Rasterizer2D.setRasterizationRect(xRender, yRender, renderWidth, renderHeight);
                                continue;
                            }

                            if (component.anInt1886 * -917776085 == 1338) {
                                //System.out.println("XR: " + xRender + " YR: " + yRender + " RW: " + renderWidth + " RH: " + renderHeight);
                                MiniMap.renderMiniMap(componentXPos, componentYPos, var19, (short) -4901);
                                Rasterizer2D.setRasterizationRect(xRender, yRender, renderWidth, renderHeight);
                                continue;
                            }
                        }

                        //if(compID == 116)
                        //System.out.println("comp : " + compID + " type: " + component.componentType * 942877543);

                        if (0 == component.componentType * 942877543) { // container or scrolling container?
                            if (!component.aBool1855 && RSInterface.isComponentHidden(component, (byte) 83) && component != Client.mouseHoveredComponent) {
                                continue;
                            }

                            if (!component.aBool1855) {
                                if (component.scrollPosition * -643576081 > component.anInt1787 * -1108406155 - component.height * 334099177) {
                                    component.scrollPosition = component.anInt1787 * 1162057435 - component.height * -126788697;
                                }

                                if (component.scrollPosition * -643576081 < 0) {
                                    component.scrollPosition = 0;
                                }
                            }
                            renderInterfaceComponents(components, component.interfaceHash * -1081473899, var24, var29, var28, var25, componentXPos - component.anInt1784 * -133270367, componentYPos - component.scrollPosition * -643576081, var19, 199691778, true);
                            if (component.aClass108_Sub17Array1879 != null) {
                                renderInterfaceComponents(component.aClass108_Sub17Array1879, component.interfaceHash * -1081473899, var24, var29, var28, var25, componentXPos - component.anInt1784 * -1332703670, componentYPos - component.scrollPosition * -643576081, var19, -1149084430, true);
                            }

                            Class108_Sub10 var45 = (Class108_Sub10) Client.aClass101_2866.get((long) (component.interfaceHash * -1081473899));
                            if (null != var45) {
                                RSInterface.renderInterface(var45.anInt1653 * 1557246219, var24, var29, var28, var25, componentXPos, componentYPos, var19, 1300032424, true);
                            }

                            Rasterizer2D.setRasterizationRect(xRender, yRender, renderWidth, renderHeight);
                            Rasterizer3D.method2970();
                        }

                        if (Client.aBoolArray2831[var19] || Client.anInt2916 * 1531358553 > 1) {
                            if (component.componentType * 942877543 == 0 && !component.aBool1855 && component.anInt1787 * -1108406155 > component.height * 334099177) {
                                Class84.renderScrollBar(componentXPos + component.width * -1281443035, componentYPos, component.scrollPosition * -643576081, component.height * 334099177, component.anInt1787 * -1108406155, -785175456);
                            }

                            if (1 != component.componentType * 942877543) {
                                int var10;
                                int var16;
                                int var18;
                                int var41;
                                int var47;
                                if (component.componentType * 942877543 == 2) {
                                    int itemIndex = 0;
                                    // sprite issue is within this loop...
                                    for (var30 = 0; var30 < component.height * 334099177; ++var30) {
                                        for (var15 = 0; var15 < component.width * -1281443035; ++var15) {
                                            int xPos = (component.widgetItemPaddingX * 876962455 + 32) * var15 + componentXPos;
                                            int yPos = var30 * (32 + component.widgetItemPaddingY * -448462053) + componentYPos;
                                            if (itemIndex < 20) {
                                                xPos += component.spritesX[itemIndex];
                                                yPos += component.spritesY[itemIndex];
                                            }

                                            if (component.widgetItems[itemIndex] > 0) {
                                                boolean var52 = false;
                                                boolean var40 = false;
                                                int itemID = component.widgetItems[itemIndex] - 1;
                                                if (xPos + 32 > xRender && xPos < renderWidth && yPos + 32 > yRender && yPos < renderHeight || IsaacRandomGen.aClass108_Sub17_745 == component && itemIndex == Client.anInt2863 * -664226831) {
                                                    RGBSprite itemSprite;
                                                    if (1 == Client.anInt2858 * -968945719 && ClientScriptReference.anInt716 * 347376265 == itemIndex && component.interfaceHash * -1081473899 == Class50.anInt699 * -932350913) {
                                                        itemSprite = ItemDefinition.getItemSprite(itemID, component.widgetItemAmounts[itemIndex], 2, 0, false, 1256224427);
                                                    } else {
                                                        itemSprite = ItemDefinition.getItemSprite(itemID, component.widgetItemAmounts[itemIndex], 1, 3153952, false, -1431232517);
                                                    }

                                                    if (itemSprite != null) {
                                                        if (component == IsaacRandomGen.aClass108_Sub17_745 && Client.anInt2863 * -664226831 == itemIndex) {
                                                            var47 = MouseInputHandler.mouseX * -367052265 - Client.anInt2900 * 785242869;
                                                            var16 = MouseInputHandler.mouseY * 1533395117 - Client.anInt2903 * 685630743;
                                                            if (var47 < 5 && var47 > -5) {
                                                                var47 = 0;
                                                            }

                                                            if (var16 < 5 && var16 > -5) {
                                                                var16 = 0;
                                                            }

                                                            // change "< 5)" to < 10 for faster switching.
                                                            if (Client.anInt2841 * 1113333989 < 5) {
                                                                var47 = 0;
                                                                var16 = 0;
                                                            }

                                                            itemSprite.method2818(var47 + xPos, var16 + yPos, 128);
                                                            if(MouseInputHandler.mouseX * -367052265 >= xPos && MouseInputHandler.mouseX * -367052265 <= (xPos + itemSprite.maxWidth)
                                                                    && MouseInputHandler.mouseY * 1533395117 >= yPos && MouseInputHandler.mouseY * 1533395117 <= (yPos + itemSprite.maxHeight)) {
                                                                //Rasterizer2D.drawFilledRectangle(var21, var12, componentSprite.maxWidth, componentSprite.maxHeight, 0);
                                                                RSFont.p11_full_font.drawBasicString("Type11: " + component.componentType * 942877543, MouseInputHandler.mouseX * -367052265, MouseInputHandler.mouseY * 1533395117);
                                                            }
                                                            if (var1 != -1) {
                                                                RSInterface var50 = components[var1 & '\uffff'];
                                                                if (yPos + var16 < Rasterizer2D.topY && var50.scrollPosition * -643576081 > 0) {
                                                                    var18 = Client.anInt2780 * 468305965 * (Rasterizer2D.topY - yPos - var16) / 3;
                                                                    if (var18 > Client.anInt2780 * 388092354) {
                                                                        var18 = Client.anInt2780 * 388092354;
                                                                    }

                                                                    if (var18 > var50.scrollPosition * -643576081) {
                                                                        var18 = var50.scrollPosition * -643576081;
                                                                    }

                                                                    var50.scrollPosition -= var18 * -1291378673;
                                                                    Client.anInt2903 += var18 * 273785511;
                                                                    MouseInputHandler.method775(var50, -16054773);
                                                                }

                                                                if (var16 + yPos + 32 > Rasterizer2D.bottomY && var50.scrollPosition * -643576081 < var50.anInt1787 * -1108406155 - var50.height * 334099177) {
                                                                    var18 = (var16 + yPos + 32 - Rasterizer2D.bottomY) * Client.anInt2780 * 468305965 / 3;
                                                                    if (var18 > Client.anInt2780 * 388092354) {
                                                                        var18 = Client.anInt2780 * 388092354;
                                                                    }

                                                                    if (var18 > var50.anInt1787 * -1108406155 - var50.height * 334099177 - var50.scrollPosition * -643576081) {
                                                                        var18 = var50.anInt1787 * -1108406155 - var50.height * 334099177 - var50.scrollPosition * -643576081;
                                                                    }

                                                                    var50.scrollPosition += var18 * -1291378673;
                                                                    Client.anInt2903 -= var18 * 273785511;
                                                                    MouseInputHandler.method775(var50, -16054773);
                                                                }
                                                            }
                                                        } else if (PlayerLoginDetails.aClass108_Sub17_75 == component && itemIndex == Client.anInt2815 * -1269538377) {
                                                            itemSprite.method2818(xPos, yPos, 128);
                                                            //if(MouseInputHandler.mouseX * -367052265 >= xPos && MouseInputHandler.mouseX * -367052265 <= (xPos + itemSprite.maxWidth)
                                                            //&& MouseInputHandler.mouseY * 1533395117 >= yPos && MouseInputHandler.mouseY * 1533395117 <= (yPos + itemSprite.maxHeight)) {
                                                            //Rasterizer2D.drawFilledRectangle(var21, var12, componentSprite.maxWidth, componentSprite.maxHeight, 0);
                                                            RSFont.p11_full_font.drawBasicString("Type9: " + component.componentType * 942877543, MouseInputHandler.mouseX * -367052265, MouseInputHandler.mouseY * 1533395117);
                                                            //}
                                                        } else {
                                                            itemSprite.method2746(xPos, yPos);
                                                            //if(MouseInputHandler.mouseX * -367052265 >= xPos && MouseInputHandler.mouseX * -367052265 <= (xPos + itemSprite.maxWidth)
                                                            //&& MouseInputHandler.mouseY * 1533395117 >= yPos && MouseInputHandler.mouseY * 1533395117 <= (yPos + itemSprite.maxHeight)) {
                                                            //Rasterizer2D.drawFilledRectangle(var21, var12, componentSprite.maxWidth, componentSprite.maxHeight, 0);
                                                            RSFont.p11_full_font.drawBasicString("Type10: " + component.componentType * 942877543, MouseInputHandler.mouseX * -367052265, MouseInputHandler.mouseY * 1533395117);
                                                            //}
                                                        }
                                                    } else {
                                                        MouseInputHandler.method775(component, -16054773);
                                                    }
                                                }
                                            } else if (null != component.sprites && itemIndex < 20) {
                                                RGBSprite var53 = component.method1959(itemIndex, 682458473);
                                                if (var53 != null) {
                                                    var53.method2746(xPos, yPos);
                                                    //if(MouseInputHandler.mouseX * -367052265 >= xPos && MouseInputHandler.mouseX * -367052265 <= (xPos + var53.maxWidth)
                                                    //&& MouseInputHandler.mouseY * 1533395117 >= yPos && MouseInputHandler.mouseY * 1533395117 <= (yPos + var53.maxHeight)) {
                                                    //Rasterizer2D.drawFilledRectangle(var21, var12, componentSprite.maxWidth, componentSprite.maxHeight, 0);
                                                    RSFont.p11_full_font.drawBasicString("Type8: " + component.componentType * 942877543, MouseInputHandler.mouseX * -367052265, MouseInputHandler.mouseY * 1533395117);
                                                    //}
                                                } else if (RSInterface.mediaUnavailable) {
                                                    MouseInputHandler.method775(component, -16054773);
                                                }
                                            }

                                            ++itemIndex;
                                        }
                                    }
                                } else if (3 == component.componentType * 942877543) { // draw colored filled/unfilled rectangle
                                    if (GameDefinition.method1103(component, (byte) -28)) {
                                        var22 = component.componentActiveColor * -310727379;
                                        if (component == Client.mouseHoveredComponent && component.mouseOverActiveColor * -1067295333 != 0) {
                                            var22 = component.mouseOverActiveColor * -1067295333;
                                        }
                                    } else {
                                        var22 = component.componentColor * -1484361639;
                                        if (component == Client.mouseHoveredComponent && 0 != component.mouseOverColor * 256440005) {
                                            var22 = component.mouseOverColor * 256440005;
                                        }
                                    }

                                    if (transparency == 0) {
                                        if (component.filled) {
                                            Rasterizer2D.drawFilledRectangle(componentXPos, componentYPos, component.width * -1281443035, component.height * 334099177, var22);
                                        } else {
                                            Rasterizer2D.drawUnfilledRectangle(componentXPos, componentYPos, component.width * -1281443035, component.height * 334099177, var22);
                                        }
                                    } else if (component.filled) {
                                        Rasterizer2D.drawFilledRectangleAlpha(componentXPos, componentYPos, component.width * -1281443035, component.height * 334099177, var22, 256 - (transparency & 255));
                                    } else {
                                        Rasterizer2D.drawUnfilledRectangleAlpha(componentXPos, componentYPos, component.width * -1281443035, component.height * 334099177, var22, 256 - (transparency & 255));
                                    }
                                } else {
                                    RSFont interfaceFont;
                                    if (component.componentType * 942877543 == 4) {
                                        interfaceFont = component.getWidgetFont(192256567);
                                        if (interfaceFont == null) {
                                            if (RSInterface.mediaUnavailable) {
                                                MouseInputHandler.method775(component, -16054773);
                                            }
                                        } else {
                                            String componentString = component.componentString;
                                            if (GameDefinition.method1103(component, (byte) -58)) {
                                                var30 = component.componentActiveColor * -310727379;
                                                if (Client.mouseHoveredComponent == component && component.mouseOverActiveColor * -1067295333 != 0) {
                                                    var30 = component.mouseOverActiveColor * -1067295333;
                                                }

                                                if (component.activeComponentString.length() > 0) {
                                                    componentString = component.activeComponentString;
                                                }
                                            } else {
                                                var30 = component.componentColor * -1484361639;
                                                if (Client.mouseHoveredComponent == component && component.mouseOverColor * 256440005 != 0) {
                                                    var30 = component.mouseOverColor * 256440005;
                                                }
                                            }

                                            if (component.aBool1855 && -1 != component.itemID * 703308511) {
                                                ItemDefinition itemDef = ItemDefinition.getItemDefinition(component.itemID * 703308511, -2007508026);
                                                componentString = itemDef.name;
                                                if (null == componentString) {
                                                    componentString = "null";
                                                }

                                                if ((1 == itemDef.stackable * 1548462817 || component.anInt1775 * 1939717269 != 1) && -1 != component.anInt1775 * 1939717269) {
                                                    componentString = HuffmanEncoding.method690(16748608, -1895881530) + componentString + Class47.COL_END + " " + 'x' + Player.method3177(component.anInt1775 * 1939717269, (byte) 36);
                                                }
                                            }

                                            if (Client.aClass108_Sub17_2869 == component) {
                                                StringUtilities var10000 = (StringUtilities) null;
                                                componentString = StringUtilities.PLEASE_WAIT;
                                                var30 = component.componentColor * -1484361639;
                                            }

                                            if (!component.aBool1855) {
                                                componentString = Class2.method40(componentString, component, 586397057);
                                            }

                                            interfaceFont.method3097(componentString, componentXPos, componentYPos, component.width * -1281443035, component.height * 334099177, var30, component.textCentered ? 0 : -1, component.anInt1863 * -164762721, component.anInt1862 * 1189093849, component.anInt1820 * -861430413);
                                        }
                                    } else if (component.componentType * 942877543 == 5) {
                                        RGBSprite componentSprite;
                                        if (!component.aBool1855) {
                                            componentSprite = component.method1938(GameDefinition.method1103(component, (byte) 98), 301049466);
                                            if (null != componentSprite) {
                                                componentSprite.method2746(componentXPos, componentYPos);
                                                if(MouseInputHandler.mouseX * -367052265 >= componentXPos && MouseInputHandler.mouseX * -367052265 <= (componentXPos + componentSprite.maxWidth)
                                                        && MouseInputHandler.mouseY * 1533395117 >= componentYPos && MouseInputHandler.mouseY * 1533395117 <= (componentYPos + componentSprite.maxHeight)) {
                                                    //Rasterizer2D.drawFilledRectangle(var21, var12, componentSprite.maxWidth, componentSprite.maxHeight, 0);
                                                    RSFont.p11_full_font.drawBasicString("Type0: " + component.componentType * 942877543, MouseInputHandler.mouseX * -367052265, MouseInputHandler.mouseY * 1533395117);
                                                }
                                            } else if (RSInterface.mediaUnavailable) {
                                                MouseInputHandler.method775(component, -16054773);
                                            }
                                        } else {
                                            if (component.itemID * 703308511 != -1) {
                                                componentSprite = ItemDefinition.getItemSprite(component.itemID * 703308511, component.anInt1775 * 1939717269, component.anInt1799 * 411719917, component.anInt1800 * -563686383, false, -364961531);
                                            } else {
                                                componentSprite = component.method1938(false, 301049466);
                                            }

                                            if (null == componentSprite) {
                                                if (RSInterface.mediaUnavailable) {
                                                    MouseInputHandler.method775(component, -16054773);
                                                }
                                            } else {
                                                var30 = componentSprite.maxWidth;
                                                var15 = componentSprite.maxHeight;
                                                if (!component.aBool1798) {
                                                    var20 = component.width * -340635648 / var30;
                                                    if (0 != component.anInt1797 * 879589047) {
                                                        componentSprite.method2766(component.width * -1281443035 / 2 + componentXPos, component.height * 334099177 / 2 + componentYPos, component.anInt1797 * 879589047, var20);
                                                        if(MouseInputHandler.mouseX * -367052265 >= componentXPos && MouseInputHandler.mouseX * -367052265 <= (componentXPos + componentSprite.maxWidth)
                                                                && MouseInputHandler.mouseY * 1533395117 >= componentYPos && MouseInputHandler.mouseY * 1533395117 <= (componentYPos + componentSprite.maxHeight)) {
                                                            //Rasterizer2D.drawFilledRectangle(var21, var12, componentSprite.maxWidth, componentSprite.maxHeight, 0);
                                                            //RSFont.p11_full_font.drawBasicString("Type1: " + component.componentType * 942877543, MouseInputHandler.mouseX * -367052265, MouseInputHandler.mouseY * 1533395117);
                                                        }
                                                    } else if (0 != transparency) {
                                                        componentSprite.method2762(componentXPos, componentYPos, component.width * -1281443035, component.height * 334099177, 256 - (transparency & 255));
                                                        if(MouseInputHandler.mouseX * -367052265 >= componentXPos && MouseInputHandler.mouseX * -367052265 <= (componentXPos + componentSprite.maxWidth)
                                                                && MouseInputHandler.mouseY * 1533395117 >= componentYPos && MouseInputHandler.mouseY * 1533395117 <= (componentYPos + componentSprite.maxHeight)) {
                                                            //Rasterizer2D.drawFilledRectangle(var21, var12, componentSprite.maxWidth, componentSprite.maxHeight, 0);
                                                            //RSFont.p11_full_font.drawBasicString("Type2: " + component.componentType * 942877543, MouseInputHandler.mouseX * -367052265, MouseInputHandler.mouseY * 1533395117);
                                                        }
                                                    } else if (var30 == component.width * -1281443035 && component.height * 334099177 == var15) {
                                                        componentSprite.method2746(componentXPos, componentYPos);
                                                        //System.out.println("hello");
                                                        if(MouseInputHandler.mouseX * -367052265 >= componentXPos && MouseInputHandler.mouseX * -367052265 <= (componentXPos + componentSprite.maxWidth)
                                                                && MouseInputHandler.mouseY * 1533395117 >= componentYPos && MouseInputHandler.mouseY * 1533395117 <= (componentYPos + componentSprite.maxHeight)) {
                                                            //Rasterizer2D.drawFilledRectangle(var21, var12, componentSprite.maxWidth, componentSprite.maxHeight, 0);
                                                            //RSFont.p11_full_font.drawBasicString("Type3: " + component.componentType * 942877543, MouseInputHandler.mouseX * -367052265, MouseInputHandler.mouseY * 1533395117);

                                                        }
                                                    } else {
                                                        componentSprite.method2756(componentXPos, componentYPos, component.width * -1281443035, component.height * 334099177);
                                                        //System.out.println("hello");
                                                        if(MouseInputHandler.mouseX * -367052265 >= componentXPos && MouseInputHandler.mouseX * -367052265 <= (componentXPos + componentSprite.maxWidth)
                                                                && MouseInputHandler.mouseY * 1533395117 >= componentYPos && MouseInputHandler.mouseY * 1533395117 <= (componentYPos + componentSprite.maxHeight)) {
                                                            //Rasterizer2D.drawFilledRectangle(var21, var12, componentSprite.maxWidth, componentSprite.maxHeight, 0);
                                                            //RSFont.p11_full_font.drawBasicString("Type4: " + component.componentType * 942877543, MouseInputHandler.mouseX * -367052265, MouseInputHandler.mouseY * 1533395117);
                                                        }
                                                    }
                                                } else {
                                                    Rasterizer2D.setRasterizerArea(componentXPos, componentYPos, component.width * -1281443035 + componentXPos, component.height * 334099177 + componentYPos);
                                                    var20 = (var30 - 1 + component.width * -1281443035) / var30;
                                                    var41 = (component.height * 334099177 + (var15 - 1)) / var15;

                                                    for (var47 = 0; var47 < var20; ++var47) {
                                                        for (var16 = 0; var16 < var41; ++var16) {
                                                            if (component.anInt1797 * 879589047 != 0) {
                                                                componentSprite.method2766(var30 / 2 + componentXPos + var30 * var47, var15 / 2 + componentYPos + var16 * var15, component.anInt1797 * 879589047, 4096);
                                                                if(MouseInputHandler.mouseX * -367052265 >= componentXPos && MouseInputHandler.mouseX * -367052265 <= (componentXPos + componentSprite.maxWidth)
                                                                        && MouseInputHandler.mouseY * 1533395117 >= componentYPos && MouseInputHandler.mouseY * 1533395117 <= (componentYPos + componentSprite.maxHeight)) {
                                                                    RSFont.p11_full_font.drawBasicString("Type5: " + component.componentType * 942877543, MouseInputHandler.mouseX * -367052265, MouseInputHandler.mouseY * 1533395117);
                                                                }
                                                            } else if (0 != transparency) {
                                                                componentSprite.method2818(var47 * var30 + componentXPos, componentYPos + var15 * var16, 256 - (transparency & 255));
                                                                if(MouseInputHandler.mouseX * -367052265 >= componentXPos && MouseInputHandler.mouseX * -367052265 <= (componentXPos + componentSprite.maxWidth)
                                                                        && MouseInputHandler.mouseY * 1533395117 >= componentYPos && MouseInputHandler.mouseY * 1533395117 <= (componentYPos + componentSprite.maxHeight)) {
                                                                    RSFont.p11_full_font.drawBasicString("Type6: " + component.componentType * 942877543, MouseInputHandler.mouseX * -367052265, MouseInputHandler.mouseY * 1533395117);
                                                                }
                                                            } else {
                                                                componentSprite.method2746(var30 * var47 + componentXPos, componentYPos + var15 * var16);
                                                                if(MouseInputHandler.mouseX * -367052265 >= componentXPos && MouseInputHandler.mouseX * -367052265 <= (componentXPos + componentSprite.maxWidth)
                                                                        && MouseInputHandler.mouseY * 1533395117 >= componentYPos && MouseInputHandler.mouseY * 1533395117 <= (componentYPos + componentSprite.maxHeight)) {
                                                                    RSFont.p11_full_font.drawBasicString("Type7: " + component.componentType * 942877543, MouseInputHandler.mouseX * -367052265, MouseInputHandler.mouseY * 1533395117);
                                                                }
                                                            }
                                                        }
                                                    }

                                                    Rasterizer2D.setRasterizationRect(xRender, yRender, renderWidth, renderHeight);
                                                }
                                            }
                                        }
                                    } else {
                                        ItemDefinition itemDef;
                                        if (component.componentType * 942877543 == 6) { // media
                                            boolean var48 = GameDefinition.method1103(component, (byte) -14);
                                            if (var48) {
                                                var30 = component.activeMediaAnimID * -1534484025;
                                            } else {
                                                var30 = component.mediaAnimID * 866704807;
                                            }

                                            ModelRasterizer rasterizer = null;
                                            var20 = 0;
                                            if (component.itemID * 703308511 != -1) {
                                                itemDef = ItemDefinition.getItemDefinition(component.itemID * 703308511, -2022329629);
                                                if (itemDef != null) {
                                                    itemDef = itemDef.transformItemByAmount(component.anInt1775 * 1939717269, 108017835);
                                                    rasterizer = itemDef.renderItem(1, (byte) -116);
                                                    if (null != rasterizer) {
                                                        rasterizer.method2855();
                                                        var20 = rasterizer.modelHeight * 782517621 / 2;
                                                    } else {
                                                        MouseInputHandler.method775(component, -16054773);
                                                    }
                                                }
                                            } else if (component.mediaType * -1873872195 == 5) { // player
                                                if (component.mediaID * 2030124439 == 0) {
                                                    rasterizer = Client.aClass93_2926.getModelRasterizer((AnimationDefinition) null, -1, (AnimationDefinition) null, -1, -1132359552);
                                                } else {
                                                    rasterizer = Player.myPlayer.getModelRasterizer((byte) 62);
                                                }
                                            } else if (var30 == -1) {
                                                rasterizer = component.getInterfaceMediaRasterizer((AnimationDefinition) null, -1, var48, Player.myPlayer.bodyEquipmentKit, -1286694344);
                                                if (null == rasterizer && RSInterface.mediaUnavailable) {
                                                    MouseInputHandler.method775(component, -16054773);
                                                }
                                            } else {
                                                AnimationDefinition var43 = AnimationDefinition.getAnimDefForID(var30, 1658041042);
                                                rasterizer = component.getInterfaceMediaRasterizer(var43, component.mediaFrameID * -1365409805, var48, Player.myPlayer.bodyEquipmentKit, -1695743692);
                                                if (rasterizer == null && RSInterface.mediaUnavailable) {
                                                    MouseInputHandler.method775(component, -16054773);
                                                }
                                            }

                                            Rasterizer3D.method2926(componentXPos + component.width * -1281443035 / 2, componentYPos + component.height * 334099177 / 2);
                                            var41 = component.mediaZoom * -1750235537 * Rasterizer3D.SINE[component.mediaRotationX * -272801613] >> 16;
                                            var47 = component.mediaZoom * -1750235537 * Rasterizer3D.COSINE[component.mediaRotationX * -272801613] >> 16;
                                            if (null != rasterizer) {
                                                if (!component.aBool1855) {
                                                    rasterizer.method2916(0, component.mediaRotationY * 260082763, 0, component.mediaRotationX * -272801613, 0, var41, var47);
                                                } else {
                                                    rasterizer.method2855();
                                                    if (component.aBool1816) {
                                                        rasterizer.renderSingle(0, component.mediaRotationY * 260082763, component.anInt1813 * -1361484521, component.mediaRotationX * -272801613, component.anInt1809 * 2076437491, var41 + var20 + component.anInt1810 * -2001912439, var47 + component.anInt1810 * -2001912439, component.mediaZoom * -1750235537);
                                                    } else {
                                                        rasterizer.method2916(0, component.mediaRotationY * 260082763, component.anInt1813 * -1361484521, component.mediaRotationX * -272801613, component.anInt1809 * 2076437491, component.anInt1810 * -2001912439 + var41 + var20, var47 + component.anInt1810 * -2001912439);
                                                    }
                                                }
                                            }

                                            Rasterizer3D.method2925();
                                        } else {
                                            if (7 == component.componentType * 942877543) {
                                                interfaceFont = component.getWidgetFont(192256567);
                                                if (null == interfaceFont) {
                                                    if (RSInterface.mediaUnavailable) {
                                                        MouseInputHandler.method775(component, -16054773);
                                                    }
                                                    continue;
                                                }

                                                var30 = 0;

                                                for (var15 = 0; var15 < component.height * 334099177; ++var15) {
                                                    for (var20 = 0; var20 < component.width * -1281443035; ++var20) {
                                                        if (component.widgetItems[var30] > 0) {
                                                            itemDef = ItemDefinition.getItemDefinition(component.widgetItems[var30] - 1, -1172020220);
                                                            String var23;
                                                            if (1 != itemDef.stackable * 1548462817 && component.widgetItemAmounts[var30] == 1) {
                                                                var23 = HuffmanEncoding.method690(16748608, -995591797) + itemDef.name + Class47.COL_END;
                                                            } else {
                                                                var23 = HuffmanEncoding.method690(16748608, -2080841110) + itemDef.name + Class47.COL_END + " " + 'x' + Player.method3177(component.widgetItemAmounts[var30], (byte) 81);
                                                            }

                                                            var16 = (component.widgetItemPaddingX * 876962455 + 115) * var20 + componentXPos;
                                                            var10 = componentYPos + var15 * (component.widgetItemPaddingY * -448462053 + 12);
                                                            if (component.anInt1863 * -164762721 == 0) {
                                                                interfaceFont.drawString(var23, var16, var10, component.componentColor * -1484361639, component.textCentered ? 0 : -1);
                                                            } else if (component.anInt1863 * -164762721 == 1) {
                                                                interfaceFont.drawStringCenter(var23, var16 + component.width * -1281443035 / 2, var10, component.componentColor * -1484361639, component.textCentered ? 0 : -1);
                                                            } else {
                                                                interfaceFont.drawStringRight(var23, var16 + component.width * -1281443035 - 1, var10, component.componentColor * -1484361639, component.textCentered ? 0 : -1);
                                                            }
                                                        }

                                                        ++var30;
                                                    }
                                                }
                                            }

                                            int var32;
                                            if (8 == component.componentType * 942877543 && component == NPCDefinition.aClass108_Sub17_2193 && Client.anInt2856 * 1284119235 == Client.anInt2857 * -1887483549) {
                                                var22 = 0;
                                                var30 = 0;
                                                RSFont p12_full = ObjectDefinition.p12_full_font;
                                                String var44 = component.componentString;

                                                String var38;
                                                for (var44 = Class2.method40(var44, component, -1385023527); var44.length() > 0; var30 += 1 + p12_full.anInt2643) {
                                                    var47 = var44.indexOf(Class47.LINE_BREAK);
                                                    if (var47 != -1) {
                                                        var38 = var44.substring(0, var47);
                                                        var44 = var44.substring(var47 + 4);
                                                    } else {
                                                        var38 = var44;
                                                        var44 = "";
                                                    }

                                                    var16 = p12_full.getTextWidth(var38);
                                                    if (var16 > var22) {
                                                        var22 = var16;
                                                    }
                                                }

                                                var22 += 6;
                                                var30 += 7;
                                                var47 = componentXPos + component.width * -1281443035 - 5 - var22;
                                                var16 = componentYPos + component.height * 334099177 + 5;
                                                if (var47 < 5 + componentXPos) {
                                                    var47 = 5 + componentXPos;
                                                }

                                                if (var22 + var47 > renderWidth) {
                                                    var47 = renderWidth - var22;
                                                }

                                                if (var16 + var30 > renderHeight) {
                                                    var16 = renderHeight - var30;
                                                }

                                                Rasterizer2D.drawFilledRectangle(var47, var16, var22, var30, 16777120);
                                                Rasterizer2D.drawUnfilledRectangle(var47, var16, var22, var30, 0);
                                                var44 = component.componentString;
                                                var10 = var16 + p12_full.anInt2643 + 2;

                                                for (var44 = Class2.method40(var44, component, 1044987450); var44.length() > 0; var10 += 1 + p12_full.anInt2643) {
                                                    var32 = var44.indexOf(Class47.LINE_BREAK);
                                                    if (var32 != -1) {
                                                        var38 = var44.substring(0, var32);
                                                        var44 = var44.substring(var32 + 4);
                                                    } else {
                                                        var38 = var44;
                                                        var44 = "";
                                                    }

                                                    p12_full.drawString(var38, 3 + var47, var10, 0, -1);
                                                }
                                            }

                                            if (component.componentType * 942877543 == 9) {
                                                if (1 == component.anInt1811 * -1974296291) {
                                                    Rasterizer2D.method2510(componentXPos, componentYPos, componentXPos + component.width * -1281443035, componentYPos + component.height * 334099177, component.componentColor * -1484361639);
                                                } else {
                                                    var22 = component.width * -1281443035 >= 0 ? component.width * -1281443035 : -(component.width * -1281443035);
                                                    var30 = component.height * 334099177 >= 0 ? component.height * 334099177 : -(component.height * 334099177);
                                                    var15 = var22;
                                                    if (var22 < var30) {
                                                        var15 = var30;
                                                    }

                                                    if (var15 != 0) {
                                                        var20 = (component.width * -1281443035 << 16) / var15;
                                                        var41 = (component.height * 334099177 << 16) / var15;
                                                        if (var41 <= var20) {
                                                            var20 = -var20;
                                                        } else {
                                                            var41 = -var41;
                                                        }

                                                        var47 = var41 * component.anInt1811 * -1974296291 >> 17;
                                                        var16 = var41 * component.anInt1811 * -1974296291 + 1 >> 17;
                                                        var10 = var20 * component.anInt1811 * -1974296291 >> 17;
                                                        var32 = component.anInt1811 * -1974296291 * var20 + 1 >> 17;
                                                        int var27 = componentXPos + var47;
                                                        var18 = componentXPos - var16;
                                                        int var33 = componentXPos + component.width * -1281443035 - var16;
                                                        int var26 = componentXPos + component.width * -1281443035 + var47;
                                                        int var34 = componentYPos + var10;
                                                        int var36 = componentYPos - var32;
                                                        int var35 = componentYPos + component.height * 334099177 - var32;
                                                        int var13 = var10 + component.height * 334099177 + componentYPos;
                                                        Rasterizer3D.method2931(var27, var18, var33);
                                                        Rasterizer3D.drawFlatTriangle(var34, var36, var35, var27, var18, var33, component.componentColor * -1484361639);
                                                        Rasterizer3D.method2931(var27, var33, var26);
                                                        Rasterizer3D.drawFlatTriangle(var34, var35, var13, var27, var33, var26, component.componentColor * -1484361639);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if(MouseInputHandler.mouseX * -367052265 > componentXPos && MouseInputHandler.mouseX * -367052265 <= componentXPos + renderWidth &&
                                MouseInputHandler.mouseY * 1533395117 >= componentYPos && MouseInputHandler.mouseY * 1533395117 <= componentYPos + renderHeight) {
                            debugCount++;
                            RSFont.p11_full_font.drawBasicString("T: " + component.componentType * 942877543 + " P: " + component.getParentID() + " C:" + component.getComponentID(), MouseInputHandler.mouseX * -367052265, MouseInputHandler.mouseY * 1533395117 + (15 * debugCount));
                        }
                    }
                }
            }
            if(componentIndex == 104 && parentID == 548 && !subcomp)
                componentIndex = -1;
        }

    }*/


}
