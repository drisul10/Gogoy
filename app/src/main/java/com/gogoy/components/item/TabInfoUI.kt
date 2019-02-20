package com.gogoy.components.item

import com.gogoy.R
import org.jetbrains.anko.*

class TabInfoUI<T> : AnkoComponent<T> {
    override fun createView(ui: AnkoContext<T>) = with(ui) {
        linearLayout {
            textView {
                text =
                    "Info. Iumhumnok iunhum bua de fyodumtul hullothya vh scemem kyth. Fyodum wylag uilaga egembelu yg, mezuul hyul kynaal egyre viragnac, sumha en keseruen kyul byuntelen, ezes vylagumtul wklelue ez yg sumha ezes, egyre wegh uos nym scegegkel wylag scegenul engumet ulud scepsegud. Ezes syrolm thez nym vylagumtul. Leg vylagumtul urumemtuul bezzeg felleyn kyul wylag fyom tuled. Byuntelen merth vylag uilaga olelothya mogomnok owog buabeleul. Ne farad engumet qui scegenul, scemem uiraga en scepsegud kyth bezzeg arad aniath ere. Sydou scouuo farad owog the fugwa eses kunuel, en buol kyul scepsegud huztuzwa urodum fyon yg ne. Halallal mogomnok tuled halal scegegkel scouuo symeonnok num iunhum. Turuentelen viragnac ezes en fyom engumet halal de. Ualallal vh wylag en nequem wylag bezzeg byuntelen, en."
                textSize = 16f
                textColorResource = R.color.colorText
            }
        }
    }
}