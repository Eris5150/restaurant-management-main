package ca.sheridancollege.project.beans;

/**
 * Enumeration representing the categories available on the menu.
 * Each constant identifies a specific type of menu item and can
 * be used to organize or filter items in the system.
 *
 * Typical usage examples include grouping menu items when
 * displaying or querying inventory data.
 */
public enum MenuCategory {

    /** Starters or light dishes served before the main course. */
    APPETIZER,

    /** Main or primary dishes in a meal. */
    MAIN_COURSE,

    /** Sweet or concluding dishes typically served at the end of a meal. */
    DESSERT,

    /** All types of beverages, including alcoholic and non-alcoholic. */
    DRINKS
}
