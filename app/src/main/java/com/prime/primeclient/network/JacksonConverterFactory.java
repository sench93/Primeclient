package com.prime.primeclient.network;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * A {@linkplain Converter.Factory converter} which uses Jackson.
 * <p>
 * Because Jackson is so flexible in the types it supports, this converter assumes that it can
 * handle all types. If you are mixing JSON serialization with something else (such as protocol
 * buffers), you must {@linkplain Retrofit.Builder#addConverterFactory(Converter.Factory) add this
 * instance} last to allow the other converters a chance to see their types.
 */
public final class JacksonConverterFactory extends Converter.Factory {
  /** Create an instance using a default {@link ObjectMapper} instance for conversion. */
  public static JacksonConverterFactory create() {
    return create(new ObjectMapper());
  }

  /** Create an instance using {@code mapper} for conversion. */
  public static JacksonConverterFactory create(ObjectMapper mapper) {
    return new JacksonConverterFactory(mapper);
  }

  private final ObjectMapper mapper;

  private JacksonConverterFactory(ObjectMapper mapper) {
    if (mapper == null) throw new NullPointerException("mapper == null");
    this.mapper = mapper;
  }

  @Override
  public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
      Retrofit retrofit) {
    JavaType javaType = mapper.getTypeFactory().constructType(type);
    ObjectReader reader = mapper.reader(javaType);
    return new JacksonResponseBodyConverter<>(reader);
  }

  @Override
  public Converter<?, RequestBody> requestBodyConverter(Type type,
      Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
    JavaType javaType = mapper.getTypeFactory().constructType(type);
    ObjectWriter writer = mapper.writerWithType(javaType);
    return new JacksonRequestBodyConverter<>(writer);
  }
}