package com.ilsmp.base.dao.mapper;

import java.io.Serializable;
import java.util.List;

import com.ilsmp.base.dao.model.DataSourceDO;
import com.ilsmp.base.setting.CrudSettings;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

/**
 * 数据源
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DataSourceMapper extends AbstractMapper<DataSourceDO> {

    @NotNull
    @Override
    protected List<DataSourceDO> getDataList() {
        return CrudSettings.getDataSources();
    }

    @NotNull
    @Override
    protected Class<DataSourceDO> getDataClass() {
        return DataSourceDO.class;
    }

    @Override
    protected Serializable getId(DataSourceDO dataSourceDO) {
        return dataSourceDO.getId();
    }

}