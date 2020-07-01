#   Santorini
![](src/main/resources/SantoriniBanner.jpg)
## Game overview:
Santorini is strategic game where 2 or 3 players try build some towers over a 5x5 board 
with the objective to reach the tower's third level before everyone else, this with the help
of a divinity.

## Authors:
Mattia Iamundo, Luca Giacometti, Merita Mullameti   (Group 51)

##  Game features:
This game is playable both with a CLI or a GUI interface. This version of the game offers
the complete rules and add some additional features.

### Additional features:
-   **Undo function**: that permit to the player to retrieve from an erroneous action
    and dodge a certain defeat.
-   **Additional Gods**: to expand the game universe and make each match different from the 
    previous one thank to the newly added gods. The new divinity are:
    -   ***Hera***
    -   ***Hestia***
    -   ***Poseidon***
    -   ***Triton***
    -   ***Zeus***

## Game Server:
In order to start a new server you must launch  <code>server.jar</code> file, that is 
collocated in: <code>deliveries/final/jar/</code>, with the following command:

    java -jar server.jar [port]

where the port number is optional, if not specified the server will start on the 
default port **20000**.

## Game Client:
In order to start a new client, first check a server is already running, then you have to
start the client application, namely <code>client.jar</code> collocated in: <code>deliveries/final/jar/</code>.

### Linux/Mac systems:
to start a new client, from a terminal, simply digit:
    
    java -jar client.jar [option][value]

### Windows systems:
If you desire to use the CLI interface it's recommended to install **Windows Terminal** from 
the Microsoft store, otherwise you can incur into some visual defects such as missed colors
visualization, unprintable characters.  
To start the client open a new command prompt from Windows terminal, if installed, and digit:

    java -jar -Dfile.encoding=cp850 -client.jar [option][value]

Instead ,if you desire to use the GUI, you can follow the same instruction for Linux/Mac.

### Client Options:
When launching a client there is some option that can be specified, 
the available options are:  
-   <code>-ip</code>: to specify the server ip, the default value is the Localhost
-   <code>-p</code>: to specify the server port, the default value is 20000
-   <code>-i</code>: to specify the user interface to use, for default is prompt if continue with
    CLI or start a GUI. The admissible value are:
    -   <code>0</code>: if you want to use a CLI
    -   <code>1</code>: if you want to use a GUI
        
        
