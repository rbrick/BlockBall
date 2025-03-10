package com.github.shynixn.blockball.core.logic.business.commandmenu

import com.github.shynixn.blockball.api.business.enumeration.*
import com.github.shynixn.blockball.api.persistence.entity.Arena
import com.github.shynixn.blockball.api.persistence.entity.ChatBuilder
import com.github.shynixn.blockball.api.persistence.entity.Particle
import com.github.shynixn.blockball.api.persistence.entity.Sound
import com.github.shynixn.blockball.core.logic.persistence.entity.ChatBuilderEntity

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
class BallSettingsPage : Page(BallSettingsPage.ID, MainSettingsPage.ID) {

    companion object {
        /** Id of the page. */
        const val ID = 30
    }

    /**
     * Returns the key of the command when this page should be executed.
     *
     * @return key
     */
    override fun getCommandKey(): MenuPageKey {
        return MenuPageKey.BALL
    }

    /**
     * Executes actions for this page.
     *
     * @param cache cache
     */
    override fun <P> execute(player: P, command: MenuCommand, cache: Array<Any?>, args: Array<String>): MenuCommandResult {
        val ballMeta = (cache[0] as Arena).meta.ballMeta
        if (command == MenuCommand.BALL_OPEN) {
            cache[5] = null
        } else if (command == MenuCommand.BALL_SKIN && args.size == 3) {
            ballMeta.skin = args[2]
        } else if (command == MenuCommand.BALL_SIZE_CALLBACK && args.size == 3) {
            ballMeta.size = BallSize.values()[args[2].toInt()]
        } else if (command == MenuCommand.BALL_HITBOX && args.size == 3 && args[2].toDoubleOrNull() != null) {
            ballMeta.hitBoxSize = args[2].toDouble()
        } else if (command == MenuCommand.BALL_TOGGLE_CARRYABLE) {
            ballMeta.carryAble = !ballMeta.carryAble
        } else if (command == MenuCommand.BALL_TOGGLE_ALWAYSBOUNCE) {
            ballMeta.alwaysBouce = !ballMeta.alwaysBouce
        } else if (command == MenuCommand.BALL_TOGGLE_ROTATING) {
            ballMeta.rotating = !ballMeta.rotating
        } else if (command == MenuCommand.BALL_PARTICLEACTION_CALLBACK && args.size == 3) {
            cache[5] = ballMeta.particleEffects[BallActionType.values()[args[2].toInt()]]
        } else if (command == MenuCommand.BALL_SOUNDACTION_CALLBACK && args.size == 3) {
            cache[5] = ballMeta.soundEffects[BallActionType.values()[args[2].toInt()]]
        }
        return super.execute(player, command, cache, args)
    }

    /**
     * Builds the page content.
     *
     * @param cache cache
     * @return content
     */
    override fun buildPage(cache: Array<Any?>): ChatBuilder {
        val ballMeta = (cache[0] as Arena).meta.ballMeta
        val builder = ChatBuilderEntity()
            .component("- Skin: ").builder()
            .component(MenuClickableItem.PREVIEW.text).setColor(MenuClickableItem.PREVIEW.color).setHoverText(ballMeta.skin).builder()
            .component(MenuClickableItem.EDIT.text).setColor(MenuClickableItem.EDIT.color)
            .setClickAction(ChatClickAction.SUGGEST_COMMAND, MenuCommand.BALL_SKIN.command)
            .setHoverText("Changes the skin of the ball. Can be the name of a skin or a skin URL.")
            .builder().nextLine()
            .component("- Skin Size: " + ballMeta.size.name).builder()
            .component(MenuClickableItem.SELECT.text).setColor(MenuClickableItem.SELECT.color)
            .setClickAction(ChatClickAction.RUN_COMMAND, MenuCommand.LIST_BALLSIZES.command)
            .setHoverText("Opens the selectionbox for ball sizes.")
            .builder().nextLine()
            .component("- Hitbox Size: " + ballMeta.hitBoxSize).builder()
            .component(MenuClickableItem.EDIT.text).setColor(MenuClickableItem.EDIT.color)
            .setClickAction(ChatClickAction.SUGGEST_COMMAND, MenuCommand.BALL_HITBOX.command)
            .setHoverText("Changes the hitbox size of the ball.")
            .builder().nextLine()
            .component("- Carry Able: " + ballMeta.carryAble).builder()
            .component(MenuClickableItem.TOGGLE.text).setColor(MenuClickableItem.TOGGLE.color)
            .setClickAction(ChatClickAction.RUN_COMMAND, MenuCommand.BALL_TOGGLE_CARRYABLE.command)
            .setHoverText("Should the ball be carry able when a player right clicks on it?")
            .builder().nextLine()
            .component("- Always Bounce: " + ballMeta.alwaysBouce).builder()
            .component(MenuClickableItem.TOGGLE.text).setColor(MenuClickableItem.TOGGLE.color)
            .setClickAction(ChatClickAction.RUN_COMMAND, MenuCommand.BALL_TOGGLE_ALWAYSBOUNCE.command)
            .setHoverText("Should the ball always bounce of surfaces?")
            .builder().nextLine()
            .component("- Rotation Animation: " + ballMeta.rotating).builder()
            .component(MenuClickableItem.TOGGLE.text).setColor(MenuClickableItem.TOGGLE.color)
            .setClickAction(ChatClickAction.RUN_COMMAND, MenuCommand.BALL_TOGGLE_ROTATING.command)
            .setHoverText("Should the ball play a rotation animation?")
            .builder().nextLine()
            .component("- Ball modifiers: ").builder()
            .component(MenuClickableItem.PAGE.text).setColor(MenuClickableItem.PAGE.color)
            .setClickAction(ChatClickAction.RUN_COMMAND, MenuCommand.BALLMOD_OPEN.command)
            .setHoverText("Opens the page for ball modifiers.")
            .builder().nextLine()
            .component("- Soundeffect: ").builder()
            .component(MenuClickableItem.SELECT.text).setColor(MenuClickableItem.SELECT.color)
            .setClickAction(ChatClickAction.RUN_COMMAND, MenuCommand.LIST_BALL_SOUNDEFFECTS.command)
            .setHoverText("Opens the selectiongbox for action binders.")
            .builder().nextLine()
            .component("- Particleeffect: ").builder()
            .component(MenuClickableItem.SELECT.text).setColor(MenuClickableItem.SELECT.color)
            .setClickAction(ChatClickAction.RUN_COMMAND, MenuCommand.LIST_BALL_PARTICLEFFECTS.command)
            .setHoverText("Opens the selectiongbox for action binders.")
            .builder().nextLine()
        if (cache[5] != null && cache[5] is Sound) {
            builder.component("- Selected Soundeffect: ").builder().component(MenuClickableItem.PAGE.text).setColor(MenuClickableItem.PAGE.color)
                .setClickAction(ChatClickAction.RUN_COMMAND, MenuCommand.SOUND_BALL.command)
                .setHoverText("Opens the page for editing the soundeffect.")
                .builder().nextLine()
        } else if (cache[5] != null && cache[5] is Particle) {
            builder.component("- Selected Particleffect: ").builder().component(MenuClickableItem.PAGE.text).setColor(MenuClickableItem.PAGE.color)
                .setClickAction(ChatClickAction.RUN_COMMAND, MenuCommand.PARTICLE_BALL.command)
                .setHoverText("Opens the page for editing the particleeffect.")
                .builder().nextLine()
        }
        return builder
    }
}