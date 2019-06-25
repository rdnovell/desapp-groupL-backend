package arquitectura;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.*;
import ar.edu.unq.groupl.app.service.annotation.Valid;
import org.aspectj.lang.annotation.Aspect;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import static com.tngtech.archunit.core.domain.JavaModifier.*;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.all;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ArquitecturaTest {

    private JavaClasses classes;

    @Before
    public void setup() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DONT_INCLUDE_TESTS)
                .importPackages("ar.edu.unq.groupl.app");
    }

    @Test
    public void persisenceClassesShouldOnlyBeAccessedByServiceClasses() {
        classes()
                .that().resideInAPackage("ar.edu.unq.groupl.app.persistence")
                .should().onlyBeAccessed().byAnyPackage("ar.edu.unq.groupl.app.service")
                .check(classes);
    }

    @Test
    public void serviceClassesShouldBeNamedXServiceORXSender() {
        classes()
                .that().resideInAPackage("ar.edu.unq.groupl.app.service")
                .should().haveSimpleNameEndingWith("Service")
                .orShould().haveSimpleNameEndingWith("Sender")
                .check(classes);
    }

    @Test
    public void repositoryClassesShouldHaveSpringRepositoryAnnotation() {
        classes()
                .that().resideInAPackage("ar.edu.unq.groupl.app.persistence")
                .should().beAnnotatedWith(Repository.class)
                .check(classes);
    }

    @Test
    public void aspectClassesShouldHaveAspectAnnotation() {
        classes()
                .that().resideInAPackage("ar.edu.unq.groupl.app.service.aspect")
                .should().beAnnotatedWith(Aspect.class)
                .check(classes);
    }

    @Test
    public void baseDirectoryShouldHaveAnApplicationClass() {
        classes()
                .that().resideInAPackage("ar.edu.unq.groupl.app")
                .should().haveSimpleNameEndingWith("Application")
                .orShould().haveSimpleNameEndingWith("CORSResponseFilter")
                .check(classes);
    }

    @Test
    public void domainClassesShouldBePublic() {
        classes()
                .that().resideInAPackage("ar.edu.unq.groupl.app.model")
                .should()
                .bePublic()
                .check(classes);
    }

    @Test
    public void utilClassesShouldBePublic() {
        classes()
                .that().resideInAPackage("ar.edu.unq.groupl.app.model.util")
                .should().haveSimpleNameEndingWith("Loader")
                .orShould().haveSimpleNameEndingWith("Util")
                .andShould()
                .bePublic()
                .check(classes);
    }

    @Test
    public void restClassesShouldBePublic() {
        classes()
                .that().resideInAPackage("ar.edu.unq.groupl.app.webservice.endpoint")
                .should().haveSimpleNameEndingWith("Rest")
                .andShould()
                .bePublic()
                .check(classes);
    }

    @Test
    public void restClassesShouldHaveSpringComponentAnnotation() {
        classes()
                .that().resideInAPackage("ar.edu.unq.groupl.app.webservice.endpoint")
                .and().haveSimpleNameEndingWith("Rest")
                .and().doNotHaveSimpleName("Rest")
                .should().beAnnotatedWith(Component.class)
                .check(classes);
    }

    @Test
    public void serviceClassesShouldHaveSpringComponentAnnotationORServiceAnnotation() {
        classes()
                .that().resideInAPackage("ar.edu.unq.groupl.app.service")
                .should().beAnnotatedWith(Component.class)
                .orShould().beAnnotatedWith(Service.class)
                .check(classes);
    }

    @Test
    public void utilityClassesShouldHavePublicMethods() {
        ClassesTransformer<JavaMethod> utilityMethods = new AbstractClassesTransformer<JavaMethod>("utility methods") {
            @Override
            public Iterable<JavaMethod> doTransform(JavaClasses classes) {
                Set<JavaMethod> result = new HashSet<>();
                for (JavaClass javaClass : classes) {
                    if(javaClass.getSimpleName().endsWith("Util")) {
                        result.addAll(javaClass.getMethods());
                    }
                }
                return result;
            }
        };

        ArchCondition<JavaMethod> havePublicStaticFunctions = new ArchCondition<JavaMethod>("be public and static ") {
            @Override
            public void check(JavaMethod method, ConditionEvents events) {
                boolean publicStaticFunctions = method.getModifiers().contains(PUBLIC) && method.getModifiers().contains(STATIC);
                String message = String.format("%s is not public and static",  method.getFullName());
                events.add(new SimpleConditionEvent(method, publicStaticFunctions, message));
            }
        };

        all(utilityMethods).should(havePublicStaticFunctions).check(classes);
    }

    @Test
    public void testClassesShouldBeNamedXTest() {
        classes()
                .that().resideInAPackage("model")
                .should().haveSimpleNameEndingWith("Test")
                .check(classes);
    }
    
    @Test
    public void serviceMethodsWithValidAnnotationMustHaveAtLeastOneArgumentToValidate() {
    	Set<Method> methodsAnnotatedWith = new Reflections("ar.edu.unq.groupl.app.service", new MethodAnnotationsScanner()).getMethodsAnnotatedWith(Valid.class);
		Predicate<Method> hasMoreThanOneArgument = method -> method.getParameterCount() > 0;
		assertTrue(methodsAnnotatedWith.stream().allMatch(hasMoreThanOneArgument));
    }

}
