package ca.sheridancollege.project.beans;

import lombok.Data;

/**
 * Represents a customer or user review submitted within the system.
 * Each review contains the author’s name, a written description, and
 * a rating category that classifies overall satisfaction or quality.
 *
 * 
 * Lombok’s {@code @Data} annotation automatically provides standard
 * accessors and mutators (getters, setters, equals, hashCode, toString),
 * ensuring concise and maintainable data model code.
 * 
 */
@Data
public class Review {

    /** Name or identifier of the person who submitted the review. */
    private String author;

    /** Written feedback or comments describing the experience. */
    private String description;

    /** Rating category, defined by the {@link ReviewCategory} enum. */
    private ReviewCategory rating;
}
