package com.github.shynixn.blockball.bukkit.logic.business.service

import com.github.shynixn.blockball.api.business.enumeration.Version
import com.github.shynixn.blockball.api.business.proxy.PluginProxy
import com.github.shynixn.blockball.api.business.service.ScreenMessageService
import com.github.shynixn.blockball.bukkit.logic.business.extension.convertChatColors
import com.github.shynixn.blockball.bukkit.logic.business.extension.findClazz
import com.github.shynixn.blockball.bukkit.logic.business.extension.sendPacket
import com.google.inject.Inject
import org.bukkit.entity.Player

/**
 * Created by Shynixn 2018.
 * <p>
 * Version 1.2
 * <p>
 * MIT License
 * <p>
 * Copyright (c) 2018 by Shynixn
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
class ScreenMessageServiceImpl @Inject constructor(private val plugin : PluginProxy) : ScreenMessageService {
    /**
     * Sets the [title] of the given [player] [P] for the amount of [stay] ticks. Optionally shows a [subTitle] and displays
     * a [fadeIn] and [fadeOut] effect in ticks.
     */
    override fun <P> setTitle(player: P, title: String, subTitle: String, fadeIn: Int, stay: Int, fadeOut: Int) {
        if (player !is Player) {
            throw IllegalArgumentException("Player has to be a BukkitPlayer!")
        }

        val finalTitle = title.convertChatColors()
        val finalSubTitle = subTitle.convertChatColors()
        val version = plugin.getServerVersion()

        val serializerMethod = if (version.isVersionSameOrGreaterThan(Version.VERSION_1_8_R2)) {
            findClazz("net.minecraft.server.VERSION.IChatBaseComponent\$ChatSerializer", plugin).getDeclaredMethod("a", String::class.java)
        } else {
            findClazz("net.minecraft.server.VERSION.ChatSerializer", plugin).getDeclaredMethod("a", String::class.java)
        }

        val chatBaseComponentClazz = findClazz("net.minecraft.server.VERSION.IChatBaseComponent", plugin)

        val titleActionClazz = if (version.isVersionSameOrGreaterThan(Version.VERSION_1_8_R2)) {
            findClazz("net.minecraft.server.VERSION.PacketPlayOutTitle\$EnumTitleAction", plugin)
        } else {
            findClazz("net.minecraft.server.VERSION.EnumTitleAction", plugin)
        }

        val packetConstructor = findClazz("net.minecraft.server.VERSION.PacketPlayOutTitle", plugin).getDeclaredConstructor(titleActionClazz, chatBaseComponentClazz, Int::class.java, Int::class.java, Int::class.java)

        if (!finalTitle.isEmpty()) {
            val titleJson = serializerMethod.invoke(null, "{\"text\": \"$finalTitle\"}")

            @Suppress("UPPER_BOUND_VIOLATED", "UNCHECKED_CAST")
            val enumTitleValue = java.lang.Enum.valueOf<Any>(titleActionClazz as Class<Any>, "TITLE")
            @Suppress("UPPER_BOUND_VIOLATED", "UNCHECKED_CAST")
            val enumTimesValue = java.lang.Enum.valueOf<Any>(titleActionClazz, "TIMES")

            val titlePacket = packetConstructor.newInstance(enumTitleValue, titleJson, fadeIn, stay, fadeOut)
            val lengthPacket = packetConstructor.newInstance(enumTimesValue, titleJson, fadeIn, stay, fadeOut)

            player.sendPacket(titlePacket)
            player.sendPacket(lengthPacket)
        }

        if (!finalSubTitle.isEmpty()) {
            val subTitleJson = serializerMethod.invoke(null, "{\"text\": \"$finalSubTitle\"}")

            @Suppress("UPPER_BOUND_VIOLATED", "UNCHECKED_CAST")
            val enumSubTitleValue = java.lang.Enum.valueOf<Any>(titleActionClazz as Class<Any>, "SUBTITLE")

            val subIitlePacket = packetConstructor.newInstance(enumSubTitleValue, subTitleJson, fadeIn, stay, fadeOut)

            player.sendPacket(subIitlePacket)
        }
    }

    /**
     * Sets the [message] for the given [player] at the actionbar.
     */
    override fun <P> setActionBar(player: P, message: String) {
        if (player !is Player) {
            throw IllegalArgumentException("Player has to be a BukkitPlayer!")
        }

        val finalMessage = message.convertChatColors()
        val packet: Any?
        val version = plugin.getServerVersion()

        if (version.isVersionSameOrGreaterThan(Version.VERSION_1_12_R1)) {
            val chatBaseComponentClazz = findClazz("net.minecraft.server.VERSION.IChatBaseComponent", plugin)
            val chatMessageClazz = findClazz("net.minecraft.server.VERSION.ChatMessageType", plugin)
            val chatMessageType = chatMessageClazz.getDeclaredMethod("a", Byte::class.java).invoke(null, 2.toByte())
            val packetConstructor = findClazz("net.minecraft.server.VERSION.PacketPlayOutChat", plugin).getDeclaredConstructor(chatBaseComponentClazz, chatMessageClazz)
            val serializerMethod = if (version.isVersionSameOrGreaterThan(Version.VERSION_1_8_R2)) {
                findClazz("net.minecraft.server.VERSION.IChatBaseComponent\$ChatSerializer", plugin).getDeclaredMethod("a", String::class.java)
            } else {
                findClazz("net.minecraft.server.VERSION.ChatSerializer", plugin).getDeclaredMethod("a", String::class.java)
            }

            val messageJSON = serializerMethod.invoke(null, "{\"text\": \"$finalMessage\"}")

            packet = packetConstructor.newInstance(messageJSON, chatMessageType)
        } else {
            val chatBaseComponentClazz = findClazz("net.minecraft.server.VERSION.IChatBaseComponent", plugin)
            val packetConstructor = findClazz("net.minecraft.server.VERSION.PacketPlayOutChat", plugin).getDeclaredConstructor(chatBaseComponentClazz, Byte::class.java)
            val serializerMethod = if (version.isVersionSameOrGreaterThan(Version.VERSION_1_8_R2)) {
                findClazz("net.minecraft.server.VERSION.IChatBaseComponent\$ChatSerializer", plugin).getDeclaredMethod("a", String::class.java)
            } else {
                findClazz("net.minecraft.server.VERSION.ChatSerializer", plugin).getDeclaredMethod("a", String::class.java)
            }

            val messageJSON = serializerMethod.invoke(null, "{\"text\": \"$finalMessage\"}")

            packet = packetConstructor.newInstance(messageJSON, 2.toByte())
        }

        player.sendPacket(packet)
    }

    /**
     * Sets the [header] and [footer] of the given [player] tab bar.
     */
    override fun <P> setTabBar(player: P, header: String, footer: String) {
        if (player !is Player) {
            throw IllegalArgumentException("Player has to be a BukkitPlayer!")
        }

        val finalHeader = header.convertChatColors()
        val finalFooter = footer.convertChatColors()
        val version = plugin.getServerVersion()

        val serializerMethod = if (version.isVersionSameOrGreaterThan(Version.VERSION_1_8_R2)) {
            findClazz("net.minecraft.server.VERSION.IChatBaseComponent\$ChatSerializer", plugin).getDeclaredMethod("a", String::class.java)
        } else {
            findClazz("net.minecraft.server.VERSION.ChatSerializer", plugin).getDeclaredMethod("a", String::class.java)
        }

        val packetInstance = findClazz("net.minecraft.server.VERSION.PacketPlayOutPlayerListHeaderFooter", plugin).newInstance()

        val headerJson = serializerMethod.invoke("{\"color\": \"\", \"text\": \"$finalHeader\"}")
        val footerJson = serializerMethod.invoke("{\"color\": \"\", \"text\": \"$finalFooter\"}")

        val aField = packetInstance::class.java.getDeclaredField("a")
        aField.isAccessible = true
        aField.set(packetInstance, headerJson)

        val bField = packetInstance::class.java.getDeclaredField("b")
        bField.isAccessible = true
        bField.set(packetInstance, footerJson)

        player.sendPacket(player)
    }
}