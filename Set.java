import java.util.Comparator;
import java.util.HashSet;
import java.util.function.Function;
import java.util.function.Predicate;
import static java.util.Collections.emptySet;

// Zermelo–Fraenkel set theory
public record Set(HashSet<Object> elements) {

    // Axiom of Extensionality: Two sets are equal if they have the same elements
    public boolean equals(Set set) {
        return this.elements.equals(set.elements);
    }

    // Axiom of Regularity: Every non-empty set A contains an element that is disjoint from A
    public boolean isRegular() {
        for (Object element : this.elements) {
            if (element instanceof Set && !((Set) element).elements.contains(this)) {
                return true;
            }
        }
        return this.elements.isEmpty(); // An empty set satisfies regularity
    }

    // Axiom Schema of Specification: Creates a subset based on a predicate
    public Set specification(Predicate<Object> predicate) {
        HashSet<Object> specifiedSet = new HashSet<>();
        for (Object element : this.elements) {
            if (predicate.test(element)) {
                specifiedSet.add(element);
            }
        }
        return new Set(specifiedSet);
    }

    // Axiom of Pairing: For any two sets, there exists a set containing exactly those two sets
    public static Set pair(Set a, Set b) {
        HashSet<Object> pairSet = new HashSet<>();
        pairSet.add(a);
        pairSet.add(b);
        return new Set(pairSet);
    }

    // Axiom of Union: For any set, there exists a set that contains all elements of the subsets
    public Set union() {
        HashSet<Object> unionSet = new HashSet<>();
        for (Object element : this.elements) {
            if (element instanceof Set) {
                unionSet.addAll(((Set) element).elements);
            }
        }
        unionSet.addAll(this.elements); // Add the original set elements as well
        return new Set(unionSet);
    }

    // Axiom Schema of Replacement: Given a definable function, replace elements with their images
    public Set replacement(Function<Object, Object> function) {
        HashSet<Object> replacedSet = new HashSet<>();
        for (Object element : this.elements) {
            replacedSet.add(function.apply(element));
        }
        return new Set(replacedSet);
    }

    // Axiom of Infinity: Create a set that contains the empty set and is closed under the operation of adding a single element
    public static Set infiniteSet() {
        HashSet<Object> infiniteSet = new HashSet<>();
        infiniteSet.add(emptySet()); // Add the empty set
        return new Set(infiniteSet);
    }

    // Axiom of Power Set: Returns the power set of the current set
    public Set powerSet() {
        HashSet<Set> powerSet = new HashSet<>();
        int n = this.elements.size();
        powerSet.add(new Set(new HashSet<>())); // Start with the empty set

        for (Object element : this.elements) {
            HashSet<Set> newSubsets = new HashSet<>();

            for (Set subset : powerSet) {
                HashSet<Object> extendedSubset = new HashSet<>(subset.elements);
                extendedSubset.add(element);
                newSubsets.add(new Set(extendedSubset));
            }

            powerSet.addAll(newSubsets);
        }

        return new Set(new HashSet<>(powerSet));
    }

    // Axiom of Well-Ordering: Every non-empty set can be well-ordered
    // Implementation can vary depending on specific ordering criteria
    public boolean isWellOrdered(Comparator<Object> comparator) {
        if (this.elements.isEmpty()) {
            return true;
        }
        return this.elements.stream().allMatch(e -> ((Set) e)
                .elements.stream().allMatch(f -> comparator.compare(e, f) <= 0));
    }
}

