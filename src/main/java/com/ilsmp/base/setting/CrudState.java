package com.ilsmp.base.setting;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.ilsmp.base.dao.model.DataSourceDO;
import com.ilsmp.base.dao.model.ProjectTemplateDO;
import com.ilsmp.base.dto.GenerateDTO;
import lombok.Data;

@Data
public class CrudState {
    private List<DataSourceDO> dataSources = new CopyOnWriteArrayList<>();
    private List<ProjectTemplateDO> projectTemplates = new CopyOnWriteArrayList<>();

    private Map<String, GenerateDTO> generateInfoMap = new ConcurrentHashMap<>();

    private boolean initialized = false;

}
