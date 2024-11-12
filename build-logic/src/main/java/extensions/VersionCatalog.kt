package extensions

import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.provider.Provider

internal val VersionCatalog.ktlint: Provider<MinimalExternalModuleDependency>
    get() = getLibrary("ktlint")
internal fun VersionCatalog.getLibrary(library: String) = findLibrary(library).get()
internal fun VersionCatalog.getVersion(library: String) = findVersion(library).get()
internal fun VersionCatalog.getBundle(bundle: String) = findBundle(bundle).get()

