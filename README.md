# Chat Application
This is a java application that allows multiple users to connect to the server and chat together.

### Tools
- Java JDK 15
- JavaFX 15

### Set up
After downloading the project folder, some setup is required for it to work.


First, you have to go into file -> project structure and do three things:
- Go to modules -> Client -> dependencies and add a dependency on the Protocols modules
- Go to modules -> Client -> sources and mark the GUI package as a source folder
- Go to modules -> Server -> dependencies and add a dependency on the Protocols modules

Also, the javaFX 15 library should be added. 