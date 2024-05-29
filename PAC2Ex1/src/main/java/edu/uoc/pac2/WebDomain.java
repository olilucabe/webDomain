package edu.uoc.pac2;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;


public class WebDomain {
    private String domain;
    private String ownerName;
    private String ownerEmail;
    private String hostingProvider;
    private LocalDate registrationDate;
    private LocalDate expirationDate;
    private int maxConcurrentUsers;
    private boolean whoisPrivacy;
    private boolean autoRenew;
    private static final String[] validExtensions = new String[]{"com", "org", "net", "int", "edu", "gov", "mil"};
    private static final double renewalCost = 9.99;

    public WebDomain(String domain, String ownerName, String ownerEmail, String hostingProvider,
                     LocalDate registrationDate, LocalDate expirationDate, int maxConcurrentUsers,
                     boolean whoisPrivacy, boolean autoRenew) {
        this.domain = domain;
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.hostingProvider = hostingProvider;
        this.registrationDate = registrationDate;
        this.expirationDate = expirationDate;
        this.maxConcurrentUsers = maxConcurrentUsers;
        this.autoRenew = autoRenew;
        this.whoisPrivacy = whoisPrivacy;
    }

    public String getDomain() {
        return domain;
    }


    public void setDomain(String domain) {
        // Método para validar el formato del dominio utilizando isValidDomain

        if (!isValidDomain(domain)) {
            System.out.println("[ERROR] Invalid domain format.");
            return;
        }
        domain = domain.toLowerCase();
        this.domain = domain;
    }

    private boolean isValidDomain(String domain) {
        // Comprobamos si es null
        if (domain == null) {
            return false;
        }
        // Nos aseguramos de que domain está en minúsculas
        domain = domain.toLowerCase();

        // Comprobamos que el dominio solo tenga una parte y una extensión, separados por un punto
        String[] parts = domain.split("\\.");
        if (parts.length != 2) {
            return false;
        }

        // Comprobamos que el nombre del dominio solo contiene letras, dígitos y guiones
        if (!parts[0].matches("^[a-z0-9-]+$")) {
            return false;
        }

        // Comparamos con validExtensions para comprobar si es una extensión valida
        String domainExtension = parts[1];
        for (String ext : validExtensions) {
            if (ext.equals(domainExtension)) {
                return true;
            }
        }
        return false;
    }


    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        // Método que comprueba si el nombre del propietario es valido

        if (ownerName != null && !ownerName.trim().isEmpty()) {
            // Eliminamos (si exsisten) los espacios en blanco al principio y/o al final
            this.ownerName = ownerName.trim();
        }
        System.out.println("[ERROR] Owner name cannot be empty.");
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        // Construir una cadena con las extensiones válidas para la expresión regular
        String validExtensionsRegex = String.join("|", validExtensions); // Esto creará una cadena "com|org|net|int|edu|gov|mil"

        // Modificar la expresión regular para incluir las extensiones válidas al final
        String emailRegex = "^[A-Za-z0-9._-]+@[A-Za-z0-9.-]+\\.(" + validExtensionsRegex + ")$";

        if (ownerEmail != null && ownerEmail.matches(emailRegex)) {
            this.ownerEmail = ownerEmail;
        } else {
            // Se imprime el error solo si el correo electrónico no es válido.
            System.out.println("[ERROR] Invalid owner email format.");
        }
    }
    public String getHostingProvider() {
        return hostingProvider;
    }


    public void setHostingProvider(String hostingProvider) {
        //Método que comprueba si el HostingProvider es valido

        // Comprobamos si el valor es null o está vacío después de eliminar espacios en blanco al principio y al final
        if (hostingProvider == null || hostingProvider.trim().isEmpty()) {
            System.out.println("[ERROR] Hosting provider cannot be empty.");
        } else {
            // Asignamos el valor limpio (sin espacios en blanco al principio y al final) a this.hostingProvider
            this.hostingProvider = hostingProvider.trim();
        }
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        // Método que comprueba si la fecha de registro es valida (si fue creada antes de la fecha actual)

        // Establecemos la fecha actual
        LocalDate currentDate = LocalDate.now();
        // Comprobamos si registrationDate es null o posterior a la fecha actual
        if (registrationDate == null || registrationDate.isAfter(currentDate)) {
            System.out.println("[ERROR] The registration date must be prior or equal to the current date.");
        } else {
            // Solo actualizamos la fecha de registro si pasa las validaciones
            this.registrationDate = registrationDate;
        }
    }


    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        // Método que verifica si la fecha de caducidad es valida ( la fecha debe ser posterior a la fecha de registro)

        // Comprobamos si expirationDate no es null y es posterior a registrationDate.
        if (expirationDate != null && expirationDate.isAfter(registrationDate)) {
            this.expirationDate = expirationDate;
        } else {
            System.out.println("[ERROR] The expiration date must be later than the registration date.");
        }
    }

    public int getMaxConcurrentUsers() {
        return maxConcurrentUsers;
    }

    public void setMaxConcurrentUsers(int maxConcurrentUsers) {
        // Método que comprueba si el número máximo de usuarios es valido (mayor que 0)
        if (maxConcurrentUsers > 0) {
            this.maxConcurrentUsers = maxConcurrentUsers;
        }
        System.out.println("[ERROR] Maximum concurrent users must be greater than 0.");
    }

    public boolean isAutoRenew() {
        return autoRenew;
    }

    public boolean setAutoRenew(boolean autoRenew) {
        this.autoRenew = autoRenew;
        return autoRenew;
    }

    public boolean isExpired() {
        // Método que comprueba si ha expiriado el dominio

        LocalDate currentDate = LocalDate.now();
        return currentDate.isAfter(expirationDate);
    }

    public boolean isAboutToExpire() {
        // Método que comprueba si queda poco para que caduque (menos de un mes)

        LocalDate currentDate = LocalDate.now();
        // Establecemos la diferencia de dias entre expirationDate y la fecha actual
        long diff = Math.abs(ChronoUnit.DAYS.between(currentDate, expirationDate));
        return diff < 30;
    }

    public String domainConcurrenceStatus(int currentConcurrentUser) {
        // Método que muestra la cantidad de usuarios proporcionalmente al máximo de usuarios y establecemos si nos
        // encontramos en un porcentaje low, average o high de ocupación

        // Comprobamos que el máximo de usuarios es mayor que 0
        if (currentConcurrentUser < 0 || currentConcurrentUser > maxConcurrentUsers) {
            System.out.println("[ERROR] The number of concurrent users exceeds the maximum allowed.");
        }
        // Hacemos el porcentaje de usuarios en el dominio
        double percentage = (double) currentConcurrentUser / maxConcurrentUsers * 100;

        // Segun el porcentaje establecemos low, average o high
        if (percentage < 20) {
            return "Low";
        } else if (percentage < 80) {
            return "Average";
        } else {
            return "High";
        }
    }

    public double predictedRenewalCostByDate(LocalDate targetDate) {
        // Método que calcula el precio de la renovación del dominio

        // Establecemos la fecha actual
        LocalDate currentDate = LocalDate.now();
        // Comprobamos que la fecha targetDate sea antes que currentDate
        if (targetDate.isBefore(currentDate)) {
            System.out.println("[ERROR] The date must be later than the current date.");
            return 0.0;
        }
        // Comprobamos que la renovación automática esta desactivada
        if (!autoRenew) {
            return 0.0;
        }
        // Establecemos totalCost para ir sumando los precios
        double totalCost = 0.0;
        // Establecemos nextRenewalDate que será la fecha con la que estemos trabajando según la iteración
        LocalDate nextRenewalDate = expirationDate;

        // Hacemos un bucle hasta que la fecha targetDate sea antes que currentDate
        while (nextRenewalDate.isBefore(targetDate)) {
            long yearsOfAntiquity = Period.between(registrationDate, nextRenewalDate.plusDays(1)).getYears();
            double discount = 1.0; // Sin descuentos por default
            // Establecemos los descuentos según los años
            if (yearsOfAntiquity >= 2 && yearsOfAntiquity <= 3) {
                discount = 0.95; // 5% discount
            } else if (yearsOfAntiquity >= 4 && yearsOfAntiquity <= 5) {
                discount = 0.90; // 10% discount
            } else if (yearsOfAntiquity >= 6) {
                discount = 0.85; // 15% discount
            }
            // Añadimos el calculo de la iteración a totalCost
            totalCost += renewalCost * discount;
            // Para pasar al siguente año
            nextRenewalDate = nextRenewalDate.plusYears(1);
        }
        // Redondeamos a dos decimales y obtenemos el precio total
        return Math.round(totalCost * 100.0) / 100.0;
    }

    public boolean isWhoisPrivacy() {
        return whoisPrivacy;
    }

    public void setWhoisPrivacy(boolean whoisPrivacy) {
        this.whoisPrivacy = whoisPrivacy;
    }

}