package com.ilsmp.base.ui;

import com.ilsmp.base.step.CodeStep;
import com.ilsmp.base.step.CrudConnStep;
import com.ilsmp.base.step.CrudDbStep;
import com.ilsmp.base.step.CrudSchemaStep;
import com.ilsmp.base.step.CrudTableStep;
import com.ilsmp.base.step.DdlStep;
import com.ilsmp.base.step.MyTemplateStep;
import com.ilsmp.base.step.PrimaryKeySelectStep;
import com.intellij.ide.util.newProjectWizard.AbstractProjectWizard;
import com.intellij.ide.util.newProjectWizard.StepSequence;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;


public class CrudActionDialog extends AbstractProjectWizard {
    private final StepSequence mySequence = new StepSequence();
    private Project myProject;
    private Module myModule;

    public CrudActionDialog(Project project, Module module) {
        super("代码生成", project, "");
        myProject = project;
        myModule = module;
        ModuleWizardStep[] wizardSteps = createWizardSteps();
        for (ModuleWizardStep wizardStep : wizardSteps) {
            mySequence.addCommonStep(wizardStep);
        }
        for (ModuleWizardStep step : mySequence.getAllSteps()) {
            addStep(step);
        }
        init();
    }

    @Override
    public StepSequence getSequence() {
        return mySequence;
    }

    public ModuleWizardStep[] createWizardSteps() {
        return new ModuleWizardStep[]{
                new MyTemplateStep(),
                new PrimaryKeySelectStep(),
                new DdlStep(),
                new CrudConnStep(),
                new CrudDbStep(),
                new CrudSchemaStep(),
                new CrudTableStep(),
                new CodeStep()
        };
    }

    @Nullable
    @Override
    protected String getHelpID() {
        return CrudActionDialog.class.getName();
    }

}

