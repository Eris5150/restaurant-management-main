package ca.sheridancollege.project.beans;

import lombok.Getter;

/**
 * Enumeration defining the available measurement units for inventory items.
 * Each constant represents a standardized unit of measure and includes
 * a descriptive label for display purposes.
 *
 * 
 * Lombok’s {@code @Getter} annotation provides read-only access to
 * the {@code description} field, promoting immutability and clean code.
 * 
 */
@Getter
public enum Units {

    /** Unit representing weight measured in kilograms. */
    KG("Kilograms"),

    /** Unit representing volume measured in liters. */
    LITERS("Liters"),

    /** Unit representing individual countable pieces. */
    PIECES("Pieces"),

    /** Unit representing weight measured in ounces. */
    OZ("Ounces"),

    /** Unit representing weight measured in grams. */
    G("Grams"),

    /** Unit representing rolled materials (e.g., fabric, tape). */
    ROLLS("Rolls");

    /** Descriptive name of the unit for display in UI components or reports. */
    private final String description;

    /**
     * Constructs a unit constant with the given human-readable description.
     *
     * @param description the descriptive name associated with the unit
     */
    Units(String description) {
        this.description = description;
    }
}
