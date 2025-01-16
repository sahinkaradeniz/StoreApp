package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.internal.artifacts.dependencies.ProjectDependencyInternal;
import org.gradle.api.internal.artifacts.DefaultProjectDependencyFactory;
import org.gradle.api.internal.artifacts.dsl.dependencies.ProjectFinder;
import org.gradle.api.internal.catalog.DelegatingProjectDependency;
import org.gradle.api.internal.catalog.TypeSafeProjectDependencyFactory;
import javax.inject.Inject;

@NonNullApi
public class RootProjectAccessor extends TypeSafeProjectDependencyFactory {


    @Inject
    public RootProjectAccessor(DefaultProjectDependencyFactory factory, ProjectFinder finder) {
        super(factory, finder);
    }

    /**
     * Creates a project dependency on the project at path ":"
     */
    public FakeStoreAppProjectDependency getFakeStoreApp() { return new FakeStoreAppProjectDependency(getFactory(), create(":")); }

    /**
     * Creates a project dependency on the project at path ":app"
     */
    public AppProjectDependency getApp() { return new AppProjectDependency(getFactory(), create(":app")); }

    /**
     * Creates a project dependency on the project at path ":core"
     */
    public CoreProjectDependency getCore() { return new CoreProjectDependency(getFactory(), create(":core")); }

    /**
     * Creates a project dependency on the project at path ":core-ui"
     */
    public CoreUiProjectDependency getCoreUi() { return new CoreUiProjectDependency(getFactory(), create(":core-ui")); }

    /**
     * Creates a project dependency on the project at path ":data"
     */
    public DataProjectDependency getData() { return new DataProjectDependency(getFactory(), create(":data")); }

    /**
     * Creates a project dependency on the project at path ":domain"
     */
    public DomainProjectDependency getDomain() { return new DomainProjectDependency(getFactory(), create(":domain")); }

    /**
     * Creates a project dependency on the project at path ":navigation"
     */
    public NavigationProjectDependency getNavigation() { return new NavigationProjectDependency(getFactory(), create(":navigation")); }

    /**
     * Creates a project dependency on the project at path ":presentation"
     */
    public PresentationProjectDependency getPresentation() { return new PresentationProjectDependency(getFactory(), create(":presentation")); }

}
