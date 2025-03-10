package com.github.shynixn.blockball.api.business.enumeration

/**
 * Created by Shynixn 2019.
 * <p>
 * Version 1.2
 * <p>
 * MIT License
 * <p>
 * Copyright (c) 2019 by Shynixn
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
enum class MenuPageKey(
    /**
     * Page key.
     */
    val key: String
) {
    OPEN("open"),
    MAINSETTING("mset"),
    LISTABLE("labl"),
    TEAMMETA("tma"),
    EFFECTS("ef"),
    SCOREBOARD("scor"),
    BOSSBAR("boss"),
    HOLOGRAM("holog"),
    SIGNS("sign"),
    DOUBLEJUMP("doubl"),
    ABILITIES("abi"),
    MULTIPLELINES("mlin"),
    PARTICLEFFECTS("part"),
    SOUNDEFFECTS("sound"),
    MISC("misc"),
    AREAPROTECTION("aprot"),
    GAMEEXTENSIONS("gameex"),
    MULTIPLEITEMS("mitem"),
    REWARDSPAGE("reward"),
    GAMESETTINGS("gameset"),
    COMMANDPAGE("com"),
    SPECTATING("spect"),
    SPECTATE("specta"),
    BALL("ball"),
    NOTIFICATIONS("notif"),
    BALLMODIFIER("ballmod"),
    TEAMTEXTBOOK("teamtext"),
    TEMPLATEPAGE("template"),
    MAINCONFIGURATION("mcf");
}