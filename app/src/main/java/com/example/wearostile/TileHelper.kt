package com.example.wearostile

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.wear.tiles.ActionBuilders
import androidx.wear.tiles.ColorBuilders.argb
import androidx.wear.tiles.DeviceParametersBuilders.DeviceParameters
import androidx.wear.tiles.DimensionBuilders.degrees
import androidx.wear.tiles.DimensionBuilders.dp
import androidx.wear.tiles.DimensionBuilders.expand
import androidx.wear.tiles.LayoutElementBuilders.ARC_ANCHOR_START
import androidx.wear.tiles.LayoutElementBuilders.Arc
import androidx.wear.tiles.LayoutElementBuilders.ArcLine
import androidx.wear.tiles.LayoutElementBuilders.Box
import androidx.wear.tiles.LayoutElementBuilders.Image
import androidx.wear.tiles.LayoutElementBuilders.Text
import androidx.wear.tiles.LayoutElementBuilders.VERTICAL_ALIGN_BOTTOM
import androidx.wear.tiles.LayoutElementBuilders.VERTICAL_ALIGN_CENTER
import androidx.wear.tiles.ModifiersBuilders
import com.example.wearostile.heplers.ButtonsIds
import com.example.wearostile.heplers.ImagesIds

class TileHelper(
    private val context: Context,
    private val deviceParameters: DeviceParameters,
) {
    fun buildFlightLayout(text: String, pizzaSlicesNumber: Int): Box = Box.builder()
        .addContent(
            buildArc(pizzaSlicesNumber.toFloat() / MAX_SLICES_NUMBER.toFloat())
        )
        .addContent(buildTextLayout(text, pizzaSlicesNumber))
        .addContent(
            buildButtonLayout(
                idImage = ImagesIds.PIZZA,
                idBtnClick = ButtonsIds.PIZZA,
                pizzaSlicesNumber = pizzaSlicesNumber,
            )
        )
        .build()

    private fun buildArc(percentage: Float): Arc = Arc.builder()
        .addContent(
            ArcLine.builder()
                .setLength(degrees(percentage * ARC_TOTAL_DEGREES))
                .setColor(argb(Color.MAGENTA))
                .setThickness(dp(6f))
        )
        .setAnchorAngle(degrees(ARC_START_POSITION))
        .setAnchorType(ARC_ANCHOR_START)
        .build()

    private fun buildButtonLayout(
        idImage: ImagesIds,
        idBtnClick: ButtonsIds,
        pizzaSlicesNumber: Int,
    ): Box = Box.builder()
        .setWidth(expand())
        .setHeight(expand())
        .addContent(
            Box.builder()
                .setWidth(BOX_ARC_BUTTON_SIZE)
                .setHeight(BOX_ARC_BUTTON_SIZE)
                .addContent(buildArc(pizzaSlicesNumber.toFloat() / MAX_SLICES_NUMBER.toFloat()))
                .addContent(
                    Image.builder()
                        .setWidth(BUTTON_SIZE)
                        .setHeight(BUTTON_SIZE)
                        .setResourceId(idImage.name)
                        .setModifiers(
                            ModifiersBuilders.Modifiers.builder()
                                .setPadding(
                                    ModifiersBuilders.Padding.builder()
                                        .setStart(BUTTON_PADDING)
                                        .setEnd(BUTTON_PADDING)
                                        .setTop(BUTTON_PADDING)
                                        .setBottom(BUTTON_PADDING)
                                )
                                .setBackground(
                                    ModifiersBuilders.Background.builder()
                                        .setCorner(
                                            ModifiersBuilders.Corner.builder().setRadius(BUTTON_RADIUS)
                                        )
                                        .setColor(
                                            argb(
                                                ContextCompat.getColor(
                                                    context,
                                                    R.color.background_btn
                                                )
                                            )
                                        )
                                )
                                .setClickable(
                                    ModifiersBuilders.Clickable.builder()
                                        .setId(idBtnClick.name)
                                        .setOnClick(ActionBuilders.LoadAction.builder())
                                )
                        )
                )
        )
        .setModifiers(
            ModifiersBuilders.Modifiers.builder()
                .setPadding(
                    ModifiersBuilders.Padding.builder()
                        .setBottom(BOX_BUTTON_PADDING)
                )
        )
        .setVerticalAlignment(VERTICAL_ALIGN_BOTTOM)
        .build()

    private fun buildTextLayout(text: String, pizzaSlicesNumber: Int): Box = Box.builder()
        .addContent(
            Text.builder()
                .setText("$text: $pizzaSlicesNumber")
        )
        .setVerticalAlignment(VERTICAL_ALIGN_CENTER)
        .build()

    companion object {
        private const val ARC_START_POSITION: Float = 0f
        private const val ARC_TOTAL_DEGREES: Float = 360f
        private const val MAX_SLICES_NUMBER: Int = 8

        private val BUTTON_SIZE = dp(48f)
        private val BOX_ARC_BUTTON_SIZE = dp(56f)
        private val BUTTON_RADIUS = dp(24f)
        private val BUTTON_PADDING = dp(12f)

        private val BOX_BUTTON_PADDING = dp(16f)
    }
}
