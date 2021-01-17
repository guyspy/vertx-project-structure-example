package net.aotter.clamlab.util.db

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoCollection
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.pojo.PojoCodecProvider

private const val DEFAULT_DB = "clam-lab"

private const val CONN_STR = "mongodb://localhost:27017"

class Mongo(
  private val dbName: String = DEFAULT_DB,
  private val mongoClient: MongoClient = MongoClients.create(
    MongoClientSettings.builder()
      .applyConnectionString(ConnectionString(CONN_STR))
      .codecRegistry(
        CodecRegistries.fromRegistries(
          MongoClients.getDefaultCodecRegistry(),
          CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
        )
      ).build()
  )
) {

  // is it possible to use reified?
  fun <T> getCollection(clazz: Class<T>): MongoCollection<T> =
    mongoClient.getDatabase(dbName).getCollection(clazz.simpleName, clazz)

  fun close() = mongoClient.close()

}

