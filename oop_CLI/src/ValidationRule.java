/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/

// Interface for validation rules
// This interface defines a contract for validation logic.
// Implementing classes or lambda expressions will provide the specific validation logic.
public interface ValidationRule {
    // Method to check if a given value satisfies the validation rule
    boolean isValid(int value);
}
