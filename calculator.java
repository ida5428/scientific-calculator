import java.util.Scanner;

public class calculator {

   public static void main(String[] args) {

      final String ANSI_RESET = "\u001B[0;49m";
      final String ANSI_TEXT_GREEN = "\u001B[92m";
      final String ANSI_TEXT_RED = "\u001B[91m";

      boolean isRunning = true;

      double num1, num2;
      double result = 0;
      double prevResult = 0;

      String inputString;
      String operator = null;

      String[] splitInput = new String[2];
      String[] possibleOperators = {"+", "*", "/", "^", "sqrt", "-"};

      try (Scanner input = new Scanner(System.in)) {
         System.out.printf("Enter %s'quit'%s to close the calculator\nUse %s'ans'%s to use the \033[4mprevious result\033[0m in your calculation\nCurrent operations available are %s'+, -, *, /, ^'%s\nYou must type your equation as %s`a+b`%s or %s`a*b=`%s and \033[4mpress enter\033[0m\n", ANSI_TEXT_GREEN, ANSI_RESET, ANSI_TEXT_GREEN, ANSI_RESET, ANSI_TEXT_GREEN, ANSI_RESET, ANSI_TEXT_GREEN, ANSI_RESET, ANSI_TEXT_GREEN, ANSI_RESET);

         while (isRunning) {
               boolean num1Sign = false;
               boolean checkingInput = true;
               boolean isCalculating = true;

               while (checkingInput) {
                  System.out.print(ANSI_TEXT_GREEN + " > " + ANSI_RESET);
                  inputString = input.nextLine().trim().replaceAll("=", "");

                  if (inputString.equalsIgnoreCase("quit")) {
                     isRunning = false;
                     checkingInput = false;
                     isCalculating = false;
                  } else {
                     try {
                        for (String item : possibleOperators) {

                           if (inputString.charAt(0) == '-') {
                              inputString = inputString.replaceFirst("-", "");
                              num1Sign = true;
                           }

                           if (inputString.startsWith("sqrt")) {
                              operator = "sqrt";
                              inputString = inputString.replace("sqrt", "0sqrt");
                              splitInput = inputString.split(operator);
                              checkingInput = false;
                              break;
                           }

                           int splitIndex = inputString.split("\\" + item).length;

                           if (splitIndex > 1 && splitIndex <= 2) {
                              operator = item;
                              splitInput = inputString.split("\\" + operator);
                              checkingInput = false;
                              break;
                           } else if (splitIndex > 2) {
                              System.out.println(ANSI_TEXT_RED + "Input error, Please try again" + ANSI_RESET);
                              break;
                           }
                        }
                     } catch (StringIndexOutOfBoundsException e) {
                           System.out.println(ANSI_TEXT_RED + "Input error, Please try again" + ANSI_RESET);
                           isCalculating = false;
                           break;
                     }
                  }
               }

               while (isCalculating) {
                  try {
                     if (splitInput[0].contains("ans")) {
                        num1 = prevResult;
                     } else {
                        num1 = Double.parseDouble(splitInput[0]);
                     }

                     if (num1Sign) {
                           num1 *= -1;
                     }

                     if (splitInput[1].contains("ans")) {
                           num2 = prevResult;
                     } else {
                           num2 = Double.parseDouble(splitInput[1]);
                     }

                  } catch (NumberFormatException e) {
                     System.out.println(ANSI_TEXT_RED + "Parsing (input) error, Please try again" + ANSI_RESET);
                     break;
                  }

                  if (operator == null) {
                     System.out.println(ANSI_TEXT_RED + "Input error, Please try again" + ANSI_RESET);
                     break;
                  }

                  if (num2 == 0 && operator.equals("/")) {
                     System.out.println(ANSI_TEXT_RED + "Cannot divide by 0" + ANSI_RESET);
                     break;
                  }

                  if (num2 < 0 && operator.equals("sqrt")) {
                     System.out.println(ANSI_TEXT_RED + "Cannot take the square root of a negative number (yet), Please try again" + ANSI_RESET);
                     break;
                  }

                  switch (operator) {
                     case "+" -> result = num1 + num2;
                     case "-" -> result = num1 - num2;
                     case "*" -> result = num1 * num2;
                     case "/" -> result = num1 / num2;
                     case "^" -> result = Math.pow(num1, num2);
                     case "sqrt" -> result = Math.sqrt(num2);
                     default -> {
                           System.out.println(ANSI_TEXT_RED + "Calculation error, Please try again" + ANSI_RESET);
                           break;
                     }
                  }

                  prevResult = result;

                  if (operator.equals("sqrt")) {
                     System.out.print("\033[F\033[2K" + ANSI_TEXT_GREEN + " > " + ANSI_RESET + operator + " " + num2 + ANSI_TEXT_GREEN + " = " + Math.round(result * 1000.0) / 1000.0 + ANSI_RESET + "\n");
                  } else {
                     System.out.print("\033[F\033[2K" + ANSI_TEXT_GREEN + " > " + ANSI_RESET + num1 + " " + operator + " " + num2 + ANSI_TEXT_GREEN + " = " + Math.round(result * 1000.0) / 1000.0 + ANSI_RESET + "\n");
                  }
                     isCalculating = false;
                  }
         }
      }
      System.out.println("Thank you for trying my calculator!");
   }
}
/*
Enter %s'quit'%s to close the calculator\n
Use %s'ans'%s to use the \033[4mprevious result\033[0m in your calculation\n
Current operations available are %s'+, -, *, /, ^'%s\n
You must type your equation as %s`a+b`%s or %s`a*b=`%s and \033[4mpress enter\033[0m\n
*/