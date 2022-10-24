//import all of the input and output java classes
import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception{
        File file = new File("C:\\Users\\benny\\IdeaProjects\\Space Cadets 2 Interpreter\\src\\bareBonesScript.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        ArrayList<String> commands = new ArrayList<String>();
        ArrayList<String> commandVariable = new ArrayList<String>();
        ArrayList<String> variables = new ArrayList<String>();
        ArrayList<Integer> values = new ArrayList<Integer>();
        ArrayList<Integer> whileCommandLine = new ArrayList<Integer>();
        String line;
        String command;
        String variableName;
        boolean commandGot;
        boolean variableGot;
        boolean byPassedIndent;

        while ((line = bufferedReader.readLine()) != null) {

            command = "";
            variableName = "";
            commandGot = false;
            variableGot = false;
            byPassedIndent = false;

            for (int index = 0; index < line.length(); index++) {
                if (line.charAt(index) == ' ' && !byPassedIndent) {

                } else if (!byPassedIndent){
                    byPassedIndent = true;
                }
                if (byPassedIndent) {
                    byPassedIndent = true;
                    if (line.charAt(index) == ' ' && !commandGot) {
                        commandGot = true;
                    } else if (line.charAt(index) == ';' && commandGot) {
                        variableGot = true;
                    } else if (line.charAt(index) == ' ' && commandGot && !variableGot) {
                        //then it is a while-not statement
                        //stop the for loop adding to the current variable/parameter
                        variableGot = true;
                    } else if (line.charAt(index) == ';' && !commandGot) {
                        //end loop command
                        commandGot = true;
                        variableGot = true;
                        //gets the same variable/parameter as the last called while loop
                        boolean endFound;
                        boolean correctWhileFound=false;
                        int indent = 0;
                        //System.out.println("a while");
                        for (int i=commands.size()-1; i>=0;i--){
                            if (!correctWhileFound) {
                                if (commands.get(i).equals("while")) {


                                    //System.out.println("while found "+i);


                                    indent+=1;
                                    endFound = false;
                                    for (int j = i; j < commands.size(); j++) {
                                        if (commands.get(j).equals("end")) {
                                            indent-=1;
                                            if (!endFound && indent==0) {


                                                //System.out.println("end found "+j);


                                                endFound = true;
                                            }
                                        }
                                    }
                                    if (endFound == false) {
                                        correctWhileFound=true;
                                        variableName = commandVariable.get(i);
                                    }
                                }
                            }
                        }

                        //System.out.println("Whiles: "+whiles);
                        //System.out.println("Ends: "+ends);

                        //int whileIndex = commands.lastIndexOf("while");
                        //variableName = commandVariable.get(whileIndex);
                    } else {
                        //reading commands and variables from the line
                        if (!commandGot) {
                            command += line.charAt(index);
                        } else if (!variableGot) {
                            variableName += line.charAt(index);
                        }
                    }
                }
            }
            commands.add(command);
            commandVariable.add(variableName);
        }

        int commandLine = 0;
        while (commandLine < commands.size()){
            if (commands.get(commandLine).equals("while")){
                whileCommandLine.add(commandLine);
            } else if (commands.get(commandLine).equals("end")) {
                String parameter = commandVariable.get(commandLine);
                int parameterValue = values.get(variables.indexOf(parameter)).intValue();
                if (parameterValue > 0){
                    commandLine = whileCommandLine.get(whileCommandLine.size()-1);

                    //System.out.println(commands.toString());
                    //System.out.println("Outputs");
                    //System.out.println("Command line: ...");
                    //System.out.println("Command: loop");
                    //System.out.println("Variables: ...");
                    //System.out.println("Values:    ...");
                    //System.out.println("Whiles:    ");
                    //System.out.println(whileCommandLine.toString());
                    //System.out.println();
                } else if (parameterValue == 0) {
                    whileCommandLine.remove(whileCommandLine.size()-1);

                    //System.out.println(commands.toString());
                    //System.out.println("Outputs");
                    //System.out.println("Command line: "+commandLine);
                    //System.out.println("Command: end");
                    //System.out.println("Variables: ...");
                    //System.out.println("Values:    ...");
                    //System.out.println("Whiles:    ");
                    //System.out.println(whileCommandLine.toString());
                    //System.out.println();
                }


            } else if (commands.get(commandLine).equals("clear")) {
                //create a new variable with value 0, or reset value of a var to 0
                if (variables.contains(commandVariable.get(commandLine))){
                    int varIndex = variables.indexOf(commandVariable.get(commandLine));
                    values.set(varIndex, 0);
                } else {
                    variables.add(commandVariable.get(commandLine));
                    values.add(0);
                }
            } else if (commands.get(commandLine).equals("incr")) {
                int index = 0;
                for (int i = 0; i < variables.size(); i++){
                    if (variables.get(i).equals(commandVariable.get(commandLine))){
                        index = i;
                    }
                }
                values.set(index,values.get(index).intValue() + 1);
            } else if (commands.get(commandLine).equals("decr")) {
                int index = 0;
                for (int i = 0; i < variables.size(); i++){
                    if (variables.get(i).equals(commandVariable.get(commandLine))){
                        index = i;
                    }
                }
                values.set(index,values.get(index).intValue() - 1);
            }

            commandLine+=1;

            //System.out.println(commands.toString());
            //System.out.println(commandVariable.toString());
            //System.out.println("Outputs");
            //System.out.println("Command line: " + (commandLine-1));
            //System.out.println("Command: " + commands.get((commandLine-1)));
            //System.out.println("Var: " + commandVariable.get((commandLine-1)));
            //System.out.print("Variables: ");
            //System.out.println(variables.toString());
            //System.out.print("Values:    ");
            //System.out.println(values.toString());
            //System.out.print("Whiles:    ");
            //System.out.println(whileCommandLine.toString());
            //System.out.println();
        }
        System.out.println("Commands");
        System.out.print("Commands: ");
        System.out.println(commands.toString());
        System.out.print("Respective variable: ");
        System.out.println(commandVariable.toString());
        System.out.println();
        System.out.println("Outputs");
        System.out.print("Variables: ");
        System.out.println(variables.toString());
        System.out.print("Values:    ");
        System.out.println(values.toString());
        System.out.println();
    }
}