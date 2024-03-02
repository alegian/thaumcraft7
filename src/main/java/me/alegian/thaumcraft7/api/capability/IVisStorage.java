package me.alegian.thaumcraft7.api.capability;

public interface IVisStorage {
    float extractVis(float maxExtract);

    float receiveVis(float maxReceive);

    float getVisStored();

    float getMaxVisStored();
}
