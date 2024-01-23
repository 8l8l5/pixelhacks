package dev.tapwatero.pixelmod.listener;

import com.google.common.collect.Sets;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;



public class PokemonTickEvent {

    int range = 512;
    long ticks = 0;
    public static ArrayList<Entity> hits = new ArrayList<>();
    public static final Set<String> targets = Sets.newHashSet();



    public boolean isTarget(Entity entity) {
        PixelmonEntity pixelmon = (PixelmonEntity) entity;

        return (targets.contains(pixelmon.getPokemon().getDisplayName()) && !hits.contains(entity));
    }



    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        ticks += 1;

        assert Minecraft.getInstance().level != null;
        if (Minecraft.getInstance().player == null) {
            return;
        }


        if (ticks % 2 == 0) {


            Vector3d position = Minecraft.getInstance().player.position();
            PokemonTickEvent.hits.removeIf(entity -> entity.distanceToSqr(Minecraft.getInstance().player) > (range * range) || !entity.isAlive());

            Minecraft.getInstance().level.getLoadedEntitiesOfClass(PixelmonEntity.class, new AxisAlignedBB(position.x - range, position.y - range, position.z - range, position.x + range, position.y + range, position.z + range)).forEach(entity -> {
                if (isTarget(entity)) {

                    int[] pos = new int[]{(int) entity.getX(), (int) entity.getY(), (int) entity.getZ()};
                    Minecraft.getInstance().player.sendMessage(new StringTextComponent(TextFormatting.RED + "" + entity.getPokemon().getDisplayName() + " - " + Arrays.toString(pos) + " - " + entity.getPokemon().getAbilityName()), Util.NIL_UUID);
                    Minecraft.getInstance().player.playSound(SoundEvents.ARROW_HIT_PLAYER, 5f, 1f);


                    hits.add(entity);
                }
            });
        }
    }
}
