package com.wujia.lib.imageloader

import android.widget.ImageView


/**
 * Created by soulrelay on 2016/10/11 13:44.
 * Class Note:
 * encapsulation of ImageView,Build Pattern used
 */
class ImageLoaderConfiguration private constructor(builder: Builder) {
    val type: Int  //图片加载类型，目前只有默认类型，以后可以扩展

    val url: String? //需要解析的url

    val placeHolder: Int //当没有成功加载的时候显示的图片

    val imgView: ImageView? //ImageView的实例

    val loadStrategy: Int//加载策略，目前只有默认加载策略，以后可以扩展

    init {
        this.type = builder.type
        this.url = builder.url
        this.placeHolder = builder.placeHolder
        this.imgView = builder.imgView
        this.loadStrategy = builder.loadStrategy
    }

    class Builder {
        var type: Int = 0
        var url: String? = null
        var placeHolder: Int = 0
        var imgView: ImageView? = null
        var loadStrategy: Int = 0

        init {
            this.type = ImageLoaderManager.PIC_DEFAULT_TYPE
            this.url = ""
            //            this.placeHolder = R.mipmap.ic_launcher;
            this.imgView = null
            this.loadStrategy = ImageLoaderManager.LOAD_STRATEGY_DEFAULT
        }

        fun type(type: Int): Builder {
            this.type = type
            return this
        }

        fun url(url: String): Builder {
            this.url = url
            return this
        }

        fun placeHolder(placeHolder: Int): Builder {
            this.placeHolder = placeHolder
            return this
        }

        fun imgView(imgView: ImageView): Builder {
            this.imgView = imgView
            return this
        }

        fun strategy(strategy: Int): Builder {
            this.loadStrategy = strategy
            return this
        }

        fun build(): ImageLoaderConfiguration {
            return ImageLoaderConfiguration(this)
        }

    }
}
