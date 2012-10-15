package de.grubler.vertx;

import org.vertx.java.busmods.BusModBase;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpClient;
import org.vertx.java.core.http.HttpClientResponse;
import org.vertx.java.core.json.JsonObject;

public class ModSolr extends BusModBase {

	private String address;
	private String host;
	private int port;
	private String core;

	private HttpClient client;

	public void start() {
		super.start();
		address = getOptionalStringConfig("address", "vertx.solr");
		host = getOptionalStringConfig("host", "dev1.tackl.it");
		port = getOptionalIntConfig("port", 8983);
		core = getOptionalStringConfig("core", "collection1");

		client = vertx.createHttpClient().setPort(port).setHost(host);

		eb.registerHandler(address, new Handler<Message<JsonObject>>() {
			@Override
			public void handle(final Message<JsonObject> message) {
				client.getNow("/solr/collection1/select?q=*%3A*&wt=json",
				        new Handler<HttpClientResponse>() {
					        @Override
					        public void handle(HttpClientResponse response) {
						        response.bodyHandler(new Handler<Buffer>() {

							        @Override
							        public void handle(Buffer body) {
								        JsonObject jsonObject = new JsonObject(
								                body.toString());
								        message.reply(jsonObject);
							        }
						        });
					        }
				        });
			}
		});
	}

	public void stop() {
		client.close();
		try {
			super.stop();
		} catch (Exception e) {
		}
	}
}
