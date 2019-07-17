package com.wujia.lib.imageloader.glideprogress

import android.content.Context

import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.GenericLoaderFactory
import com.bumptech.glide.load.model.ModelCache
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.stream.StreamModelLoader

import java.io.InputStream

/**
 * modified by soulrelay
 */
class ProgressModelLoader @JvmOverloads constructor(private val modelCache: ModelCache<String, String>?, private val proListener: ProgressUIListener? = null) : StreamModelLoader<String> {

    constructor(listener: ProgressUIListener) : this(null, listener) {}


    override fun getResourceFetcher(model: String, width: Int, height: Int): DataFetcher<InputStream> {
        var result: String? = null
        if (modelCache != null) {
            result = modelCache.get(model, width, height)
        }
        if (result == null) {
            result = model
            modelCache?.put(model, width, height, result)
        }
        return ProgressDataFetcher(result, proListener)
    }

    class Factory : ModelLoaderFactory<String, InputStream> {

        private val mModelCache = ModelCache<String, String>(500)

        override fun build(context: Context, factories: GenericLoaderFactory): ModelLoader<String, InputStream> {
            return ProgressModelLoader(mModelCache)
        }

        override fun teardown() {

        }
    }

}
