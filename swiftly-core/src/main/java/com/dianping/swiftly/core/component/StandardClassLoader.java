/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.dianping.swiftly.core.component;

import org.slf4j.Logger;

import java.net.URL;
import java.net.URLClassLoader;

public class StandardClassLoader extends URLClassLoader {

    private static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ClassLoaderFactory.class);

    public StandardClassLoader(URL repositories[]) {
        super(repositories);
    }

    public StandardClassLoader(URL repositories[], ClassLoader parent) {
        super(repositories, parent);
    }

    // @Override
    /*
     * protected Class<?> findClass(String name) throws ClassNotFoundException { Class clazz = null; try { try { clazz =
     * super.findClass(name); } catch (ClassNotFoundException cnfe) { // Ignore - will search internal repositories next
     * LOGGER.warn("do not find in system class loader"); } catch (AccessControlException ace) {
     * LOGGER.warn("WebappClassLoader.findClassInternal(" + name + ") security exception: " + ace.getMessage(), ace);
     * throw new ClassNotFoundException(name, ace); } catch (RuntimeException e) { if (LOGGER.isTraceEnabled())
     * LOGGER.trace("      -->RuntimeException Rethrown", e); throw e; } if ((clazz == null)) { try { clazz =
     * findClassInternal(name); } catch (ClassNotFoundException cnfe) { } catch (AccessControlException ace) {
     * LOGGER.warn("WebappClassLoader.findClassInternal(" + name + ") security exception: " + ace.getMessage(), ace);
     * throw new ClassNotFoundException(name, ace); } catch (RuntimeException e) { if (LOGGER.isTraceEnabled())
     * LOGGER.trace("      -->RuntimeException Rethrown", e); throw e; } } if (clazz == null) { try { clazz =
     * super.findClass(name); } catch (AccessControlException ace) { LOGGER.warn("WebappClassLoader.findClassInternal("
     * + name + ") security exception: " + ace.getMessage(), ace); throw new ClassNotFoundException(name, ace); } catch
     * (RuntimeException e) { if (LOGGER.isTraceEnabled()) LOGGER.trace("      -->RuntimeException Rethrown", e); throw
     * e; } } if (clazz == null) { if (LOGGER.isDebugEnabled())
     * LOGGER.debug("    --> Returning ClassNotFoundException"); throw new ClassNotFoundException(name); } } catch
     * (ClassNotFoundException e) { if (LOGGER.isTraceEnabled())
     * LOGGER.trace("    --> Passing on ClassNotFoundException"); throw e; } return (clazz); }
     */
}
