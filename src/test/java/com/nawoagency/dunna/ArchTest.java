package com.nawoagency.dunna;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.nawoagency.dunna");

        noClasses()
            .that()
            .resideInAnyPackage("com.nawoagency.dunna.service..")
            .or()
            .resideInAnyPackage("com.nawoagency.dunna.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.nawoagency.dunna.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
