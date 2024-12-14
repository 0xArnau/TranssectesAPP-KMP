package com.github.oxarnau.transsectes_app.features.transect.domain.mappers

import com.github.oxarnau.transsectes_app.features.transect.domain.entities.Transect

/**
 * Interface for mapping data objects to the domain entity [Transect].
 *
 * This mapper provides a contract to convert objects of a generic type [T]
 * into instances of the domain-specific entity [Transect]. It is useful for
 * transforming data between layers in a clean architecture pattern, such as
 * mapping from DTOs or database models to domain entities.
 *
 * @param T The type of the input object that will be mapped to a [Transect].
 */
interface TransectMapper<T> {

    /**
     * Maps an object of type [T] to a [Transect] entity.
     *
     * @param item The input object of type [T] to be mapped.
     * @return The resulting [Transect] entity.
     */
    fun toEntity(item: T): Transect


}
