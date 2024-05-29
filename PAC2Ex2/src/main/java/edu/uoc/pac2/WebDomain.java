package edu.uoc.pac2;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

/**
 * Represents a web domain, including details such as domain name, owner information,
 * registration and expiration dates, concurrent user limits, privacy settings, and auto-renewal status.
 * It also provides functionality to validate domain and email formats, check domain expiration status,
 * calculate concurrent user status, and estimate future renewal costs with possible discounts.
 */
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

    /**
     * WebDomain parameterized constructor
     *
     * @param domain the domain name
     * @param ownerName the name of the domain owner
     * @param ownerEmail the email address of the domain owner
     * @param hostingProvider the provider hosting the domain
     * @param registrationDate the date the domain was registered
     * @param expirationDate the date the domain expires
     * @param maxConcurrentUsers the maximum number of concurrent users allowed
     * @param whoisPrivacy whether WHOIS privacy is enabled
     * @param autoRenew whether the domain is set to auto-renew
     */

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

    /**
     * Returns the domain name.
     *
     * @return the domain name as a String
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the domain name after validating its format. Throws an exception if the format is invalid.
     *
     * @param domain the domain name to be set
     * @throws Exception if the domain format is invalid
     */
    public void setDomain(String domain) throws Exception {
        if (!isValidDomain(domain)) {
           throw new Exception ("[ERROR] Invalid domain format.");
        }
        domain = domain.toLowerCase();
        this.domain = domain;
    }

    /**
     * Validates the format of the domain name.
     * A valid domain name must consist of a name and a valid extension, separated by a dot. The name can only contain
     * lowercase letters, digits, and hyphens.
     *
     * @param domain the domain name to validate
     * @return true if the domain name is valid, false otherwise
     */
    private boolean isValidDomain(String domain) {
        if (domain == null) {
            return false;
        }
        domain = domain.toLowerCase();

        String[] parts = domain.split("\\.");
        if (parts.length != 2) {
            return false;
        }

        if (!parts[0].matches("^[a-z0-9-]+$")) {
            return false;
        }

        String domainExtension = parts[1];
        for (String ext : validExtensions) {
            if (ext.equals(domainExtension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the owner's name.
     *
     * @return the name of the domain owner as a String
     */
    public String getOwnerName() {
        return ownerName;
    }
    /**
     * Sets the owner's name after validating that it is not null or empty.
     * Leading and trailing whitespace is trimmed.
     *
     * @param ownerName the name of the domain owner to be set
     * @throws Exception if the owner name is null or empty
     */
    public void setOwnerName(String ownerName) throws Exception {
        if (ownerName != null && !ownerName.trim().isEmpty()) {
            this.ownerName = ownerName.trim();
        } else {
            throw new Exception("[ERROR] Owner name cannot be empty.");
        }
        }

    /**
     * Returns the owner's email address.
     *
     * @return the email address of the domain owner as a String
     */
    public String getOwnerEmail() {
        return ownerEmail;
    }

    /**
     * Sets the owner's email address after validating its format.
     * The email must follow a standard email format.
     *
     * @param ownerEmail the email address of the domain owner to be set
     * @throws Exception if the email format is invalid
     */
    public void setOwnerEmail(String ownerEmail) throws Exception {

        String emailRegex = "^[A-Za-z0-9._-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (ownerEmail != null && ownerEmail.matches(emailRegex)) {
            this.ownerEmail = ownerEmail;
        } else {
            throw new Exception("[ERROR] Invalid owner email format.");
        }
    }

    /**
     * Returns the name of the hosting provider.
     *
     * @return the hosting provider as a String
     */
    public String getHostingProvider() {
        return hostingProvider;
    }

    /**
     * Sets the hosting provider's name after validating that it is not null or empty.
     * Leading and trailing whitespace is trimmed.
     *
     * @param hostingProvider the name of the hosting provider to be set
     * @throws Exception if the hosting provider name is null or empty
     */
    public void setHostingProvider(String hostingProvider) throws Exception {

        if (hostingProvider == null || hostingProvider.trim().isEmpty()) {
            throw new Exception("[ERROR] Hosting provider cannot be empty.");
        } else {
            this.hostingProvider = hostingProvider.trim();
        }
    }

    /**
     * Returns the registration date of the domain.
     *
     * @return the registration date as a LocalDate
     */
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Sets the registration date after validating that it is not in the future.
     *
     * @param registrationDate the registration date to be set
     * @throws Exception if the registration date is null or in the future
     */
    public void setRegistrationDate(LocalDate registrationDate) throws Exception {
        // Método que comprueba si la fecha de registro es valida (si fue creada antes de la fecha actual)

        // Establecemos la fecha actual
        LocalDate currentDate = LocalDate.now();
        // Comprobamos si registrationDate es null o posterior a la fecha actual
        if (registrationDate == null || registrationDate.isAfter(currentDate)) {
            throw new Exception("[ERROR] The registration date must be prior or equal to the current date.");
        } else {
            // Solo actualizamos la fecha de registro si pasa las validaciones
            this.registrationDate = registrationDate;
        }
    }

    /**
     * Returns the expiration date of the domain.
     *
     * @return the expiration date as a LocalDate
     */
    public LocalDate getExpirationDate() {
        return expirationDate;
    }


    /**
     * Sets the expiration date after validating that it is after the registration date.
     *
     * @param expirationDate the expiration date to be set
     * @throws Exception if the expiration date is null or before the registration date
     */
    public void setExpirationDate(LocalDate expirationDate) throws Exception{
        // Método que verifica si la fecha de caducidad es valida ( la fecha debe ser posterior a la fecha de registro)

        // Comprobamos si expirationDate no es null y es posterior a registrationDate.
        if (expirationDate != null && expirationDate.isAfter(registrationDate)) {
            this.expirationDate = expirationDate;
        } else {
            throw new Exception("[ERROR] The expiration date must be later than the registration date.");
        }
    }

    /**
     * Returns the maximum number of concurrent users allowed.
     *
     * @return the maximum number of concurrent users as an int
     */
    public int getMaxConcurrentUsers() {
        return maxConcurrentUsers;
    }

    /**
     * Sets the maximum number of concurrent users after validating the number is positive.
     *
     * @param maxConcurrentUsers the maximum number of concurrent users to be set
     * @throws Exception if the maximum number is not greater than 0
     */
    public void setMaxConcurrentUsers(int maxConcurrentUsers) throws Exception {
        // Método que comprueba si el número máximo de usuarios es válido (mayor que 0)
        if (maxConcurrentUsers > 0) {
            this.maxConcurrentUsers = maxConcurrentUsers;
        } else {
            throw new Exception("[ERROR] Maximum concurrent users must be greater than 0.");
        }
    }

    /**
     * Returns the auto-renewal status of the domain.
     *
     * @return true if auto-renewal is enabled, false otherwise
     */
    public boolean isAutoRenew() {
        return autoRenew;
    }

    /**
     * Sets the auto-renewal status of the domain.
     *
     * @param autoRenew the auto-renewal status to be set
     * @return the updated auto-renewal status
     */
    public boolean setAutoRenew(boolean autoRenew) {
        this.autoRenew = autoRenew;
        return autoRenew;
    }

    /**
     * Checks whether the domain has expired based on the current date.
     *
     * @return true if the domain has expired, false otherwise
     */
    public boolean isExpired() {
        // Método que comprueba si ha expiriado el dominio

        LocalDate currentDate = LocalDate.now();
        return currentDate.isAfter(expirationDate);
    }

    /**
     * Checks whether the domain is about to expire within the next month.
     *
     * @return true if the domain is about to expire, false otherwise
     */
    public boolean isAboutToExpire() {
        // Método que comprueba si queda poco para que caduque (menos de un mes)

        LocalDate currentDate = LocalDate.now();
        // Establecemos la diferencia de dias entre expirationDate y la fecha actual
        long diff = Math.abs(ChronoUnit.DAYS.between(currentDate, expirationDate));
        return diff < 30;
    }

    /**
     * Provides the status of domain usage based on the current number of concurrent users.
     * The status is categorized as "Low", "Average", or "High" based on usage thresholds.
     *
     * @param currentConcurrentUser the current number of concurrent users
     * @return a String indicating the usage status
     * @throws Exception if the number of concurrent users exceeds the maximum allowed
     */
    public String domainConcurrenceStatus(int currentConcurrentUser) throws Exception {
        if (currentConcurrentUser < 0 || currentConcurrentUser > maxConcurrentUsers) {
            throw new Exception("[ERROR] The number of concurrent users exceeds the maximum allowed.");
        }
        double percentage = (double) currentConcurrentUser / maxConcurrentUsers * 100;
        if (percentage < 20) {
            return "Low";
        } else if (percentage < 80) {
            return "Average";
        } else {
            return "High";
        }
    }

    /**
     * Estimates the renewal cost of the domain by a target date, considering possible discounts for longevity.
     *
     * @param targetDate the date by which the renewal cost is calculated
     * @return the estimated renewal cost as a double
     * @throws Exception if the target date is before the current date or if auto-renew is disabled
     */
    public double predictedRenewalCostByDate(LocalDate targetDate) throws Exception{
        LocalDate currentDate = LocalDate.now();
        if (targetDate.isBefore(currentDate)) {
            throw new Exception( "[ERROR] The date must be later than the current date.");
        }
        if (!autoRenew) {
            return 0.0;
        }
        double totalCost = 0.0;
        LocalDate nextRenewalDate = expirationDate;
        while (nextRenewalDate.isBefore(targetDate)) {
            long yearsOfAntiquity = Period.between(registrationDate, nextRenewalDate.plusDays(1)).getYears();
            double discount = 1.0;
            if (yearsOfAntiquity >= 2 && yearsOfAntiquity <= 3) {
                discount = 0.95;
            } else if (yearsOfAntiquity >= 4 && yearsOfAntiquity <= 5) {
                discount = 0.90;
            } else if (yearsOfAntiquity >= 6) {
                discount = 0.85;
            }
            totalCost += renewalCost * discount;
            nextRenewalDate = nextRenewalDate.plusYears(1);
        }
        return Math.round(totalCost * 100.0) / 100.0;
    }

    /**
     * Returns the WHOIS privacy status of the domain.
     *
     * @return true if WHOIS privacy is enabled, false otherwise
     */
    public boolean isWhoisPrivacy() {
        return whoisPrivacy;
    }

    /**
     * Sets the WHOIS privacy status of the domain.
     *
     * @param whoisPrivacy the WHOIS privacy status to be set
     */
    public void setWhoisPrivacy(boolean whoisPrivacy) {
        this.whoisPrivacy = whoisPrivacy;
    }

}