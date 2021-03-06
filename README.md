# Quick - UML
### Getting Start
In some case we need to generate UML diagram for exist code for some reason 
like warm up with some new project,or learn some new technical framework...
This tool will help developer to create PlantUML automatic by add annotations to source code. 
And this tool is only working for java based project now.
>Add the dependence to the module which need to generate uml by this tool
~~~~
dependencies {
    compileOnly 'net.sourceforge.plantuml:plantuml:1.2020.16'
    implementation 'com.example.xtian:QuickUML:v1.0.3'
    annotationProcessor 'com.example.xtian:QuickUML:v1.0.3'
}
~~~~

>Add for kotlin
~~~~
apply plugin: 'kotlin-kapt'
...

dependencies {
    compileOnly 'net.sourceforge.plantuml:plantuml:1.2020.16'
    implementation 'com.example.xtian:QuickUML:v1.0.3'
    kapt 'com.example.xtian:QuickUML:v1.0.3'
}
~~~~
#### How to use annotation to create ClassUML 
>The annotation for ClassUML named @IncludeClass.
>Add the annotation above the class which include in generated UML.
>And this annotation only can be marked on class, if not process will throw the UMLAnnotationNotOnRightPlaceException.
>You can define the class node and package by fill the attribute 'umlNode' and 'umlPackage'.
>And add the note for this class by fill the attribute 'umlNote'.
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
>Then run compileDebugJavaWithJavac for target module, the Plant UML file will be generated under 'XXModule'\build\intermediates\classes\debug\UML
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
>And the png will also generated under the 'XXModule'\build\intermediates\classes\debug\UML

![UMLExample.png](https://i.loli.net/2020/10/22/awvcYxNhGXKr76J.png)


>The parameter of IncludeClass annotation has default values
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
#### How to use annotation to create Sequence UML
>The annotation for SequenceUML named @Step. 
>And this annotation only can be marked on Method, if not process will throw the UMLAnnotationNotOnRightPlaceException.
>And the @Step annotation is repeatable.
>The value of @Step("ClassName:MethodName") refer the invoke method.
>You can define the divider by fill the attribute 'divider'.
>And define the group by fill the attribute 'group'. 
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
>Then run compileDebugJavaWithJavac, the Plant UML file will be generated under 'XXModule'\build\intermediates\classes\debug\UML
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
>And the png will also generated under the 'XXModule'\build\intermediates\classes\debug\UML

[![UMLSequenceExample.png](https://i.loli.net/2020/10/22/8QujP6W9BiglYFw.png)](https://github.com/tianxunaicaoke/UML--Creater/blob/master/UMLSequenceExample.png)

### Know Issue:
    1. For class UML, the inner class not support very fine, will update in V1.0.2 version.
    2. For sequence UML, the if and repeat step will support in V1.0.3 version.
### Contribute:
Anyone can rise the issue in https://github.com/tianxunaicaoke/UML--Creater/issues, and anyone can contribute code by pull Request.Thanks for reading.    
