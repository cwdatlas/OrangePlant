---
Orange Plant
---
Orange plant is a test application to learn more about multithreaded design.
The objective was to create a data and task parallel program with multiple Plants
and workers per plant completing different jobs. The Orange class was provided by 
my professor Nate Williams. 

---
How to install
---
You will need Java and ant to run this program

1. Go to a new directory and type 'git clone https://github.com/cwdatlas/OrangePlant'
   2. I recommend to clone this project in a appropriate "projects" folder.
3. Navigate into the top level of the program's directory, you should see a file named 'build.xml'
4. Next, type 'ant run' and you are off! you launched the program!
   5. Note: If you are not in the correct directory, ant will say it cant find a build.xml file.

---
How to use
---
Usage is as simple as launching the application: 'ant run'.
If you are wanting to change parameters, you can edit the Boss class then type 'ant run'.
'ant run' will work in both situations because it includes compiling the code.