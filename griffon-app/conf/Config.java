import griffon.util.AbstractMapResourceBundle;

import javax.annotation.Nonnull;
import java.util.Map;

import static griffon.util.CollectionUtils.map;
import static java.util.Arrays.asList;

public class Config extends AbstractMapResourceBundle {
    @Override
    protected void initialize(@Nonnull Map<String, Object> entries) {
        map(entries)
            .e("application", map()
                .e("title", "floridfx")
                .e("startupGroups", asList("container"))
                .e("autoShutdown", true)
            )
            .e("mvcGroups", map()
                    .e("container", map()
                            .e("model", "editor.ContainerModel")
                            .e("view", "editor.ContainerView")
                            .e("controller", "editor.ContainerController")
                    )
                    .e("editor", map()
                            .e("model", "editor.EditorModel")
                            .e("view", "editor.EditorView")
                            .e("controller", "editor.EditorController")
                    )
            );
    }
}