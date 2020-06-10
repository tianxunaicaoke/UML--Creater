# Quick - UML
### Getting Start
This tool will help developer to create PlantUML automatic by add annotations to source code. This tool is working for java project.
>Add the dependence to the module which you want to create PlantUML
~~~~
dependencies {
    implementation 'com.example.xtian:QuickUML:v1.0.1'
}
~~~~
#### How to use annotation create ClassUML 
>The annotation for ClassUML named @IncludeClass.
>Add the annotation above the class which you want to include in created UML.
>And this annotation only can be marked on class, if not will get the UMLAnnotationNotOnRightPlaceException.
>You can define the class belong to which node and which package by fill the attribute 'umlNode' and 'umlPackage'.
>You can add the note for this class by fill the attribute 'umlNote'.
>The QuickUML will automatic include super class/interface of the annotated class.
~~~
-------------------------
---- Example.java -----
@IncludeClass(umlNode = "example",umlPackage = "examplepackage",umlNote = "this is note of this class")
public class Example implements EInterface{
    ExampleA A;
    ...
}
-------------------------
---- EInterface.java -----
@IncludeClass(umlNode = "example",umlPackage = "interface")
public interface EInterface {
}
-------------------------
---- ExampleA.java -----
@IncludeClass(umlNode = "example", umlPackage = "examplepackage")
public class ExampleA {
    ...
}
~~~
>Then run compileDebugJavaWithJavac, the Plant UML file will be created under 'XXModule'\build\intermediates\classes\debug\UML
~~~
@startuml
node "example"{
 package "interface"{
  interface EInterface

}
 package "examplepackage"{
  class Example
  note top of Example : this is note of this\n class
  class ExampleA
}
}
class Example implements EInterface
Example *-- ExampleA
@enduml
~~~
>And the png will also created under the 'XXModule'\build\intermediates\classes\debug\UML

![avatar](https://github.com/tianxunaicaoke/UML--Creater/blob/master/UMLExample.png)


>The parameter of IncludeClass annotation has default value
~~~
 /**
 *umlNode default "Hello"
 *umlPackage default "David"
 *umlNote default ""
 **/
@IncludeClass
class example{

}
~~~
#### How to use annotation create Sequence UML
>The annotation for SequenceUML named @Step. 
>And this annotation only can be marked on Method, if not will get the UMLAnnotationNotOnRightPlaceException.
>And the @Step annotation is repeatable.
>The value of @Step("ClassName:MethodName") refer the invoke method.
>You can define the divider by fill the attribute 'divider'.
>You can define the group by fill the attribute 'group'. 
~~~
-------------------------
---- ExampleA.java -----
public class ExampleA {
    ...

    @Step(value = "ExampleB:init", divider = "init")
    @Step(value = "ExampleA:startWork",group = "Core part",divider = "start work")
    public void init() {
        b.init();
        ...
        startWork();
        ...
    }

    @Step("ExampleC:getAction")
    @Step("ExampleD:doAction")
    @Step("ExampleE:doFinish")
    public boolean startWork() {
        String action = ExampleC.getAction();
        ExampleD.doAction(action);
        ExampleE.doFinished();
        ....
    }
}
-------------------------
---- ExampleB.java -----
public class ExampleB {
    ...
    @Step("ExampleH:initEngine")
    public boolean init(){
      ExampleH.initEngine();
      ...
    }
}
-------------------------
---- ExampleD.java -----
public class ExampleD {
    ... 
    @Step(value = "ExampleF:doOnce",note = "this action is in another thread")
    public boolean doAction(String action){
      ExampleF.doOnce();
      ...
    }
}
~~~
>Then run compileDebugJavaWithJavac, the Plant UML file will be created under 'XXModule'\build\intermediates\classes\debug\UML
~~~
@startuml
  --> ExampleA : init
==init==
ExampleA --> ExampleB : init
ExampleB --> ExampleH : initEngine
activate ExampleH
ExampleH --> ExampleB : initEngine returned
deactivate ExampleH
==start work==
group Core part
ExampleA --> ExampleA : startWork
ExampleA --> ExampleC : getAction
ExampleA --> ExampleD : doAction
note over ExampleF : this action is in another\n thread 
ExampleD --> ExampleF : doOnce
activate ExampleF
ExampleF --> ExampleD : doOnce returned
deactivate ExampleF
ExampleA --> ExampleE : doFinish
end
@enduml
~~~
>And the png will also created under the 'XXModule'\build\intermediates\classes\debug\UML

![avatar](https://github.com/tianxunaicaoke/UML--Creater/blob/master/UMLSequenceExample.png)
