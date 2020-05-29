# UML- Creater
### Getting Start
This tool will help developer to create PlantUML automatic by add annotation. This tool is working for java project.

---
>Add the dependence to the module which you want to create PlantUML
~~~~
dependencies {
    implementation 'com.example.xtian:QuickUML:v1.0.0'
}
~~~~
>Add the annotation above the class
~~~
@IncludeClass(umlNode = "Hello",umlPackage = "world",umlNote = "this is note of this class")
class example{

}
~~~
>Then run compileDebugJavaWithJavac, the Plant UML file will be created under 'XXModule'\build\intermediates\classes\debug\UML
~~~
@startuml
node "Hello"{
 package "world"{
  class example
  note top of example : this is note of this\n class 
 }
}
@enduml
~~~
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
