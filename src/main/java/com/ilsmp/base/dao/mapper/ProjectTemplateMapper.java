package com.ilsmp.base.dao.mapper;

import java.io.Serializable;
import java.util.List;

import com.ilsmp.base.dao.model.ProjectTemplateDO;
import com.ilsmp.base.setting.CrudSettings;
import org.jetbrains.annotations.NotNull;

public class ProjectTemplateMapper extends AbstractMapper<ProjectTemplateDO> {
    @NotNull
    @Override
    protected List<ProjectTemplateDO> getDataList() {
        return CrudSettings.getProjectTemplates();
    }

    @NotNull
    @Override
    protected Class<ProjectTemplateDO> getDataClass() {
        return ProjectTemplateDO.class;
    }

    @Override
    protected Serializable getId(ProjectTemplateDO projectTemplateDO) {
        return projectTemplateDO.getId();
    }

}
