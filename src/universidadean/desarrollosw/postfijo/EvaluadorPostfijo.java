/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad Ean (Bogotá - Colombia)
 * Departamento de Tecnologías de la Información y Comunicaciones
 * Licenciado bajo el esquema Academic Free License version 2.1
 * <p>
 * Proyecto Evaluador de Expresiones Postfijas
 * Fecha: Febrero 2021
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package universidadean.desarrollosw.postfijo;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.*;
import java.util.function.BiFunction;

/**
 * Esta clase representa una clase que evalúa expresiones en notación polaca o
 * postfija. Por ejemplo: 4 5 +
 */
public class EvaluadorPostfijo {

    public static Map<String, BiFunction<Integer, Integer, Integer>> OPERATORS;

    static {
        OPERATORS = new HashMap<String, BiFunction<Integer, Integer, Integer>>() {{
            put("^", (a, b) -> (int) Math.pow(a, b));
            put("*", (a, b) -> a * b);
            put("/", (a, b) -> a / b);
            put("+", (a, b) -> a + b);
            put("-", (a, b) -> a - b);
            put("%", (a, b) -> a % b);
        }};
    }


    /**
     * Realiza la evaluación de la expresión postfijo utilizando una pila
     *
     * @param expresion una lista de elementos con números u operadores
     * @return el resultado de la evaluación de la expresión.
     */
    static int evaluarPostFija(List<String> expresion) throws Exception {
        Stack<Integer> pila = new Stack<>();

        for (int i = 0; i < expresion.size(); ++i) {
            String token = expresion.get(i);
            if (isNumeric(token)) {
                pila.push(Integer.parseInt(expresion.remove(0)));
                --i;
            } else if (OPERATORS.containsKey(token)) {
                if (pila.size() < 2) {
                    throw new Exception("ARGUMENT ERROR");
                }
                Integer[] args = {pila.pop(), pila.pop()};

                int result = OPERATORS.get(expresion.remove(0)).apply(args[1], args[0]);
                --i;
                pila.push(result);
            }
        }

        return pila.pop();
    }


    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Programa principal
     */
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.print("> ");
        String linea = teclado.nextLine();

        try {
            List<String> expresion = Token.dividir(linea);
            System.out.println(evaluarPostFija(expresion));
        } catch (Exception e) {
            System.err.printf("Error grave en la expresión: %s", e.getMessage());
        }

    }
}
