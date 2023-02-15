package com.ilsmp.base.action;

import java.util.List;

import com.github.mars05.crud.hub.common.dto.CodeGenerateReqDTO;
import com.github.mars05.crud.hub.common.dto.FileRespDTO;
import com.github.mars05.crud.hub.common.service.ProjectService;
import com.github.mars05.crud.hub.common.util.BeanUtils;
import com.ilsmp.base.dto.GenerateDTO;
import com.ilsmp.base.setting.CrudSettings;
import com.ilsmp.base.ui.CrudActionDialog;
import com.ilsmp.base.util.CrudUtils;
import com.intellij.ide.IdeView;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.DumbAwareRunnable;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class CreateCrudFromDbAction extends AnAction {
    private static final String NOTIFICATION_GROUP = "Base Code Generation";
    private final ProjectService projectService = CrudUtils.getBean(ProjectService.class);

    @Override
    public void update(AnActionEvent e) {
        final DataContext dataContext = e.getDataContext();
        final Presentation presentation = e.getPresentation();

        final boolean enabled = isAvailable(dataContext);

        presentation.setEnabled(enabled);
    }

    protected boolean isAvailable(DataContext dataContext) {
        final Project project = CommonDataKeys.PROJECT.getData(dataContext);
        final IdeView view = LangDataKeys.IDE_VIEW.getData(dataContext);
        return project != null && view != null && view.getDirectories().length != 0;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        VirtualFile virtualFile = e.getData(LangDataKeys.VIRTUAL_FILE);
        if (!virtualFile.isDirectory()) {
            virtualFile = virtualFile.getParent();
        }
        String projectPath = "";
        String basePackage = "";
        Module module = ModuleUtil.findModuleForFile(virtualFile, project);
        if (module != null) {
            String moduleRootPath = ModuleRootManager.getInstance(module).getContentRoots()[0].getPath();
            String actionDir = virtualFile.getPath();

            projectPath = moduleRootPath;
            String str = StringUtils.substringAfter(actionDir, moduleRootPath + "/src/main/java/");
            basePackage = StringUtils.replace(str, "/", ".");
        } else {
            projectPath = project.getPresentableUrl();
        }

        GenerateDTO generateDTO = CrudSettings.getGenerate(project.getName());
        generateDTO.setDataSource(null);
        generateDTO.setTables(null);
        generateDTO.setTableSource(1);

        if (StringUtils.isNotBlank(basePackage) && StringUtils.isBlank(CrudSettings.currentGenerate().getBasePackage())) {
            CrudSettings.currentGenerate().setBasePackage(basePackage);
        }
        if (StringUtils.isNotBlank(projectPath) && StringUtils.isBlank(CrudSettings.currentGenerate().getProjectPath())) {
            CrudSettings.currentGenerate().setProjectPath(projectPath);
        }
        CrudActionDialog dialog = new CrudActionDialog(project, module);
        if (!dialog.showAndGet()) {
            return;
        }
        DumbService.getInstance(project).runWhenSmart((DumbAwareRunnable) () -> WriteCommandAction.writeCommandAction(project).run(() -> {
            CrudUtils.runInBackground(new Task.Backgroundable(project, "代码生成中...", true) {
                @Override
                public void run(@NotNull ProgressIndicator indicator) {
                    try {
                        GenerateDTO currentGenerate = CrudSettings.currentGenerate();

                        List<FileRespDTO> fileRespDTOList = projectService.generateCode(BeanUtils.convertBean(currentGenerate,
                                CodeGenerateReqDTO.class));
                        List<FileRespDTO> successList = projectService.processFileToDisk(currentGenerate.getProjectPath(),
                                fileRespDTOList);

                        Notifications.Bus.notify(new Notification(NOTIFICATION_GROUP, "代码生成完成", "生成数量: " + successList.size()
                                + "\n失败数量: " + (fileRespDTOList.size() - successList.size())
                                + "\n项目路径: " + currentGenerate.getProjectPath(), NotificationType.INFORMATION), project);
                        //优化生成的所有Java类
                        CrudUtils.doOptimize(project);
                        VirtualFileManager.getInstance().refreshWithoutFileWatcher(true);
                    } catch (Exception ex) {
                        Notifications.Bus.notify(new Notification(NOTIFICATION_GROUP, "代码生成失败", ex.getMessage(), NotificationType.INFORMATION), project);
                    } finally {
                        CrudSettings.saveGenerate(project.getName());
                    }
                }
            });
        }));
    }
}
