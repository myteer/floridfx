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

import griffon.core.artifact.GriffonView;
import griffon.metadata.ArtifactProviderFor;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import org.codehaus.griffon.runtime.javafx.artifact.AbstractJavaFXGriffonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@ArtifactProviderFor(GriffonView.class)
public class EditorView extends AbstractJavaFXGriffonView {
    private static final Logger LOG = LoggerFactory.getLogger(EditorView.class);
    private EditorModel model;
    private ContainerView parentView;
    private String tabName;

    @FXML
    private TextArea editor;

    private Tab tab;

    @Override
    public void initUI() {
        tab = new Tab(tabName);
        tab.setId(getMvcGroup().getMvcId());
        tab.setContent(loadFromFXML());
        parentView.getTabGroup().getTabs().add(tab);

        model.getDocument().addPropertyChangeListener("contents", (e) -> editor.setText((String) e.getNewValue()));

        editor.textProperty().addListener((observable, oldValue, newValue) ->
            model.getDocument().setDirty(!Objects.equals(editor.getText(), model.getDocument().getContents())));
    }

    public TextArea getEditor() {
        return editor;
    }

    @Override
    public void mvcGroupDestroy() {
        LOG.info("EditorView-00000");
        /*runInsideUISync(() -> {
                    LOG.info("111111");
                    parentView.getTabGroup().getTabs().remove(tab);
                    LOG.info("222222");
                }
        );*/
    }
}