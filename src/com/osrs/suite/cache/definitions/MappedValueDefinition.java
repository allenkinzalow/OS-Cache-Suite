package com.osrs.suite.cache.definitions;

import com.alex.io.InputStream;
import com.alex.io.OutputStream;
import com.osrs.suite.utilities.StringUtilities;

/**
 * Created by Allen Kinzalow on 6/18/2015.
 */
public class MappedValueDefinition extends Definition {

    public final static int INDEX_ID = 2;
    public final static int ARCHIVE_ID = 8;

    public int csMapIntIdentifier;
    public char aChar2053;
    public char aChar2055;
    public String csMapStringIdentifier = "null";
    public int[] csMapIdentifier;
    public int csMapValueCount = 0;
    public String[] csMapStringValues;
    public int[] csMapIntValues;

    public MappedValueDefinition(int definitionID) {
        super(definitionID);
    }

    @Override
    OutputStream encode(OutputStream stream) {
        return null;
    }

    @Override
    void decodeValues(int opcode, InputStream stream) {
        if(1 == opcode) {
            this.aChar2053 = (char)stream.readUnsignedByte();
        } else if(2 == opcode) {
            this.aChar2055 = (char)stream.readUnsignedByte();
        } else if(3 == opcode) {
            this.csMapStringIdentifier = StringUtilities.readString_2(stream);
        } else if(4 == opcode) {
            this.csMapIntIdentifier = stream.readInt();
        } else {
            int var4;
            if(opcode == 5) { // id
                this.csMapValueCount = stream.readUnsignedShort();
                this.csMapIdentifier = new int[this.csMapValueCount];
                this.csMapStringValues = new String[this.csMapValueCount];

                for(var4 = 0; var4 < this.csMapValueCount; ++var4) {
                    this.csMapIdentifier[var4] = stream.readInt();
                    this.csMapStringValues[var4] = StringUtilities.readString_2(stream);
                }

            } else if(opcode == 6) {
                this.csMapValueCount = stream.readUnsignedShort();
                this.csMapIdentifier = new int[this.csMapValueCount];
                this.csMapIntValues = new int[this.csMapValueCount];

                for(var4 = 0; var4 < this.csMapValueCount; ++var4) {
                    this.csMapIdentifier[var4] = stream.readInt();
                    this.csMapIntValues[var4] = stream.readInt();
                }

            }
        }
    }

    @Override
    public void printDefinition() {
        System.out.println();
        System.out.println("[MapID: " + this.definitionID + " StringID: " + this.csMapStringIdentifier + " IntID: " + this.csMapIntIdentifier);
        System.out.println("\tMapValue Count: " + this.csMapValueCount);
        for(int index = 0; index < csMapValueCount; index++) {
            if(csMapStringValues != null)
                System.out.println("\t\tValue: [" + csMapIdentifier[index] + ", " + csMapStringValues[index]);
            else if(csMapIntValues != null)
                System.out.println("\t\tValue: [" + csMapIdentifier[index] + ", " + csMapIntValues[index]);
        }
        System.out.println("---------");
    }
}
