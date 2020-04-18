# cspl
Compact Statement Programming Language v4.2.1 
(Formaly: Quick Statement Programing Language)

# About
CSPL is a dynamicly typed, interpreted programing language written in Java by MRH0 (aka MRH/mrhminer/hminer.lll).
cspl supports both procedural and functional programming styles and is extendable through java modules.
Syntax is sort of a  mashup between Javascript and Python.
cspl is purpose built to be good for prototyping programming ideas and does so with a deep but intuitive syntax.

# Language

Variables & Values:
```
//Variables:
x = 5; //x = 5
x = x-1; //x = 4
x++; //x = 5
x--; //x = 4
c = (((x*4)/2)+2) //c = 10

j = 5 + 6; //j = 11
j = j + 5; //j = 16

CONSTANTVALUE = 5; //Constant when variable name is all capital letters


//Tail keywords
out "Hello World"[1]; //Prints 'e' to out stream.
out "Hello World"[0,4]; //Prints 'Hello'
error "An Error Message"; //Prints message to error stream.
assert condition //Prints assertion error if condition is false.
let f=5;
val f;

//Strings:
text = "Hi my name is ";
j = j + "MrH."; //Joins strings : "Hi my name is MrH."
j = j - "."; //Removes string from string : "Hi my name is MrH"
j/" "; //Splits string to array : ['Hi','my','name','is','MrH']
```
Functions:
```
//Define:
func x1[arg1, arg2, arg3="!"]:
  exit "Hello"+arg1+arg3; //Return value.
  
func x2[arg1="world"]: //Function default value
  exit "Hello"+arg1;

//Call:
x1[" World", arg3="?"]; //returns "Hello World?"
x2[" World!"]; //returns "Hello World!"
x2[]; //returns "Helloworld"

//Chain (functional):
func double[]:
  exit this*2;
x = 5;
out x#double[]#double[]; //Prints 20

arr = new [1,2,3,4];
func x3[val, index, array]:
  exit (val+1);
out arr#map[x3]; //Prints [2,3,4,5]
out arr#map[[o, i, a] => {o+1}]; //Prints [2,3,4,5]
``` 
Operators:
```
Math: '+ - * / % ++ -- **'
Assigning: '= += -= *= /= %='
Boolean: '&& || ! == < > <= >='
Shift & Rotate: '<< >> <<< >>>'
Push & Pull: '<- ->' //Example: list = new [1,2,3]; list -> x; //list = [2,3], x = 1
Bitwise: '& | ^'
Contains: '?' //Example: 5 ? $[2,4,5,7] : true
Round: '~' //Example: 5.72~ : 6.0

Is type: 'is' //Example: 56 is STRING : false (0)
As type: 'as' //Example: 56 as STRING : "56"
Range iterator: '...' //Example: 0...10 : range iterator from 0 to 10
Iterator operator: 'where' //Example: new [5,10,15,20,25] where fn[e]{e >= 15} : returns iterator of elements [15,20,25]
Variable iterator: 'in of' //Example: let i in 0...10 : iterate i from 0 to 10

```
Arrays:
```
//Define:
a = new []; //Creates empty array.
a = $[]; //Creates empty array.
a = new [5,3,new[6,4],1]; //Creates array : [5.0,3.0,[6.0,4.0],1.0]

a = new [1,2,4,8];

//Accessor:
out a[0]; // : 1
out a[-1]; // : 8

out a[0,0]; // : [1]
out a[-1, -1]; // : [8]

out a[0, 3]; // : [1.0, 2.0, 4.0, 8.0]
out a[3, 0]; // : [8.0, 4.0, 2.0, 1.0]

out a[0, -1]; // : [1.0, 2.0, 4.0, 8.0]
out a[-1, 0]; // : [8.0, 4.0, 2.0, 1.0]

out a[-2, -1]; // : [4.0, 8.0]
out a[-1, -2]; // : [8.0, 4.0]

out a[0...2]; // : [1.0, 2.0, 4.0]
out a[2...0]; // : [8.0, 4.0, 2.0]

out a[0...-1]; // : [1.0, 8.0]

out a[new[0, -1]]; // : [1.0, 8.0]

//Operate:
a + 4; //Adds element 4 : [5.0,3.0,2.0,1.0,4.0]
a - 3; //Removes element at index 3 : [5.0,2.0,1.0,4.0]
a / 1; //Splits array at index 2 : [[5.0], [1.0, 4.0]]
a * 2; //Doubles the array : [5.0,1.0,4.0,5.0,1.0,4.0]
a * "|"; //Joins elements in array with "|" : "5.0|1.0|4.0|5.0|1.0|4.0"
a ? 4 //Array contains 4 : true
a ? 9 //Array contains 9 : false
a[]; //Get first depth size : 6
a[0] //Get first element : 5.0
a[1,3] //Get array of elements 1 to 3 : [1.0,4.0,5.0]
a[0] = 5; //Assigning value 5 to index 0

//Fuctions:
a#clear[]; //Clears array : []
a#add["World"]; //Adds element "World" to the end of the array : ["Hello"]
a#add[0, "Hello"]; //Adds element "Hello" to array before index 0 (same as #enqueue[value]) : ["Hello", "World"]
a#remove["Hello"]; //Removes element "Hello" : ["World"]
a#set[0, "Hello"]; //Sets first element to "Hello" : ["Hello"];
a#push["World"]; //Stack push : ["Hello", "World]"
a#collapse[]; //Returns collapsed string : "HelloWorld"
a#removeAt[0]; //Removes element at index 0 : ["World"]
a#pop[]; //Stack pop element : "World"
a#dequeue[]; //Queue dequeue element (a is empty) : undefined

a = new [3,2,4,6];
a#sort[] //Sorts array : [2,3,4,6]
a#toJSON[] //Get json string : "[2,3,4,6]"
a = new []#fromJSON["[4,5,[2,4,'text']]"] //Object from json : [4,5,[2,4,"text"]]
```
Objects:
```
//Define:
obj = new {}; //Creates empty object.
obj = ${}; //Creates empty object.
obj = new {a=1, b=2}; //Creates object with elements : {a=1, b=2}
obj = new {a=1, b=new{a=3}, x=4}; //Creates object with elements : {a=1, b={a=3}, x=4}}

//Operate:
obj["a"]; //Get element 'a' : 1
obj["a", "b"]; //Get array : [1, {a=3}]
obj.a; //Get element 'a' : 1
obj[]; //Get first depth size : 2;
obj + new{y=7}; //Join objects : {a=1, b=new{a=3}, x=4, y=7}
obj ? 1 //Object contains  1 : true
obj#keys[] ? "a" //Object has key 'a' : true
obj["a"] = 2; // Assigning value 2 to object at key "a"
obj.a = 3; // Assigning value 3 to object at key "a"

//Functions:
obj#add["g", "text"]; //Adds element to object : {a=3, b={a=3}, x=4, y=7, g="text"}
obj#set["a", 5] //Set element value : {a=5, b=new{a=3}, x=4, y=7, g="text"}
obj#remove["x"] //Removes element at key 'x' : {a=5, b={a=3}, y=7, g="text"}
obj#toJSON[] //Get json string : "{'a':5, 'b'={'a':3}, 'y':7, 'g':'text'}"
obj = new {}#fromJSON["{'x':5}"] //Object from json : {x=5}
```
Flow control:
```
//If x print 'Hello!'
x:
  out "Hello!";
else y:
  out "x was not true but y was";
else: 
  out "x and y was false"
  
//While x print 'Hello!'
loop x:
  out "Hello!";
else: // If the while condition (x == true) is never meet.
  out "While never ran"; 

//Foreach v in g:
a = new [1,2,3,4];
loop let v in a:
  out v; // Prints all values
loop let k of a:
  out k; // Prints all keys
loop let k in a where fn[elem]{elem > 2}:
  out v; // Prints all values greater than 2

o = new {a=1, b=2, c=3};
loop let v in o:
  out v; // Prints all values
loop let k of o:
  out v; // Prints all keys


  
j = 5;
out prev; //Prints result of previous statement (5)

//Prints "Bye":
if -3:
  out "Hi";
else:
  out "Bye";
  
//Loops can also use keywords:
continue; //Go to loop start.
break; //Stop loop.
```
Predefined:
```
//constants:
true, false,
prev, else,
PI, INF, INFINITY, NEGINF, NEGATIVE_INFINITY
NUMBER, STRING, ARRAY, OBJECT, FUNCTION, CLASS,
UNDEFINED, UNDEF, undefined, null //All same.

//functions: ([n]umber, [b]oolean)
print[string], println[string], read[type]
min[...], max[...], clamp[n, lower, upper]
random[lower, upper], 
round[n], sqrt[n], abs[n], pow[n, x], floor[n], ceil[n],
sin[n], cos[n], tan[n], asin[n], acos[n], atan[n],
valueOf[string], //Get value of variable with name.
parametersOf[func], //Get parameter names of function.
stop[], sleep[millis],
if[b], condition[b, t, f]

//chain functions: ([v]alue, [i]ndex, [a]rray, [s]tring, [err]or)
String#:
collapse[],
Array#:
map[func(v, i, a)], filter[func(v, i, a)],
set[i, v], set[[...], v], add[v], push[v], 
pop[], dequeue[], remove[v], removeAt[i], keys[],
sort[], find[[...]],
Object#:
keys[], values[],
Obj#/Arr#:
fromJSON[s], toJSON[],
Func#:
call[args, this], callAsync[args, this, callback(result, err)]
timeout[milis, args], interval[milis, args], cancelExecution[id], cancelAllExecution[],
httpGetAsync[url, type], httpPostAsync[url, type, data]
```
Import/Export & Extensions:
```
export var1; //Marks variable accessable for imports.
export var1, var2; //Marks multiple variables accessable for imports.
//Executes file in a separate VM and imports variables marked as export from it's Scope to this Scope:
import var1, func1, func2 from "path/file.qs"; 
import * from "path/file.qs"; //Imports all exports from file.
import ALL from "path/file.qs"; //Imports all exports from file.
import Math; //Imports Math from internal extension.

//Imports 'externalVar1' from a extension class in a external jar file:
import externalVar1 from "com.my.extension.MyExtension@filepath.jar";

//Creating a qspl java extension:
public class MyExtension implements com.mrh.qspl.io.extension.Extension{
  ...
  @Override
  public void extend(com.mrh.qspl.io.extension.ExtensionScope ext) {
    ext.export("extensionNumber", new TNumber(42));
    IFunc f = (ArrayList<Value> args, VM vm, Value _this) -> {
      return new TString("Hello " + (args.size()>0?TString.from(args.get(0)).get():"World") + "!");
    };
    ext.export("extensionFunction", new TFunc(f, "name"));
  }
}
```
Miscellaneous:
```
//One Line Comment.
/* Multi
Line
Comment */
@notation text... (WIP)

delete var1; //Remove reference.
delete var1, var2; //Removes references.
out "Some message."; //Prints [OUT:line:scope]: 'Some message.' to System.out.
error "Some error message."; //Prints [ERR:line:scope]: 'Some message.' to System.err.
exit value; //Exits current scope such as a function or program file and returns value.
prev; //Returns result of previous same block statement.
else; //Returns inverse boolean result of previous same block statement.
value in array:: //Iterates through all values of array.
value of array:: //Iterates through all keys of array.
value in object:: //Iterates through all values of object.
value of object:: //Iterates through all keys of object.
break; //Cancels further execution in block.
continue; //Cancels further execution in block until next iteration.
```

# License

Available under MIT the license more info at: https://tldrlegal.com/license/mit-license

MIT License

Copyright 2020 MRH0 (aka MRH/mrhminer/hminer.lll)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
