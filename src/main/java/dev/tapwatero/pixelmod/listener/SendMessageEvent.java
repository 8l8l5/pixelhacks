package dev.tapwatero.pixelmod.listener;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.NewChatGui;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CEditBookPacket;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.lang3.StringUtils;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;

import static dev.tapwatero.pixelmod.listener.PokemonTickEvent.hits;
import static dev.tapwatero.pixelmod.listener.PokemonTickEvent.targets;

public class SendMessageEvent {

// working on book dupe
//    private static final ItemStack DUPE_BOOK;


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void sendMessage(ClientChatEvent event) {

        String[] command = event.getMessage().split(" ");
        String[] commands = event.getMessage().split("add, remove, random, list, clear");


        if (command[0].startsWith("?") || command[0].startsWith(":")) {
            event.setCanceled(true);
        }

        if (command.length == 2) {
            String arg = command[1].toLowerCase();

            switch (command[0].toLowerCase()) {

                case ":add":


                    if (PixelmonSpecies.get(arg).isPresent() && !PokemonTickEvent.targets.contains(arg)) {
                        Minecraft.getInstance().player.sendMessage(new StringTextComponent(TextFormatting.GREEN + StringUtils.capitalize(arg) + " added to targets!"), Util.NIL_UUID);
                        PokemonTickEvent.targets.add(StringUtils.capitalize(command[1]));
                    } else if (!PixelmonSpecies.get(arg).isPresent()) {
                        Minecraft.getInstance().player.sendMessage(new StringTextComponent(TextFormatting.RED + "Pokemon does not exist."), Util.NIL_UUID);
                    } else if (PixelmonSpecies.get(arg).isPresent() && PokemonTickEvent.targets.contains(arg)) {
                        Minecraft.getInstance().player.sendMessage(new StringTextComponent(TextFormatting.RED + "Pokemon already in targets."), Util.NIL_UUID);
                    }
                    break;
                case ":remove":

                    if (PokemonTickEvent.targets.contains(arg)) {
                        Minecraft.getInstance().player.sendMessage(new StringTextComponent(TextFormatting.GREEN + StringUtils.capitalize(arg) + " removed from targets."), Util.NIL_UUID);
                        PokemonTickEvent.targets.remove(StringUtils.capitalize(arg));
                        PokemonTickEvent.hits.clear();

                    } else {
                        Minecraft.getInstance().player.sendMessage(new StringTextComponent(TextFormatting.RED + arg + " not in targets."), Util.NIL_UUID);
                    }
                    break;
                case ":random":
                    Minecraft.getInstance().player.sendMessage(new StringTextComponent(String.valueOf(Minecraft.getInstance().level.players().size())), Util.NIL_UUID);
                    break;
            }

        } else if (command[0].equals(":list")) {
            Minecraft.getInstance().player.sendMessage(new StringTextComponent(TextFormatting.GREEN + "" + TextFormatting.UNDERLINE + "" + TextFormatting.BOLD + "Targets"), Util.NIL_UUID);
            Minecraft.getInstance().player.sendMessage(new StringTextComponent(TextFormatting.GREEN + String.join(", ", PokemonTickEvent.targets)), Util.NIL_UUID);

        } else if (command[0].equals(":clear")) {
            Minecraft.getInstance().player.sendMessage(new StringTextComponent(TextFormatting.GREEN + "Targets cleared."), Util.NIL_UUID);
            PokemonTickEvent.targets.clear();
            PokemonTickEvent.hits.clear();

        } else if (command[0].equals(":commands")) {
            Minecraft.getInstance().player.sendMessage(new StringTextComponent(TextFormatting.GREEN + commands[0]), Util.NIL_UUID);
// do not edit
//        } else if (command[0].equals(":d")) {
//            ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
//
//            if (player.getMainHandItem().getItem() == Items.WRITABLE_BOOK)
//            {
//                IPacket<?> packet = new CEditBookPacket(DUPE_BOOK, true, player.inventory.selected);
//                player.connection.send(packet);
//            }
//
//            event.setCanceled(true);
//        }
//    }
//
//    static
//    {
//        StringBuilder stringBuilder = new StringBuilder();
//
//        for (int i = 0; i < 21845; i++)
//        {
//            stringBuilder.append((char) 2048);
//        }
//
//        String str1 = stringBuilder.toString();
//        DUPE_BOOK = new ItemStack(Items.WRITABLE_BOOK, 1);
//        DUPE_BOOK.addTagElement("title", StringNBT.valueOf("a"));
//        ListNBT listTag = new ListNBT();
//        listTag.addTag(0, StringNBT.valueOf(str1));
//
//        for (int i = 1; i < 40; i++)
//        {
//            listTag.addTag(i, StringNBT.valueOf(
//                    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
//        }
//
//        DUPE_BOOK.addTagElement("pages", listTag);
//
//
        }
    }
}