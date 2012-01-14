/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.jsr299.tck.tests.lookup.modules.broken;

import static org.jboss.jsr299.tck.TestGroups.JAVAEE_FULL;

import javax.enterprise.inject.spi.DeploymentException;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.ShouldThrowException;
import org.jboss.jsr299.tck.AbstractJSR299Test;
import org.jboss.jsr299.tck.shrinkwrap.EnterpriseArchiveBuilder;
import org.jboss.jsr299.tck.shrinkwrap.WebArchiveBuilder;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecVersion;
import org.testng.annotations.Test;

/**
 * 
 * @author Martin Kouba
 */
@SpecVersion(spec = "cdi", version = "20091101")
public class DisabledSessionBeanInjectionNotAvailableTest extends AbstractJSR299Test {

    @ShouldThrowException(DeploymentException.class)
    @Deployment
    public static EnterpriseArchive createTestArchive() {

        EnterpriseArchive enterpriseArchive = new EnterpriseArchiveBuilder().noDefaultWebModule()
                .withTestClassDefinition(DisabledSessionBeanInjectionNotAvailableTest.class)
                .withClasses(DisabledEjbFoo.class, EjbFooLocal.class).withBeanLibrary(BrokenFoo.class, BrokenBar.class).build();

        enterpriseArchive.addAsModule(new WebArchiveBuilder().notTestArchive().withDefaultEjbModuleDependency()
                .withClasses(DisabledSessionBeanInjectionNotAvailableTest.class, BrokenWebBar.class).build());

        return enterpriseArchive;
    }

    @Inject
    BrokenBar bar;

    @Test(groups = JAVAEE_FULL)
    @SpecAssertion(section = "5.1.4", id = "j")
    public void testInjection() throws Exception {
    }

}
