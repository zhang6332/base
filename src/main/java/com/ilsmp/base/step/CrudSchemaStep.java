package com.ilsmp.base.step;

import javax.swing.*;

import com.github.mars05.crud.hub.common.dto.DataSourceDTO;
import com.github.mars05.crud.hub.common.enums.DatabaseTypeEnum;
import com.github.mars05.crud.hub.common.service.DataSourceService;
import com.ilsmp.base.dto.GenerateDTO;
import com.ilsmp.base.icon.CrudIcons;
import com.ilsmp.base.setting.CrudSettings;
import com.ilsmp.base.ui.CrudList;
import com.ilsmp.base.ui.ListElement;
import com.ilsmp.base.util.CrudUtils;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBScrollPane;

public class CrudSchemaStep extends ModuleWizardStep {
    private JPanel myMainPanel;
    private CrudList mySchemaList;
    private JLabel mySchemaLabel;
    private JLabel myPathLabel;
    private JScrollPane myScrollPane;

    private final DataSourceService dataSourceService = CrudUtils.getBean(DataSourceService.class);

    @Override
    public JComponent getComponent() {
        getList();
        return myMainPanel;
    }

    @Override
    public boolean isStepVisible() {
        GenerateDTO generateDTO = CrudSettings.currentGenerate();
        return 1 == generateDTO.getTableSource()
                && generateDTO.getDataSource() != null
                && (DatabaseTypeEnum.PG_SQL.getCode().equals(generateDTO.getDataSource().getDatabaseType())
                || DatabaseTypeEnum.ORACLE.getCode().equals(generateDTO.getDataSource().getDatabaseType()));
    }

    private void getList() {
        DataSourceDTO dataSource = CrudSettings.currentGenerate().getDataSource();
        if (dataSource != null) {
            mySchemaList.clearElement();
            for (String name : dataSourceService.allSchema(dataSource.getId(), dataSource.getDatabase())) {
                mySchemaList.addElement(new ListElement(CrudIcons.SCHEMA, name));
            }
        }
    }

    @Override
    public void updateDataModel() {

    }

    @Override
    public boolean validate() throws ConfigurationException {
        try {
            ListElement listElement = mySchemaList.getSelectedElement();
            if (listElement == null) {
                throw new Exception("请选择一个模式");
            }
            CrudSettings.currentGenerate().getDataSource().setSchema(listElement.getName());
        } catch (Exception e) {
            throw new ConfigurationException(e.getMessage(), "数据库打开失败");
        }
        return super.validate();
    }

    private void createUIComponents() {
        myScrollPane = new JBScrollPane();
        mySchemaLabel = new JBLabel();
    }
}
