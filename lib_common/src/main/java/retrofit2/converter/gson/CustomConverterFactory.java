package retrofit2.converter.gson;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.wujia.lib_common.data.network.exception.ApiJsonFormateException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Author: shenbingkai
 * CreateDate: 2019-04-06 00:51
 * Description:
 */
public final class CustomConverterFactory extends Converter.Factory {

    private final Gson gson;

    public static CustomConverterFactory create() {
        return create(new Gson());
    }

    @SuppressWarnings("ConstantConditions") // Guarding public API nullability.
    public static CustomConverterFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new CustomConverterFactory(gson);
    }

    private CustomConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        // attention here!
        return new CustomResponseConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonRequestBodyConverter<>(gson, adapter);
    }

    class CustomResponseConverter<T> implements Converter<ResponseBody, T> {

        private final Gson gson;
        private final TypeAdapter<T> adapter;
        private static final String CODE = "code";
        private static final String DATA = "data";
        private static final int SUCCESS_CODE = 900;


        CustomResponseConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            try {
                String body = value.string();
//                JSONObject json = new JSONObject(body);
//                int code = json.optInt(CODE);
//                if (code == SUCCESS_CODE) {
//                    json.put(DATA, null);
//                    body = json.toString();
//                }
                try {
                    T t = adapter.fromJson(body);
                    return t;
                } catch (JsonSyntaxException e) {
                    throw new ApiJsonFormateException(body, e.getMessage());
                }
            } finally {
                value.close();
            }
        }

    }

}
