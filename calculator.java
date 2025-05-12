import java.util.Scanner;

public class calculator {
   public static void main(String[] args) {

      final String ANSI_RESET = "\u001B[0;49m";
      final String ANSI_TEXT_RED = "\u001B[91m";
      final String ANSI_TEXT_GREEN = "\u001B[92m";
      final String ANSI_TEXT_YELLOW = "\u001B[93m";
      final String ANSI_TEXT_BLUE = "\u001B[94m";

      String inputString, tempInput;
      String operator;
      String[] splitInput = new String[2];
      String[] possibleOperators = {"*", "/", "^", "sqrt", "root", "sin", "cos", "tan", "ln", "log", "+", "-"};
      String[] singleInputOperators = {"sqrt", "sin", "cos", "tan", "ln"};
      String[] quitCommands = {"quit", "exit", "close", "q"};

      double num1, num2, result = 0;
      int errorCount = 0, ansCount = 0;

      boolean isRunning = true;
      boolean singleInputOperatorActive;

      try (Scanner input = new Scanner(System.in)) {
         System.out.printf("Enter %s'quit, exit, close, bye, q'%s to close the calculator\nEnter %s'clear'%s to clear the calculation history\nUse %s'ans'%s to use the \033[4mprevious result\033[0m in your calculation\nYou can also use constants like %s'pi, 2pi and e'%s\nCurrent operations available are: %s'+, -, *, /, ^ (exponentation), sqrt (square root), root (nth root), sin, cos, tan, ln (natural log), log (diff. base)'%s\n   %sroot equations MUST be formatted as [n] root [number]%s\n   %slog equations MUST be formatted as log_[base]([number]%s\nYou must type your equation as %s`a+b`%s or %s`a*b=`%s and \033[4mpress enter\033[0m\n", ANSI_TEXT_YELLOW, ANSI_RESET, ANSI_TEXT_YELLOW, ANSI_RESET, ANSI_TEXT_YELLOW, ANSI_RESET, ANSI_TEXT_YELLOW, ANSI_RESET, ANSI_TEXT_YELLOW, ANSI_RESET, ANSI_TEXT_RED, ANSI_RESET, ANSI_TEXT_RED, ANSI_RESET, ANSI_TEXT_YELLOW, ANSI_RESET, ANSI_TEXT_YELLOW, ANSI_RESET);

         while (isRunning) {
            operator = null;
            splitInput[0] = "";
            splitInput[1] = "";
            num1 = 0;

            singleInputOperatorActive = false;

            // Get the user's input and format it
            System.out.print(ANSI_TEXT_GREEN + " > " + ANSI_RESET);
            inputString = input.nextLine().toLowerCase().replaceAll("[ =]", "");
            tempInput = inputString;

            // Clears the input if nothing is entered
            if (inputString.isEmpty()) {
               System.out.print("\033[1A");
               continue;
            }

            // Exit the program if the user types a quit command
            for (String command : quitCommands) {
               if (inputString.equals(command)) {
                  System.out.println("Thank you for using my calculator!");
                  return;
               }
            }

            // Clears the calculation history
            if (inputString.equals("clear")) {
               // Clears the error messages
               for (int i = 0; i < errorCount; i++) {
                  System.out.print("\033[1A\033[2K\033[1A\033[2K");
               }
               // Clears the calculation history
               for (int i = 0; i < ansCount; i++) {
                  System.out.print("\033[1A\033[2K");
               }
               System.out.print("\033[1A\033[2K");
               errorCount = 0;
               ansCount = 0;
               continue;
            }

            // Check if the first number is negative
            if (inputString.charAt(0) == '-') {
               // Create a new string without the negative sign
               tempInput = inputString.replaceFirst("-", "");
            }

            // Split the input string into numbers and operator
            try {
               // Checks if the input string is a log calculation
               if (inputString.startsWith("log")) {
                  operator = "log";
                  splitInput[0] = inputString.substring(4, inputString.indexOf("(")).replace("(", "");
                  splitInput[1] = inputString.substring(inputString.indexOf("(") + 1, inputString.length());
               } else {
                  // Check if the input string starts with a 'single input operator'
                  for (String op : singleInputOperators) {
                     if (inputString.startsWith(op)) {
                        singleInputOperatorActive = true;
                        break;
                     }
                  }

                  // Check if the input string contains a valid operator and splits the input
                  for (String op : possibleOperators) {
                     int opIndex = tempInput.indexOf(op);

                     if (tempInput.contains(op)) {
                        if (inputString.charAt(0) == '-') {
                           splitInput[0] = inputString.substring(0, opIndex + 1);
                           splitInput[1] = inputString.substring(opIndex + op.length() + 1);
                        } else {
                           splitInput[0] = inputString.substring(0, opIndex);
                           splitInput[1] = inputString.substring(opIndex + op.length());
                        }
                        operator = op;
                        break;
                     }
                  }
               }
            } catch (Exception e) {
               if (singleInputOperatorActive) {
                  break;
               }
               if (splitInput[0].isEmpty()) {
                  System.out.println(ANSI_TEXT_RED + "Invalid input, please enter the first number" + ANSI_RESET);
               } else if (splitInput[1].isEmpty()) {
                  System.out.println(ANSI_TEXT_RED + "Invalid input, please enter the second number" + ANSI_RESET);
               } else {
                  System.out.println(ANSI_TEXT_RED + "Invalid input, please enter both numbers" + ANSI_RESET);
               }
               errorCount++;
               continue;
            }

            if (operator == null) {
               System.out.println(ANSI_TEXT_RED + "Invalid input, please try again" + ANSI_RESET);
               errorCount++;
               continue;
            }

            try {
               // Parse the strings into doubles or as constants for calculation
               if (!singleInputOperatorActive) {
                  switch (splitInput[0]) {
                     case "ans" -> num1 = result;
                     case "-ans" -> num1 = -result;
                     case "pi" -> num1 = Math.PI;
                     case "-pi" -> num1 = -Math.PI;
                     case "2pi" -> num1 = Math.PI * 2;
                     case "-2pi" -> num1 = -Math.PI * 2;
                     case "e" -> num1 = Math.E;
                     case "-e" -> num1 = -Math.E;
                     default -> num1 = Double.parseDouble(splitInput[0]);
                  }
               }

               switch (splitInput[1]) {
                  case "ans" -> num2 = result;
                  case "-ans" -> num2 = -result;
                  case "pi" -> num2 = Math.PI;
                  case "-pi" -> num2 = - Math.PI;
                  case "2pi" -> num2 = Math.PI * 2;
                  case "-2pi" -> num2 = - Math.PI * 2;
                  case "e" -> num2 = Math.E;
                  case "-e" -> num2 = - Math.E;
                  default -> num2 = Double.parseDouble(splitInput[1]);
               }

               if ((operator.equals("ln") || operator.equals("log") || operator.equals("sqrt")) && num2 < 0) {
                  System.out.println(ANSI_TEXT_RED + "Cannot take the " + operator + " of a negative number, please try again" + ANSI_RESET);
                  errorCount++;
                  continue;
               }
            } catch (NumberFormatException e) {
               if (singleInputOperatorActive) {
                  if (splitInput[1].isEmpty()) {
                     System.out.println(ANSI_TEXT_RED + "Error while parsing input, please enter a number" + ANSI_RESET);
                  } else {
                     break;
                  }
               } else {
                  if (splitInput[0].isEmpty()) {
                     System.out.println(ANSI_TEXT_RED + "Error while parsing input, please enter the first number" + ANSI_RESET);
                  } else if (splitInput[1].isEmpty()) {
                     System.out.println(ANSI_TEXT_RED + "Error while parsing input, please enter the second number" + ANSI_RESET);
                  } else {
                     System.out.println(ANSI_TEXT_RED + "Error while parsing input, please try again" + ANSI_RESET);
                  }
               }
               errorCount++;
               continue;
            }

            // Perform the calculation
            switch (operator) {
               case "+" -> result = num1 + num2;
               case "-" -> result = num1 - num2;
               case "*" -> result = num1 * num2;
               case "/" -> result = num1 / num2;
               case "^" -> result = Math.pow(num1, num2);
               case "sqrt" -> result = Math.sqrt(num2);
               case "root" -> result = Math.pow(num2, 1.0 / num1);
               case "sin" -> result = Math.sin(num2);
               case "cos" -> result = Math.cos(num2);
               case "tan" -> result = Math.tan(num2);
               case "ln" -> result = Math.log(num2);
               case "log" -> result = Math.log(num2) / Math.log(num1);
               default -> {
                  System.out.println(ANSI_TEXT_RED + "Invalid operator, please try again" + ANSI_RESET);
                  errorCount++;
                  continue;
               }
            }

            // Print the result
            if (operator.equals("log")) {
               // Print the result of a log calculation
               System.out.print("\033[1A\033[1K" + ANSI_TEXT_GREEN + " > " + ANSI_RESET + operator + "_(" +Math.round(num1 * 1000.0) / 1000.0 + ") (" + Math.round(num2 * 1000.0) / 1000.0 + ")" + ANSI_TEXT_BLUE + " = " + Math.round(result * 1000.0) / 1000.0 + ANSI_RESET + "\n");
            } else if (singleInputOperatorActive) {
               // Print the result of a 'single input operator' calculation
               System.out.print("\033[1A\033[1K" + ANSI_TEXT_GREEN + " > " + ANSI_RESET + operator + " " + Math.round(num2 * 1000.0) / 1000.0 + ANSI_TEXT_BLUE + " = " + Math.round(result * 1000.0) / 1000.0 + ANSI_RESET + "\n");
            } else {
               // Print the result of a normal 'two input operator' calculation
               System.out.print("\033[1A\033[1K" + ANSI_TEXT_GREEN + " > " + ANSI_RESET + Math.round(num1 * 1000.0) / 1000.0 + " " + operator + " " + Math.round(num2 * 1000.0) / 1000.0 + ANSI_TEXT_BLUE + " = " + Math.round(result * 1000.0) / 1000.0 + ANSI_RESET + "\n");
            }

            ansCount++;
         }
      }
   }
}

/*
Enter %s'quit, exit, close, bye, q'%s to close the calculator\n
Enter %s'clear'%s to clear the calculation history\n
Use %s'ans'%s to use the \033[4mprevious result\033[0m in your calculation\n
You can also use constants like %s'pi, 2pi and e'%s\n
Current operations available are: %s'+, -, *, /, ^ (exponentation),\n
sqrt (square root), root (nth root), sin, cos, tan, ln (natural log), log (diff. base)'%s\n
\t%sroot equations MUST be formatted as [n] root [number]%s\n
\t%slog equations MUST be formatted as log_[base]([number]%s\n
You must type your equation as %s`a+b`%s or %s`a*b=`%s and \033[4mpress enter\033[0m\n

ANSI_TEXT_YELLOW, ANSI_RESET, ANSI_TEXT_YELLOW, ANSI_RESET, ANSI_TEXT_YELLOW, ANSI_RESET, ANSI_TEXT_YELLOW, ANSI_RESET, ANSI_TEXT_YELLOW, ANSI_RESET, ANSI_TEXT_RED, ANSI_RESET, ANSI_TEXT_RED, ANSI_RESET, ANSI_TEXT_YELLOW, ANSI_RESET, ANSI_TEXT_YELLOW, ANSI_RESET
*/