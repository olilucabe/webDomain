package edu.uoc.pac2;

public class PasswordImprover {

    private static String specialCharacters = "!@#$%^&*()_+-=[]{}|;:',.<>/?";

    public static String replaceVowels(String password) {
        /* DEFINICIÓN replaceVowels:
        Remplazamos todas las vocales tanto mayúscuals como minúsculas */
        password = password.replaceAll("([aA])", "4")
                .replaceAll("([eE])", "3")
                .replaceAll("([iI])", "1")
                .replaceAll("([oO])", "0")
                .replaceAll("([uU])", "2");
        return password; //Devolvemos la contraseña modificada
    }

    public static boolean isLetter(char c) {
        /* DEFINICIÓN isLetter:
        Devolvemos ture/false si la letra c es un char (una letra) */
        return Character.isLetter(c);
    }

    public static String replaceWithSpecialCharacters(String password) {
        /* DEFINICIÓN replaceWithSpecialCharacters:
        Cambiamos los valores de password de manera, que no encontremos letras secuenciadas */

        // boolean que nos determina si el anterior carácter es una letra
        boolean lastchar = false;
        // Utilizaremos StringBuilder para tratar la string sin necesidad de crear nuevas instancias
        StringBuilder newpassword = new StringBuilder();
        // Convertimos password en una array de carácteres para poder iterarla
        char[] letters = password.toCharArray();
        // Utilizamos esta flag para determinar que simbolo de specialCharacters utilizamos
        int i = 0;

        for (char letter : letters) {
            // Establecemos actualchar con isLetter para saber si nos encontramos con dos letras seguidas
            boolean actualchar = isLetter(letter);
            if (lastchar && actualchar) { // Dos letras seguidas
                // Añadimos el carácter i a la nueva contraseña
                newpassword.append(specialCharacters.charAt(i));
                i += 1; // Pasamos al siguiente carácter especial para el siguiente cambio
            } else {
                // Añadimos el carácter, ya que, no nos encontramos con dos letras seguidas
                newpassword.append(letter);
            }
            // Actualizamos lastchar para la próxima iteración
            lastchar = actualchar;
            // Si hemos utilizado el último carácter especial reseteamos i a 0
            if (i == specialCharacters.length()) {
                i = 0;
            }
        }
        return newpassword.toString(); // Convertimos a string la contraseña
    }

    public static String improvePassword(String password) {
        /* DEFINICIÓN improvePassword:
        Suistutuimos las vocales por números, sustituimos los cáracteres para no encontrar
        dos letras seguidas y eliminamos todos los espacios */

        // Eliminamos los espacios en blanco
        password = password.replaceAll(" ", "");
        // Utilizamos la función replaceVowels y sustituimos las vocales por números
        password = replaceVowels(password);
        // Utilizamos la función replaceWithSpecialCharacters y evitamos encontrarnos dos letras seguidas
        password = replaceWithSpecialCharacters(password);
        return password;
    }

}
