/*
 * Copyright 2018-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mongodb.stitch.server.services.aws.internal;

import com.mongodb.stitch.core.services.aws.AwsRequest;
import com.mongodb.stitch.core.services.aws.internal.CoreAwsServiceClient;
import com.mongodb.stitch.server.services.aws.AwsServiceClient;
import org.bson.codecs.Decoder;
import org.bson.codecs.configuration.CodecRegistry;

public final class AwsServiceClientImpl implements AwsServiceClient {

  private final CoreAwsServiceClient proxy;

  public AwsServiceClientImpl(final CoreAwsServiceClient client) {
    this.proxy = client;
  }

  /**
   * Executes the AWS request.
   *
   * @param request the AWS request to execute.
   */
  public void execute(final AwsRequest request) {
    proxy.execute(request);
  }

  /**
   * Executes the AWS request, and decodes the result into an instance of the
   * specified type. The response will be decoded using the codec registry specified when the app
   * client was configured. If no codec registry was configured, a default codec registry will be
   * used. The default codec registry supports the mappings specified <a
   * href="http://mongodb.github.io/mongo-java-driver/3.1/bson/documents/#document">here</a>
   *
   * @param request the AWS request to execute.
   * @param resultDecoder the {@link Decoder} to use to decode the result into a value.
   * @param <ResultT> the type into which the response will be decoded.
   * @return the decoded result value.
   */
  public <ResultT> ResultT execute(final AwsRequest request, final Decoder<ResultT> resultDecoder) {
    return proxy.execute(request, resultDecoder);
  }

  /**
   * Executes the AWS request, and decodes the result into an instance of the
   * specified type. The response will be decoded using the codec registry specified when the app
   * client was configured. If no codec registry was configured, a default codec registry will be
   * used. The default codec registry supports the mappings specified <a
   * href="http://mongodb.github.io/mongo-java-driver/3.1/bson/documents/#document">here</a>
   *
   * @param request the AWS request to execute.
   * @param resultClass the class that the result should be decoded as.
   * @param <ResultT> the type into which the response will be decoded.
   * @return the decoded result value.
   */
  public <ResultT> ResultT execute(final AwsRequest request, final Class<ResultT> resultClass) {
    return proxy.execute(request, resultClass);
  }

  /**
   * Executes the AWS request, and decodes the result into an instance of the
   * specified type. The response will be decoded using the codec registry given.
   *
   * @param request the AWS request to execute.
   * @param resultClass the class that the result should be decoded as.
   * @param codecRegistry the codec registry used for de/serialization of the function call.
   * @param <ResultT> the type into which the response will be decoded.
   * @return the decoded result value.
   */
  public <ResultT> ResultT execute(
      final AwsRequest request,
      final Class<ResultT> resultClass,
      final CodecRegistry codecRegistry
  ) {
    return proxy.execute(request, resultClass, codecRegistry);
  }

  /**
   * Executes the AWS request.
   *
   * @param request the AWS request to execute.
   * @param requestTimeout the number of milliseconds the client should wait for a response from the
   *                       server before failing with an error.
   */
  public void execute(
      final AwsRequest request,
      final Long requestTimeout
  ) {
    proxy.execute(request, requestTimeout);
  }

  /**
   * Executes the AWS request, and decodes the result into an instance of the
   * specified type. The response will be decoded using the codec registry specified when the app
   * client was configured. If no codec registry was configured, a default codec registry will be
   * used. The default codec registry supports the mappings specified <a
   * href="http://mongodb.github.io/mongo-java-driver/3.1/bson/documents/#document">here</a>
   *
   * @param request the AWS request to execute.
   * @param requestTimeout the number of milliseconds the client should wait for a response from the
   *                       server before failing with an error.
   * @param resultDecoder the {@link Decoder} to use to decode the result into a value.
   * @param <ResultT> the type into which the response will be decoded.
   * @return the decoded result value.
   */
  public <ResultT> ResultT execute(
      final AwsRequest request,
      final Long requestTimeout,
      final Decoder<ResultT> resultDecoder
  ) {
    return proxy.execute(request, requestTimeout, resultDecoder);
  }

  /**
   * Executes the AWS request, and decodes the result into an instance of the
   * specified type. The response will be decoded using the codec registry specified when the app
   * client was configured. If no codec registry was configured, a default codec registry will be
   * used. The default codec registry supports the mappings specified <a
   * href="http://mongodb.github.io/mongo-java-driver/3.1/bson/documents/#document">here</a>
   *
   * @param request the AWS request to execute.
   * @param requestTimeout the number of milliseconds the client should wait for a response from the
   *                       server before failing with an error.
   * @param resultClass the class that the result should be decoded as.
   * @param <ResultT> the type into which the response will be decoded.
   * @return the decoded result value.
   */
  public <ResultT> ResultT execute(
      final AwsRequest request,
      final Long requestTimeout,
      final Class<ResultT> resultClass
  ) {
    return proxy.execute(request, requestTimeout, resultClass);
  }

  /**
   * Executes the AWS request, and decodes the result into an instance of the
   * specified type. The response will be decoded using the codec registry given.
   *
   * @param request the AWS request to execute.
   * @param requestTimeout the number of milliseconds the client should wait for a response from the
   *                       server before failing with an error.
   * @param resultClass the class that the result should be decoded as.
   * @param codecRegistry the codec registry used for de/serialization of the function call.
   * @param <ResultT> the type into which the response will be decoded.
   * @return the decoded result value.
   */
  public <ResultT> ResultT execute(
      final AwsRequest request,
      final Long requestTimeout,
      final Class<ResultT> resultClass,
      final CodecRegistry codecRegistry
  ) {
    return proxy.execute(request, requestTimeout, resultClass, codecRegistry);
  }

  /**
   * Get the codec registry that will be used to decode responses when a codec registry.
   *
   * @return the {@link CodecRegistry}
   */
  public CodecRegistry getCodecRegistry() {
    return proxy.getCodecRegistry();
  }

  /**
   * Create a new AwsServiceClient instance with a different codec registry.
   *
   * @param codecRegistry the new {@link CodecRegistry} for the client.
   * @return a new AwsServiceClient instance with the different codec registry
   */
  public AwsServiceClient withCodecRegistry(final CodecRegistry codecRegistry) {
    return new AwsServiceClientImpl(proxy.withCodecRegistry(codecRegistry));
  }
}
