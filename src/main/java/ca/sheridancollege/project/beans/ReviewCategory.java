package ca.sheridancollege.project.beans;

/**
 * Enumeration defining the possible categories for user or customer reviews.
 * Each constant represents a qualitative rating level that can be applied
 * to feedback or evaluations within the system.
 *
 * These values can be used for filtering, analytics, or displaying
 * overall satisfaction summaries in the user interface.
 */
public enum ReviewCategory {

    /** Indicates a poor experience or product quality. */
    BAD,

    /** Represents an average or neutral review. */
    AVERAGE,

    /** Reflects a positive experience or above-average satisfaction. */
    GOOD,

    /** Denotes outstanding quality or exceptional satisfaction. */
    EXCELLENT
}
