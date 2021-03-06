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

package com.mongodb.stitch.server.services.mongodb.local;

import com.mongodb.client.MongoClient;
import com.mongodb.stitch.core.services.mongodb.local.internal.LocalMongoClientFactory;
import com.mongodb.stitch.server.core.services.internal.ServiceClientFactory;
import com.mongodb.stitch.server.services.mongodb.local.internal.ServerEmbeddedMongoClientFactory;

/**
 * The LocalMongoDbService is used to access {@link LocalMongoDbService#clientFactory} which
 * provides MongoClients used for local storage using the embedded MongoDB platform.
 */
public final class LocalMongoDbService extends LocalMongoClientFactory {

  public static final ServiceClientFactory<MongoClient> clientFactory =
      (service, appInfo) -> LocalMongoClientFactory.getClient(
          appInfo, ServerEmbeddedMongoClientFactory.getInstance());
}
