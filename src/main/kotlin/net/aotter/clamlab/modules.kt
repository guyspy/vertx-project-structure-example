package net.aotter.clamlab

import net.aotter.clamlab.repository.mongo.PostRepository
import net.aotter.clamlab.util.db.Mongo
import org.koin.dsl.module

val module = module {
  single { Mongo() }
  single { PostRepository(get()) }
}
