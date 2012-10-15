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

package vertx.mods.test.verticals

import org.vertx.groovy.core.Vertx;
import org.vertx.groovy.core.buffer.Buffer
import org.vertx.groovy.core.http.HttpClient;
import org.vertx.groovy.framework.TestUtils

tu = new TestUtils(vertx)
tu.checkContext()

HttpClient client = vertx.createHttpClient().setPort(8080)

def testMyFirstTest() {
  println("bla bla bla...")
  HttpClient client = vertx.createHttpClient().setPort(8080)
  client.get("test") {
    
  }
  
  tu.azzert(true)
  tu.testComplete()
  
}


tu.registerTests(this)
tu.appReady()

void vertxStop() {
  client.close()
  tu.unregisterAll()
  tu.appStopped()

}

