package net.aotter.clamlab.repository.mongo

import com.mongodb.client.model.IndexModel
import com.mongodb.reactivestreams.client.MongoCollection
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.awaitFirstOrNull
import net.aotter.clamlab.util.db.Mongo

abstract class BaseMongoRepository<T>(clazz: Class<T>, mongo: Mongo) {

  protected var col: MongoCollection<T> = mongo.getCollection(clazz)

  abstract val indexes: List<IndexModel>

  init {
    GlobalScope.launch {
      col.createIndexes(indexes).awaitFirstOrNull()
    }
  }

}
