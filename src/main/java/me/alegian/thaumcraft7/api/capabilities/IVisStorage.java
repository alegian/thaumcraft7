package me.alegian.thaumcraft7.api.capabilities;

public interface IVisStorage {
    float extractVis(float maxExtract);

    float receiveVis(float maxReceive);

    float getVisStored();

    float getMaxVisStored();
}
