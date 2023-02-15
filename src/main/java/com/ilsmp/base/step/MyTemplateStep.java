package com.ilsmp.base.step;

import javax.swing.*;

import com.github.mars05.crud.hub.common.dto.ProjectTemplateDTO;
import com.github.mars05.crud.hub.common.util.BeanUtils;
import com.ilsmp.base.dto.ProjectTemplateRespDTO;
import com.ilsmp.base.service.ProjectTemplateService;
import com.ilsmp.base.setting.CrudSettings;
import com.ilsmp.base.ui.CrudList;
import com.ilsmp.base.ui.ListElement;
import com.ilsmp.base.util.CrudUtils;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.ui.components.JBScrollPane;

public class MyTemplateStep extends ModuleWizardStep {
    private JPanel myMainPanel;
    private CrudList templateList;
    private JScrollPane myScrollPane;

    private ProjectTemplateDTO curr;

    private final ProjectTemplateService projectTemplateService = CrudUtils.getBean(ProjectTemplateService.class);

    public MyTemplateStep() {

    }

    @Override
    public JComponent getComponent() {
        getList();
        return myMainPanel;
    }

    @Override
    public void updateDataModel() {

    }

    @Override
    public boolean validate() throws ConfigurationException {
        try {
            ProjectTemplateRespDTO projectTemplateRespDTO = getSelectedProjectTemplate();
            if (projectTemplateRespDTO == null) {
                throw new Exception("请选择一个项目");
            }
            curr = BeanUtils.convertBean(projectTemplateRespDTO, ProjectTemplateDTO.class);
            CrudSettings.currentGenerate().setProjectTemplate(curr);
        } catch (Exception e) {
            throw new ConfigurationException(e.getMessage(), "验证失败");
        }
        return super.validate();
    }

    private void getList() {
        if (curr == null) {
            templateList.clearElement();
            for (ProjectTemplateRespDTO projectTemplateDTO : projectTemplateService.list()) {
                templateList.addElement(new ListElement(null,
                        projectTemplateDTO.getId(), projectTemplateDTO.getName() + "（" + projectTemplateDTO.getOrganizationName() + "）"));
            }
        }
    }

    public ProjectTemplateRespDTO getSelectedProjectTemplate() {
        ListElement selectedElement = templateList.getSelectedElement();
        if (selectedElement == null) {
            return null;
        }
        return projectTemplateService.detail(selectedElement.getId());
    }

    private void createUIComponents() {
        myScrollPane = new JBScrollPane();
    }
}
