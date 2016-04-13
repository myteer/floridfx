/*
 * Copyright 2008-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package editor;

import griffon.core.artifact.GriffonModel;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ArtifactProviderFor(GriffonModel.class)
public class EditorModel extends AbstractGriffonModel {
    private static final Logger LOG = LoggerFactory.getLogger(EditorModel.class);
    private Document document;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        firePropertyChange("document", this.document, this.document = document);
    }

    @Override
    public void mvcGroupDestroy() {
        LOG.info("EditorModel-00000");
    }
}