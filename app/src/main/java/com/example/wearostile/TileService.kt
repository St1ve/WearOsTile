package com.example.wearostile

import androidx.wear.tiles.LayoutElementBuilders
import androidx.wear.tiles.LayoutElementBuilders.Box
import androidx.wear.tiles.LayoutElementBuilders.Layout
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.ResourceBuilders
import androidx.wear.tiles.ResourceBuilders.Resources
import androidx.wear.tiles.TileBuilders.Tile
import androidx.wear.tiles.TileProviderService
import androidx.wear.tiles.TimelineBuilders.Timeline
import androidx.wear.tiles.TimelineBuilders.TimelineEntry
import com.example.wearostile.heplers.ButtonsIds
import com.example.wearostile.heplers.ImagesIds
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import kotlin.random.Random

private const val RESOURCES_VERSION: String = "1"

class TileService : TileProviderService() {
    override fun onTileRequest(
        requestParams: RequestBuilders.TileRequest
    ): ListenableFuture<Tile> {
        val deviceParameters = requestParams.deviceParameters

        val defaultLayout = Box.builder()
            .addContent(
                LayoutElementBuilders.Text.builder().setText("Error!")
            ).build()

        val defaultTile = Tile.builder()
            .setResourcesVersion(RESOURCES_VERSION)
            .setTimeline(
                Timeline.builder()
                    .addTimelineEntry(
                        TimelineEntry.builder()
                            .setLayout(
                                Layout.builder()
                                    .setRoot(defaultLayout)
                            )
                    )
            ).build()

        deviceParameters ?: return Futures.immediateFuture(defaultTile)

        val slicesNumber = Random.nextInt(0, 9)

        val text = when (requestParams.state?.lastClickableId) {
            ButtonsIds.PIZZA.name -> "Updated pizza"
            else -> "Pizza"
        }

        val tileHelper = TileHelper(context = this, deviceParameters = deviceParameters)
        val tileLayout = tileHelper.buildPizzaLayout(text, slicesNumber)
        val tile = Tile.builder()
            .setResourcesVersion(RESOURCES_VERSION)
            .setTimeline(
                Timeline.builder()
                    .addTimelineEntry(
                        TimelineEntry.builder()
                            .setLayout(
                                Layout.builder()
                                    .setRoot(tileLayout)
                            )
                    )
            ).build()

        return Futures.immediateFuture(tile)
    }

    override fun onResourcesRequest(
        requestParams: RequestBuilders.ResourcesRequest
    ): ListenableFuture<Resources> = Futures.immediateFuture(
        Resources.builder()
            .setVersion(RESOURCES_VERSION)
            .addIdToImageMapping(
                ImagesIds.PIZZA.name,
                ResourceBuilders.ImageResource.builder()
                    .setAndroidResourceByResId(
                        ResourceBuilders.AndroidImageResourceByResId.builder()
                            .setResourceId(R.drawable.ic_pizza)
                    )
            )
            .build()
    )
}
