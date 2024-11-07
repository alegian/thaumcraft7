package me.alegian.thavma.impl.init.registries.deferred;

import me.alegian.thavma.impl.Thavma;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.common.BooleanAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class T7Attributes {
  public static final DeferredRegister<Attribute> REGISTRAR = DeferredRegister.create(Registries.ATTRIBUTE, Thavma.MODID);

  public static final DeferredHolder<Attribute, Attribute> REVEALING = REGISTRAR.register("revealing", (key) ->
      new BooleanAttribute(
          Util.makeDescriptionId(Registries.ATTRIBUTE.location().getPath(), key),
          false
      ).setSyncable(true)
  );
}
