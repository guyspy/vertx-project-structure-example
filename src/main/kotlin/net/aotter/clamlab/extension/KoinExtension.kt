package net.aotter.clamlab.extension

import net.aotter.clamlab.repository.mongo.BaseMongoRepository
import org.koin.core.Koin
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

/**
 * Adapted from Ktor Koin extensions
 *
 */

///**
// * Help work on ModuleDefinition
// */
//fun <T> BaseMongoRepository<T>.getKoin(): Koin = GlobalContext.get()
//
///**
// * inject lazily given dependency
// * @param qualifier - bean name / optional
// * @param scope
// * @param parameters
// */
//inline fun <reified T : Any, K> BaseMongoRepository<K>.inject(
//  qualifier: Qualifier? = null,
//  noinline parameters: ParametersDefinition? = null
//) =
//  lazy { get<T, K>(qualifier, parameters) }
//
///**
// * Retrieve given dependency for KoinComponent
// * @param qualifier - bean name / optional
// * @param parameters
// */
//inline fun <reified T : Any, K> BaseMongoRepository<K>.get(
//  qualifier: Qualifier? = null,
//  noinline parameters: ParametersDefinition? = null
//) =
//  getKoin().get<T>(qualifier, parameters)
//
///**
// * Retrieve given property for KoinComponent
// * @param key - key property
// */
//fun <T : Any, K> BaseMongoRepository<K>.getProperty(key: String) =
//  getKoin().getProperty<T>(key)
//
///**
// * Retrieve given property for KoinComponent
// * give a default value if property is missing
// *
// * @param key - key property
// * @param defaultValue - default value if property is missing
// *
// */
//fun <K> BaseMongoRepository<K>.getProperty(key: String, defaultValue: String) =
//  getKoin().getProperty(key) ?: defaultValue
