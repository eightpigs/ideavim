/*
 * IdeaVim - Vim emulator for IDEs based on the IntelliJ platform
 * Copyright (C) 2003-2019 The IdeaVim authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

@file:Suppress("RemoveCurlyBracesFromTemplate")

package org.jetbrains.plugins.ideavim.action.motion.updown

import com.maddyhome.idea.vim.command.CommandState
import com.maddyhome.idea.vim.helper.StringHelper.parseKeys
import org.jetbrains.plugins.ideavim.VimTestCase

/**
 * @author Alex Plate
 */
class MotionDownActionTest : VimTestCase() {
  fun `test motion down in visual block mode`() {
    val keys = parseKeys("<C-V>2kjjj")
    val before = """
            A Discovery

            I |found| it in a legendary land
            al|l roc|ks and lavender and tufted grass,
            wh|${c}ere i|t was settled on some sodden sand
            ha|rd by| the torrent of a mountain pass.
        """.trimIndent()
    val after = """
            A Discovery

            I |found| it in a legendary land
            al|l roc|ks and lavender and tufted grass,
            wh|${s}e${se}re i|t was settled on some sodden sand
            ha|${s}r${se}d by| the torrent of a mountain pass.
        """.trimIndent()
    doTest(keys, before, after, CommandState.Mode.VISUAL, CommandState.SubMode.VISUAL_BLOCK)
  }

  fun `test motion down in visual block mode with dollar motion`() {
    val keys = parseKeys("<C-V>\$jj")
    val before = """
            A Discovery

            I |${c}found it in a legendary land
            al|l rocks and lavender and tufted grass,
            wh|ere it was settled on some sodden sand[additional Chars]
            hard by the torrent of a mountain pass.
        """.trimIndent()
    val after = """
            A Discovery

            I |${s}found it in a legendary lan${c}d${se}
            al|${s}l rocks and lavender and tufted grass${c},${se}
            wh|${s}ere it was settled on some sodden sand[additional Chars]${c}${se}
            hard by the torrent of a mountain pass.
        """.trimIndent()
    doTest(keys, before, after, CommandState.Mode.VISUAL, CommandState.SubMode.VISUAL_BLOCK)
  }

  fun `test last column after line deletion`() {
    val keys = parseKeys("Vd", "j")
    val before = """
            I found it in a ${c}legendary land
            
            all rocks and lavender and tufted grass,
        """.trimIndent()
    val after = """
            
            ${c}all rocks and lavender and tufted grass,
        """.trimIndent()
    doTest(keys, before, after, CommandState.Mode.COMMAND, CommandState.SubMode.NONE)
  }
}