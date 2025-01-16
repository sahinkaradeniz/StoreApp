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
public class PresentationProjectDependency extends DelegatingProjectDependency {

    @Inject
    public PresentationProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":presentation:basket"
     */
    public Presentation_BasketProjectDependency getBasket() { return new Presentation_BasketProjectDependency(getFactory(), create(":presentation:basket")); }

    /**
     * Creates a project dependency on the project at path ":presentation:checkout"
     */
    public Presentation_CheckoutProjectDependency getCheckout() { return new Presentation_CheckoutProjectDependency(getFactory(), create(":presentation:checkout")); }

    /**
     * Creates a project dependency on the project at path ":presentation:detail"
     */
    public Presentation_DetailProjectDependency getDetail() { return new Presentation_DetailProjectDependency(getFactory(), create(":presentation:detail")); }

    /**
     * Creates a project dependency on the project at path ":presentation:favorites"
     */
    public Presentation_FavoritesProjectDependency getFavorites() { return new Presentation_FavoritesProjectDependency(getFactory(), create(":presentation:favorites")); }

    /**
     * Creates a project dependency on the project at path ":presentation:home"
     */
    public Presentation_HomeProjectDependency getHome() { return new Presentation_HomeProjectDependency(getFactory(), create(":presentation:home")); }

}
