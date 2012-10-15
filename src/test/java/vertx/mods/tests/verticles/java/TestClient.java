package vertx.mods.tests.verticles.java;

/*
 * Copyright 2011-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpClient;
import org.vertx.java.core.http.HttpClientResponse;
import org.vertx.java.core.http.WebSocket;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.framework.TestClientBase;

/**
 * 
 * Most of the testing is done in JS since it's so much easier to play with JSON
 * in JS rather than Java
 * 
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class TestClient extends TestClientBase {

	private EventBus eb;

	private String solrID;

	private HttpClient client;
	
	private static String address = "test.solr"; 

	@Override
	public void start() {
		super.start();
		eb = vertx.eventBus();
		JsonObject config = new JsonObject();
		config.putString("address", address);
		config.putString("core", "collection1");
		container.deployModule(
		        "vertx.mod-solr-v" + System.getProperty("vertx.version"),
		        config, 1, new Handler<String>() {
			        public void handle(String res) {
				        solrID = res;
				        tu.appReady();
			        }
		        });
	}

	@Override
	public void stop() {
//		 client.close();
		super.stop();
	}

	public void testQuery() throws Exception {

		JsonObject json = new JsonObject();

		eb.send(address, json, new Handler<Message<JsonObject>>() {
			public void handle(Message<JsonObject> reply) {
//				System.out.println("reply " + reply.body);
				tu.azzert(reply.body.getField("responseHeader") != null);
				tu.azzert(reply.body.getField("response") != null);
				tu.testComplete();
			}
		});
	}

}
