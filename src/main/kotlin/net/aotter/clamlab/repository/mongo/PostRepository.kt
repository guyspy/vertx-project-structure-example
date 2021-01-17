package net.aotter.clamlab.repository.mongo

import com.mongodb.client.model.IndexModel
import com.mongodb.client.model.Indexes
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.awaitFirst
import net.aotter.clamlab.extension.getLogger
import net.aotter.clamlab.model.po.Post
import net.aotter.clamlab.util.db.Mongo

class PostRepository(mongo: Mongo) : BaseMongoRepository<Post>(Post::class.java, mongo) {

  init {
    GlobalScope.launch {
      getLogger().info("Yppp ${col.insertOne(Post("foso")).awaitFirst()}")
    }
  }

  override val indexes: List<IndexModel>
    get() = listOf(IndexModel(Indexes.ascending(Post::title.name)))


}
