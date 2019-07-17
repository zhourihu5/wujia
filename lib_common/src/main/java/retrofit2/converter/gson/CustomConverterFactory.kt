package retrofit2.converter.gson

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.wujia.lib_common.data.network.exception.ApiJsonFormateException
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.Type

/**
 * Author: shenbingkai
 * CreateDate: 2019-04-06 00:51
 * Description:
 */
class CustomConverterFactory private constructor(private val gson: Gson) : Converter.Factory() {

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        // attention here!
        return CustomResponseConverter(gson, adapter)
    }

    override fun requestBodyConverter(type: Type, parameterAnnotations: Array<Annotation>, methodAnnotations: Array<Annotation>, retrofit: Retrofit): Converter<*, RequestBody> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return GsonRequestBodyConverter(gson, adapter)
    }

    internal inner class CustomResponseConverter<T>(private val gson: Gson, private val adapter: TypeAdapter<T>) : Converter<ResponseBody, T> {

        @Throws(IOException::class)
        override fun convert(value: ResponseBody): T {
            try {
                val body = value.string()
                try {
                    return adapter.fromJson(body)
                } catch (e: JsonSyntaxException) {
                    throw ApiJsonFormateException(body, e.message!!)
                }

            } finally {
                value.close()
            }
        }


    }

    companion object {

        fun create(): CustomConverterFactory {
            return create(Gson())
        }

        // Guarding public API nullability.
        fun create(gson: Gson): CustomConverterFactory {
            if (gson == null) throw NullPointerException("gson == null")
            return CustomConverterFactory(gson)
        }
    }

}
