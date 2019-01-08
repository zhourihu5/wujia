package com.wingsofts.mvphelper.biz.file.generator.impl;

import com.intellij.ide.fileTemplates.JavaTemplateUtil;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;

/**
 * The file generator works in Contract/Presenter Mode.
 *
 * @author DengChao
 * @since 2017/4/10
 */
@SuppressWarnings("ConstantConditions")
public class JavaModeFileGenerator extends BaseFileGenerator {

    public JavaModeFileGenerator(Project project, PsiDirectory contractDir, PsiDirectory modelDir, PsiDirectory presenterDir, String prefix) {
        super(project, contractDir, modelDir, presenterDir, prefix);
    }

    @Override
    public void start() {
        generateFile(myContractDir, myPrefix + "Contract", JavaTemplateUtil.INTERNAL_INTERFACE_TEMPLATE_NAME, (javaFile, psiClass) -> {
            PsiClass model = myFactory.createInterface("Model");
            PsiClass view = myFactory.createInterface("View");//You have to achieve 'View' yourself.
            PsiClass presenter = myFactory.createInterface("Presenter");

            PsiClass basePresenterClass = myShortNamesCache.getClassesByName("BasePresenter", myProjectScope)[0];
            PsiClass baseViewClass = myShortNamesCache.getClassesByName("BaseView", myProjectScope)[0];
            PsiClass baseModleClass = myShortNamesCache.getClassesByName("IBaseModle", myProjectScope)[0];


            model.getExtendsList().add(myFactory.createClassReferenceElement(baseModleClass));
            view.getExtendsList().add(myFactory.createClassReferenceElement(baseViewClass));
            presenter.getExtendsList().add(myFactory.createClassReferenceElement(basePresenterClass));

            model.getModifierList().setModifierProperty("public", false);//Remove modifier
            view.getModifierList().setModifierProperty("public", false);
            presenter.getModifierList().setModifierProperty("public", false);

            psiClass.add(model);
            psiClass.add(view);
            psiClass.add(presenter);
            psiClass.getModifierList().setModifierProperty("public", true);//force 'public interface myPrefixContract'

            FileEditorManager fileEditorManager = FileEditorManager.getInstance(myProject);
            OpenFileDescriptor fileDescriptor = new OpenFileDescriptor(myProject, javaFile.getVirtualFile());
            fileEditorManager.openTextEditor(fileDescriptor, true);//Open the Contract

            generateModel();
            generatePresenter();
        });
    }

    private void generatePresenter() {
        generateFile(myPresenterDir, myPrefix + "Presenter", JavaTemplateUtil.INTERNAL_CLASS_TEMPLATE_NAME, (javaFile, psiClass) -> {
            PsiClass contractClass = myShortNamesCache.getClassesByName(myPrefix + "Contract", myProjectScope)[0];
            PsiClass presenter = contractClass.findInnerClassByName("Presenter", false);//don't need to search base
            PsiClass rxPresenterclass = myShortNamesCache.getClassesByName("RxPresenter",myProjectScope)[0];
            psiClass.getExtendsList().add(myFactory.createClassReferenceElement(rxPresenterclass));
            psiClass.getImplementsList().add(myFactory.createClassReferenceElement(presenter));
            psiClass.getModifierList().setModifierProperty("public", true);//force 'public interface myPrefixContract'
        });
    }

    private void generateModel() {
        generateFile(myModelDir, myPrefix + "Model", JavaTemplateUtil.INTERNAL_CLASS_TEMPLATE_NAME, (javaFile, psiClass) -> {
            PsiClass contractClass = myShortNamesCache.getClassesByName(myPrefix + "Contract", myProjectScope)[0];
            PsiClass model = contractClass.findInnerClassByName("Model", false);//don't need to search base
            psiClass.getImplementsList().add(myFactory.createClassReferenceElement(model));
            psiClass.getModifierList().setModifierProperty("public", true);//force 'public interface myPrefixContract'
        });
    }

}
