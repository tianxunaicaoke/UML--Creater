package com.example.classdiagramlib.processer;

import com.example.classdiagramlib.annotation.Step;
import com.example.classdiagramlib.annotation.Steps;
import com.example.classdiagramlib.bean.UMLInvoke;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;


import static com.example.classdiagramlib.fileCreater.Template.ACTIVATE;
import static com.example.classdiagramlib.strategy.ProcessChecker.checkAnnotationIfOnMethod;

public class SequenceUMLProcessor implements UMLProcessor<UMLInvoke> {
    private List<UMLInvoke> invokes = new ArrayList<>();
    private UMLInvoke root;
    @Override
    public void configUMLElement(List<Element> elements, List<UMLInvoke> nodes) throws Exception {
        for (Element item : elements) {
            checkAnnotationIfOnMethod(item);
            UMLInvoke invoke = convert(item);
            invokes.add(invoke);
        }
    }

    private UMLInvoke convert(Element element) {
        ExecutableElement elementType = (ExecutableElement) element;
        String preMethod = elementType.getSimpleName().toString();
        String preClassName = elementType.getEnclosingElement().getSimpleName().toString();
        UMLInvoke out = new UMLInvoke();
        out.setPreClassName(preClassName);
        out.setPreMethod(preMethod);
        out.setHasReturnType(!elementType.getReturnType().toString().equals("void"));
        if (element.getAnnotation(Steps.class) != null) {
            Step[] steps = element.getAnnotation(Steps.class).value();
            for (Step step : steps) {
                String[] strings = step.value().split(":");
                UMLInvoke umlInvoke = new UMLInvoke();
                umlInvoke.setCurrentMethod(strings[1]);
                umlInvoke.setClassName(strings[0]);
                umlInvoke.setPreMethod(preMethod);
                umlInvoke.setPreClassName(preClassName);
                if (!step.note().equals("")) {
                    umlInvoke.setNote(step.note());
                }
                if (!step.divider().equals("")) {
                    umlInvoke.setDivider(step.divider());
                }
                if (!step.group().equals("")) {
                    umlInvoke.setGroup(step.group());
                }
                out.addInvoke(umlInvoke);
            }
        } else if (element.getAnnotation(Step.class) != null) {
            Step step = element.getAnnotation(Step.class);
            String[] strings = step.value().split(":");
            UMLInvoke umlInvoke = new UMLInvoke();
            umlInvoke.setCurrentMethod(strings[1]);
            umlInvoke.setClassName(strings[0]);
            umlInvoke.setPreMethod(preMethod);
            umlInvoke.setPreClassName(preClassName);
            umlInvoke.setHasReturnType(!elementType.getReturnType().toString().equals("void"));
            if (!step.note().equals("")) {
                umlInvoke.setNote(step.note());
            }
            if (!step.divider().equals("")) {
                umlInvoke.setDivider(step.divider());
            }
            if (!step.group().equals("")) {
                umlInvoke.setGroup(step.group());
            }
            out.addInvoke(umlInvoke);
        }
        return out;
    }

    @Override
    public void buildUMLGraph(List<UMLInvoke> umlInvokes) {
        buildInvokeTree();
        UpdateReturnInvok(root);
        umlInvokes.add(root);
    }

    private void buildInvokeTree() {
        int count = invokes.size();
        for(int index=0;index<count;index++){
            UMLInvoke invoke = invokes.get(index);
            boolean isRoot = true;
            for (int i = 0; i < invokes.size(); i++) {
                List<UMLInvoke> leafs = getInvokeLeaf(invokes.get(i));
                for (int j = 0; j < leafs.size(); j++) {
                    if(UMLEquals(invoke,leafs.get(j))){
                        leafs.get(j).setInvokes(invoke.getInvokes());
                        isRoot = false;
                    }
                }
            }
            if(isRoot){
               root = invoke;
            }
        }
    }

    private void UpdateReturnInvok(UMLInvoke invoke) {
        if(invoke.isHasReturnType()){
            invoke.setActivate(ACTIVATE + invoke.getClassName());
        }
        if (invoke.getInvokes().isEmpty()) {
            return ;
        }
        for (UMLInvoke invoke1 : invoke.getInvokes()) {
             UpdateReturnInvok(invoke1);
        }
    }

    private List<UMLInvoke> getInvokeLeaf(UMLInvoke invoke){
        List<UMLInvoke> invokes = new ArrayList<>();
        if(invoke.getInvokes().isEmpty()){
            invokes.add(invoke);
           return invokes;
        }
        for(UMLInvoke umlInvoke:invoke.getInvokes()){
            invokes.addAll(getInvokeLeaf(umlInvoke));
        }
        return invokes;
    }

    private boolean UMLEquals(UMLInvoke a, UMLInvoke b) {
        return a.getPreClassName().equals(b.getClassName()) && a.getPreMethod().equals(b.getCurrentMethod());
    }
}
