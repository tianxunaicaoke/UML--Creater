package com.example.classdiagramlib.processer;

import com.example.classdiagramlib.annotation.Step;
import com.example.classdiagramlib.bean.UMLInvoke;

import java.util.HashMap;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;


import static com.example.classdiagramlib.fileCreater.Template.ACTIVATE;
import static com.example.classdiagramlib.fileCreater.Template.DEACTIVATE;
import static com.example.classdiagramlib.fileCreater.Template.RETURNED;
import static com.example.classdiagramlib.strategy.ProcessChecker.checkAnnotationIfOnMethod;

public class SequenceUMLProcessor implements UMLProcessor<UMLInvoke> {
    private HashMap<Integer, UMLInvoke> invokes = new HashMap<>();

    @Override
    public void configUMLElement(List<Element> elements, List<UMLInvoke> nodes) throws Exception {
        for (Element item : elements) {
            checkAnnotationIfOnMethod(item);
            UMLInvoke invoke = convert(item);
            invokes.put(item.getAnnotation(Step.class).value(), invoke);
        }
    }

    private UMLInvoke convert(Element element) {
        ExecutableElement elementType = (ExecutableElement) element;
        Step step = element.getAnnotation(Step.class);
        UMLInvoke umlInvoke = new UMLInvoke();
        umlInvoke.setCurrentMethod(elementType.getSimpleName().toString());
        umlInvoke.setClassName(elementType.getEnclosingElement().getSimpleName().toString());
        umlInvoke.setHasReturnType(!elementType.getReturnType().toString().equals("void"));
        if (!step.note().equals("")) {
            umlInvoke.setNote(step.note());
        }
        if (!step.divider().equals("")) {
            umlInvoke.setDivider(step.divider());
        }
        return umlInvoke;
    }

    @Override
    public void buildUMLGraph(List<UMLInvoke> umlInvokes) {
        sortInvokeList(invokes);
        umlInvokes.add(invokes.get(1));
    }

    private void sortInvokeList(HashMap<Integer, UMLInvoke> invokes) {
        for (int i = 1; i < invokes.size(); i++) {
            invokes.get(i).setNext(invokes.get(i + 1));
            invokes.get(i + 1).setPreClassName(invokes.get(i).getClassName());
            invokes.get(i + 1).setNeedToDrawReturn(!invokes.get(i).getClassName().equals(invokes.get(i + 1).getClassName()));

        }

        UMLInvoke end = invokes.get(invokes.size());
        for (int i = invokes.size(); i > 0; i--) {
            if (invokes.get(i).isNeedToDrawReturn() && invokes.get(i).isHasReturnType()) {
                invokes.get(i).setActivate(ACTIVATE + invokes.get(i).getClassName());
                UMLInvoke invoke = new UMLInvoke();
                invoke.setPreClassName(invokes.get(i).getClassName());
                invoke.setClassName(invokes.get(i).getPreClassName());
                invoke.setCurrentMethod(invokes.get(i).getCurrentMethod()+RETURNED);
                invoke.setActivate(DEACTIVATE + invokes.get(i).getClassName());
                end.setNext(invoke);
                end = end.getNext();
            }
        }
    }
}
