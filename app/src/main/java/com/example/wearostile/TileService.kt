package com.example.wearostile

import androidx.wear.tiles.LayoutElementBuilders.Layout
import androidx.wear.tiles.LayoutElementBuilders.Text
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.ResourceBuilders.Resources
import androidx.wear.tiles.TileBuilders.Tile
import androidx.wear.tiles.TileProviderService
import androidx.wear.tiles.TimelineBuilders.Timeline
import androidx.wear.tiles.TimelineBuilders.TimelineEntry
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture

private const val RESOURCES_VERSION: String = "1"

class TileService : TileProviderService() {
    override fun onTileRequest(
        requestParams: RequestBuilders.TileRequest
    ): ListenableFuture<Tile> {
        val tileLayout = Layout.builder().setRoot(
            Text.builder().setText("Hello world!")
        )
        val tileTimelineEntry = TimelineEntry.builder().setLayout(tileLayout)
        val tileTimeLine = Timeline.builder().addTimelineEntry(tileTimelineEntry)

        return Futures.immediateFuture(
            Tile.builder()
                .setResourcesVersion(RESOURCES_VERSION)
                .setTimeline(tileTimeLine).build()
        )
    }

    override fun onResourcesRequest(
        requestParams: RequestBuilders.ResourcesRequest
    ): ListenableFuture<Resources> = Futures.immediateFuture(
        Resources.builder()
            .setVersion(RESOURCES_VERSION)
            .build()
    )
}
